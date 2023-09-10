package digi.joy.mandala.note.scenario;

import com.google.common.eventbus.EventBus;
import digi.joy.mandala.infra.event.MandalaEventHandler;
import digi.joy.mandala.note.Note;
import digi.joy.mandala.note.dao.InMemoryNoteRepositoryOperator;
import digi.joy.mandala.note.event.NoteCreated;
import digi.joy.mandala.note.repository.NoteRepository;
import digi.joy.mandala.workspace.dao.InMemoryWorkspaceRepositoryOperator;
import digi.joy.mandala.workspace.event.WorkspaceNoteCreated;
import digi.joy.mandala.workspace.event.WorkspaceUpdated;
import digi.joy.mandala.workspace.listener.WorkspaceEventListener;
import digi.joy.mandala.workspace.repository.WorkspaceRepository;
import digi.joy.mandala.workspace.scenario.BuildWorkspaceUseCase;
import digi.joy.mandala.workspace.scenario.WorkspaceContextBuilders;
import digi.joy.mandala.workspace.scenario.WorkspaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CreateNoteUseCaseTest {
    private CreateNoteUseCase useCaseUnderTest;
    private BuildWorkspaceUseCase buildWorkspaceUseCase;
    private NoteRepository noteRepository;
    private MandalaEventHandler workspaceEventHandler;
    private MandalaEventHandler noteEventHandler;
    private WorkspaceRepository workspaceRepository;

    @BeforeEach
    void setUp() {
        this.workspaceRepository = new WorkspaceRepository(new InMemoryWorkspaceRepositoryOperator());
        final EventBus eventBus = new EventBus();
        this.workspaceEventHandler = new MandalaEventHandler(eventBus);
        final WorkspaceService workspaceService = new WorkspaceService(workspaceRepository, workspaceEventHandler);
        this.buildWorkspaceUseCase = workspaceService;

        this.noteRepository = new NoteRepository(new InMemoryNoteRepositoryOperator());
        this.noteEventHandler = new MandalaEventHandler(eventBus);
        this.noteEventHandler.register(new WorkspaceEventListener(workspaceService));
        this.useCaseUnderTest = new NoteService(noteRepository, noteEventHandler);
    }

    @Test
    void createNote() {
        final CreateNoteContext readModel = NoteContextBuilders.createNoteScene()
                .title("TEST_NOTE")
                .content(List.of("TEST_CONTENT")).author(UUID.randomUUID())
                .build();

        final UUID noteId = assertDoesNotThrow(() -> useCaseUnderTest.createNote(readModel));

        assertInstanceOf(Note.class, noteRepository.get(noteId));
        final var eventCount = noteEventHandler.history().size();
        assertInstanceOf(NoteCreated.class, noteEventHandler.history().get(eventCount - 1));
    }

    @Test
    void createWorkspaceNote() {
        final UUID workspaceId = assertDoesNotThrow(() -> buildWorkspaceUseCase.buildWorkspace(
                WorkspaceContextBuilders.buildWorkspaceScenario()
                        .workspaceName("TEST_WORKSPACE")
                        .build())
        );
        final CreateNoteContext context = NoteContextBuilders.createNoteScene()
                .title("TEST_NOTE")
                .content(List.of("TEST_CONTENT"))
                .workspaceId(workspaceId).author(UUID.randomUUID())
                .build();

        final UUID noteId = assertDoesNotThrow(() -> useCaseUnderTest.createNote(context));

        assertInstanceOf(Note.class, noteRepository.get(noteId));
        final var noteEventCount = noteEventHandler.history().size();
        assertInstanceOf(WorkspaceNoteCreated.class, noteEventHandler.history().get(noteEventCount - 1));
        final var workspaceEventCount = workspaceEventHandler.history().size();
        assertInstanceOf(WorkspaceUpdated.class, workspaceEventHandler.history().get(workspaceEventCount - 1));
        assertEquals(noteId, workspaceRepository.get(workspaceId).getCommittedNotes().get(0).noteId());
    }
}