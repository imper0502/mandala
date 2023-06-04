package digi.joy.mandala.note.services.scenario;

import com.google.common.eventbus.EventBus;
import digi.joy.mandala.common.adapters.infra.MandalaEventBus;
import digi.joy.mandala.common.services.exception.RepositoryException;
import digi.joy.mandala.note.adapters.gateway.InMemoryNoteDataAccessor;
import digi.joy.mandala.note.entities.Note;
import digi.joy.mandala.note.entities.event.NoteCreated;
import digi.joy.mandala.note.services.NoteContextBuilders;
import digi.joy.mandala.note.services.NoteService;
import digi.joy.mandala.note.services.infra.NoteRepository;
import digi.joy.mandala.note.services.scenario.context.CreateNoteContext;
import digi.joy.mandala.workspace.adapters.gateway.InMemoryWorkspaceDataAccessor;
import digi.joy.mandala.workspace.adapters.listener.WorkspaceEventListener;
import digi.joy.mandala.workspace.entities.event.NoteCreatedWithWorkspaceId;
import digi.joy.mandala.workspace.services.WorkspaceContextBuilders;
import digi.joy.mandala.workspace.services.WorkspaceService;
import digi.joy.mandala.workspace.services.infra.WorkspaceRepository;
import digi.joy.mandala.workspace.services.scenario.BuildWorkspaceUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class CreateNoteUseCaseTest {
    private CreateNoteUseCase useCaseUnderTest;
    private BuildWorkspaceUseCase buildWorkspaceUseCase;
    private NoteRepository noteRepository;
    private MandalaEventBus noteEventBus;

    @BeforeEach
    void setUp() {
        WorkspaceRepository workspaceRepository = new WorkspaceRepository(new InMemoryWorkspaceDataAccessor());
        MandalaEventBus workspaceEventBus = new MandalaEventBus(new EventBus());
        WorkspaceService workspaceService = new WorkspaceService(workspaceRepository, workspaceEventBus);
        this.buildWorkspaceUseCase = workspaceService;

        this.noteRepository = new NoteRepository(new InMemoryNoteDataAccessor());
        this.noteEventBus = new MandalaEventBus(new EventBus());
        this.useCaseUnderTest = new NoteService(noteRepository, noteEventBus);
        noteEventBus.register(new WorkspaceEventListener(workspaceService));
    }

    @Test
    void createNote() {
        CreateNoteContext readModel = NoteContextBuilders.createNoteScene()
                .title("TEST_NOTE")
                .content(List.of("TEST_CONTENT"))
                .build();

        UUID noteId = useCaseUnderTest.createNote(readModel);

        assertInstanceOf(Note.class, noteRepository.query(noteId));
        var eventCount = noteEventBus.history().size();
        assertInstanceOf(NoteCreated.class, noteEventBus.history().get(eventCount - 1));
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
        var eventCount = noteEventBus.history().size();
        assertInstanceOf(NoteCreatedWithWorkspaceId.class, noteEventBus.history().get(eventCount - 1));
    }
}