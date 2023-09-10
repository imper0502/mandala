package digi.joy.mandala.note.scenario;

import com.google.common.eventbus.EventBus;
import digi.joy.mandala.infra.dao.DAOException;
import digi.joy.mandala.infra.event.MandalaEventHandler;
import digi.joy.mandala.infra.repository.RepositoryException;
import digi.joy.mandala.note.Note;
import digi.joy.mandala.note.dao.InMemoryNoteRepositoryOperator;
import digi.joy.mandala.note.event.NoteCreated;
import digi.joy.mandala.note.repository.NoteRepository;
import digi.joy.mandala.workspace.dao.InMemoryWorkspaceRepositoryOperator;
import digi.joy.mandala.workspace.event.NoteCreatedWithWorkspaceId;
import digi.joy.mandala.workspace.listener.WorkspaceEventListener;
import digi.joy.mandala.workspace.repository.WorkspaceRepository;
import digi.joy.mandala.workspace.scenario.BuildWorkspaceUseCase;
import digi.joy.mandala.workspace.scenario.WorkspaceContextBuilders;
import digi.joy.mandala.workspace.scenario.WorkspaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class CreateNoteUseCaseTest {
    private CreateNoteUseCase useCaseUnderTest;
    private BuildWorkspaceUseCase buildWorkspaceUseCase;
    private NoteRepository noteRepository;
    private MandalaEventHandler noteEventBus;

    @BeforeEach
    void setUp() {
        WorkspaceRepository workspaceRepository = new WorkspaceRepository(new InMemoryWorkspaceRepositoryOperator());
        MandalaEventHandler workspaceEventBus = new MandalaEventHandler(new EventBus());
        WorkspaceService workspaceService = new WorkspaceService(workspaceRepository, workspaceEventBus);
        this.buildWorkspaceUseCase = workspaceService;

        this.noteRepository = new NoteRepository(new InMemoryNoteRepositoryOperator());
        this.noteEventBus = new MandalaEventHandler(new EventBus());
        this.useCaseUnderTest = new NoteService(noteRepository, noteEventBus);
        noteEventBus.register(new WorkspaceEventListener(workspaceService, workspaceService, workspaceService, workspaceService));
    }

    @Test
    void createNote() throws DAOException {
        CreateNoteContext readModel = NoteContextBuilders.createNoteScene()
                .title("TEST_NOTE")
                .content(List.of("TEST_CONTENT"))
                .build();

        UUID noteId = useCaseUnderTest.createNote(readModel);

        assertInstanceOf(Note.class, noteRepository.get(noteId));
        var eventCount = noteEventBus.history().size();
        assertInstanceOf(NoteCreated.class, noteEventBus.history().get(eventCount - 1));
    }

    @Test
    void createNoteWithWorkspaceId() throws RepositoryException, DAOException {
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

        assertInstanceOf(Note.class, noteRepository.get(noteId));
        var eventCount = noteEventBus.history().size();
        assertInstanceOf(NoteCreatedWithWorkspaceId.class, noteEventBus.history().get(eventCount - 1));
    }
}