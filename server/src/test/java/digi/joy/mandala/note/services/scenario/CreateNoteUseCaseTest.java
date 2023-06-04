package digi.joy.mandala.note.services.scenario;

import digi.joy.mandala.common.adapters.infra.MandalaEventBus;
import digi.joy.mandala.common.services.exception.RepositoryException;
import digi.joy.mandala.note.adapters.gateway.InMemoryNoteDataAccessor;
import digi.joy.mandala.note.entities.Note;
import digi.joy.mandala.note.services.NoteContextBuilders;
import digi.joy.mandala.note.services.NoteService;
import digi.joy.mandala.note.services.infra.NoteRepository;
import digi.joy.mandala.note.services.scenario.context.CreateNoteContext;
import digi.joy.mandala.workspace.adapters.gateway.InMemoryWorkspaceDataAccessor;
import digi.joy.mandala.workspace.entities.event.NoteCreatedWithWorkspaceId;
import digi.joy.mandala.workspace.entities.event.WorkspaceBuilt;
import digi.joy.mandala.workspace.services.WorkspaceContextBuilders;
import digi.joy.mandala.workspace.services.WorkspaceService;
import digi.joy.mandala.workspace.services.infra.WorkspaceRepository;
import digi.joy.mandala.workspace.services.scenario.BuildWorkspaceUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
public class CreateNoteUseCaseTest {
    private CreateNoteUseCase useCaseUnderTest;
    private BuildWorkspaceUseCase buildWorkspaceUseCase;

    private NoteRepository noteRepository;

    private final MandalaEventBus mandalaEventBus;

    @Autowired
    public CreateNoteUseCaseTest(MandalaEventBus mandalaEventBus) {
        this.mandalaEventBus = mandalaEventBus;
    }

    @BeforeEach
    void setUp() {
        WorkspaceRepository workspaceRepository = new WorkspaceRepository(new InMemoryWorkspaceDataAccessor());
        WorkspaceService workspaceService = new WorkspaceService(workspaceRepository, mandalaEventBus);
        this.buildWorkspaceUseCase = workspaceService;

        this.noteRepository = new NoteRepository(new InMemoryNoteDataAccessor());
        this.useCaseUnderTest = new NoteService(noteRepository, mandalaEventBus);
    }

    @Test
    void createNote() {
        CreateNoteContext readModel = NoteContextBuilders.createNoteScene()
                .title("TEST_NOTE")
                .content(List.of("TEST_CONTENT"))
                .build();

        UUID noteId = useCaseUnderTest.createNote(readModel);

        assertInstanceOf(Note.class, noteRepository.query(noteId));
    }

    @Test
    void createNoteWithWorkspaceId() throws RepositoryException {
        UUID workspaceId = buildWorkspaceUseCase.buildWorkspace(
                WorkspaceContextBuilders.buildWorkspaceScenario()
                        .workspaceName("TEST_WORKSPACE")
                        .build()
        );
        CreateNoteContext context = NoteContextBuilders.createNoteScene()
                .title("TEST_NOTE")
                .content(List.of("TEST_CONTENT"))
                .workspaceId(workspaceId)
                .build();

        UUID noteId = useCaseUnderTest.createNote(context);

        assertInstanceOf(Note.class, noteRepository.query(noteId));
        var eventCount = mandalaEventBus.history().size();
        assertInstanceOf(NoteCreatedWithWorkspaceId.class, mandalaEventBus.history().get(eventCount - 1));
        assertInstanceOf(WorkspaceBuilt.class, mandalaEventBus.history().get(eventCount - 2));
    }
}