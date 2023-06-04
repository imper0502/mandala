package digi.joy.mandala.application.services.scenario;

import digi.joy.mandala.application.adapters.gateway.InMemoryNoteDataAccessor;
import digi.joy.mandala.application.adapters.gateway.InMemoryWorkspaceDataAccessor;
import digi.joy.mandala.application.adapters.listener.WorkspaceEventListener;
import digi.joy.mandala.application.entities.Note;
import digi.joy.mandala.application.entities.Workspace;
import digi.joy.mandala.application.services.context.BuildWorkspaceContext;
import digi.joy.mandala.application.services.context.CreateNoteContext;
import digi.joy.mandala.application.services.infra.NoteRepository;
import digi.joy.mandala.application.services.infra.WorkspaceRepository;
import digi.joy.mandala.application.services.infra.exception.RepositoryException;
import digi.joy.mandala.application.services.utils.NoteContextBuilders;
import digi.joy.mandala.application.services.utils.WorkspaceContextBuilders;
import digi.joy.mandala.common.adapters.infra.MandalaEventPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
public class CreateNoteScenarioTest {
    private CreateNoteUseCase sceneUnderTest;
    private BuildWorkspaceUseCase buildWorkspaceScenario;

    private WorkspaceRepository workspaceRepository;

    private NoteRepository noteRepository;

    private final MandalaEventPublisher mandalaEventPublisher;

    @Autowired public CreateNoteScenarioTest(MandalaEventPublisher mandalaEventPublisher) {
        this.mandalaEventPublisher = mandalaEventPublisher;
    }

    @BeforeEach
    void setUp() {
        this.workspaceRepository = new WorkspaceRepository(new InMemoryWorkspaceDataAccessor());
        this.noteRepository = new NoteRepository(new InMemoryNoteDataAccessor());
        WorkspaceService workspaceService = new WorkspaceService(
                workspaceRepository,
                noteRepository,
                mandalaEventPublisher
        );
        mandalaEventPublisher.register(new WorkspaceEventListener(workspaceService));
        this.buildWorkspaceScenario = workspaceService;
        this.sceneUnderTest = workspaceService;
    }

    @Test
    void createNote() {
        CreateNoteContext readModel = NoteContextBuilders.createNoteScene()
                .title("TEST_NOTE")
                .content(List.of("TEST_CONTENT"))
                .build();

        UUID id = sceneUnderTest.createNote(readModel);

        assertInstanceOf(Note.class, noteRepository.query(id));
    }

    @Test
    void createNoteInWorkspace() throws RepositoryException {
        BuildWorkspaceContext context1 = WorkspaceContextBuilders.buildWorkspaceScenario()
                .workspaceName("TEST_WORKSPACE")
                .build();
        UUID workspaceId = buildWorkspaceScenario.buildWorkspace(context1);

        assertInstanceOf(Workspace.class, workspaceRepository.query(workspaceId));
        CreateNoteContext context2 = NoteContextBuilders.createNoteScene()
                .title("TEST_NOTE")
                .content(List.of("TEST_CONTENT"))
                .workspaceId(workspaceId)
                .build();
        UUID noteId = sceneUnderTest.createNote(context2);

        assertInstanceOf(Note.class, noteRepository.query(noteId));
        assertFalse(workspaceRepository.query(workspaceId).getCommittedNotes().isEmpty());
    }
}