package digi.joy.mandala.workspace.services.scenario;

import digi.joy.mandala.common.adapters.infra.MandalaEventPublisher;
import digi.joy.mandala.common.services.exception.RepositoryException;
import digi.joy.mandala.note.adapters.gateway.InMemoryNoteDataAccessor;
import digi.joy.mandala.note.entities.Note;
import digi.joy.mandala.note.services.NoteContextBuilders;
import digi.joy.mandala.note.services.NoteService;
import digi.joy.mandala.note.services.infra.NoteRepository;
import digi.joy.mandala.note.services.scenario.CreateNoteUseCase;
import digi.joy.mandala.note.services.scenario.context.CreateNoteContext;
import digi.joy.mandala.workspace.adapters.gateway.InMemoryWorkspaceDataAccessor;
import digi.joy.mandala.workspace.adapters.listener.WorkspaceEventListener;
import digi.joy.mandala.workspace.entities.Workspace;
import digi.joy.mandala.workspace.services.WorkspaceContextBuilders;
import digi.joy.mandala.workspace.services.WorkspaceService;
import digi.joy.mandala.workspace.services.infra.WorkspaceRepository;
import digi.joy.mandala.workspace.services.scenario.context.BuildWorkspaceContext;
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
        WorkspaceService workspaceService = new WorkspaceService(
                workspaceRepository,
                mandalaEventPublisher
        );
        this.buildWorkspaceScenario = workspaceService;

        this.noteRepository = new NoteRepository(new InMemoryNoteDataAccessor());
        NoteService noteService = new NoteService(noteRepository, mandalaEventPublisher);
        this.sceneUnderTest = noteService;

        mandalaEventPublisher.register(new WorkspaceEventListener(workspaceService));
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