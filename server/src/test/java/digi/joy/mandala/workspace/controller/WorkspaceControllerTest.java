package digi.joy.mandala.workspace.controller;

import com.google.common.eventbus.EventBus;
import digi.joy.mandala.infra.event.MandalaEvent;
import digi.joy.mandala.infra.event.MandalaEventHandler;
import digi.joy.mandala.infra.repository.RepositoryException;
import digi.joy.mandala.note.dao.InMemoryNoteRepositoryOperator;
import digi.joy.mandala.note.repository.NoteRepository;
import digi.joy.mandala.note.repository.NoteRepositoryOperator;
import digi.joy.mandala.note.scenario.CreateNoteUseCase;
import digi.joy.mandala.note.scenario.NoteContextBuilders;
import digi.joy.mandala.note.scenario.NoteService;
import digi.joy.mandala.workspace.dao.InMemoryWorkspaceRepositoryOperator;
import digi.joy.mandala.workspace.listener.WorkspaceEventListener;
import digi.joy.mandala.workspace.repository.WorkspaceRepository;
import digi.joy.mandala.workspace.repository.WorkspaceRepositoryOperator;
import digi.joy.mandala.workspace.scenario.BuildWorkspaceUseCase;
import digi.joy.mandala.workspace.scenario.WorkspaceContextBuilders;
import digi.joy.mandala.workspace.scenario.WorkspaceService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


class WorkspaceControllerTest {
    private WorkspaceController sut;

    private BuildWorkspaceUseCase buildWorkspaceScenario;
    private CreateNoteUseCase createNoteScenario;
    private MandalaEventHandler workspaceEventHandler;
    private MandalaEventHandler noteEventHandler;


    @BeforeEach
    void setUp() {
        final WorkspaceRepositoryOperator workspaceRepositoryOperator = new InMemoryWorkspaceRepositoryOperator();
        final WorkspaceRepository workspaceRepository = new WorkspaceRepository(workspaceRepositoryOperator);
        final NoteRepositoryOperator noteRepositoryOperator = new InMemoryNoteRepositoryOperator();
        final NoteRepository noteRepository = new NoteRepository(noteRepositoryOperator);
        this.sut = new WorkspaceController(workspaceRepository, noteRepository);


        this.workspaceEventHandler = new MandalaEventHandler(new EventBus());
        final WorkspaceService workspaceService = new WorkspaceService(workspaceRepository, workspaceEventHandler);
        this.buildWorkspaceScenario = workspaceService;


        this.noteEventHandler = new MandalaEventHandler(new EventBus());
        noteEventHandler.register(new WorkspaceEventListener(workspaceService));
        this.createNoteScenario = new NoteService(noteRepository, noteEventHandler);
    }

    @SneakyThrows
    @Test
    void listWorkspaces() {
        assertDoesNotThrow(this::prepareTestFixtures);
        assertDoesNotThrow(this::prepareTestFixtures);

        final var result = assertDoesNotThrow(() -> sut.queryWorkspaces());

        assertInstanceOf(List.class, result);
        assertFalse(result.isEmpty());
        assertInstanceOf(WorkspaceResource.class, result.get(0));
        assertEquals(2, result.size());

        Stream.concat(workspaceEventHandler.history().stream(), noteEventHandler.history().stream())
                .sorted(Comparator.comparing(MandalaEvent::getOccurredTime))
                .forEach(event -> System.out.printf("<%s> %s # %s%n", event.getOccurredTime().atZone(ZoneId.systemDefault()).toLocalDateTime(), event.getClass().getSimpleName(), event.getId()));
    }

    @SneakyThrows
    @Test
    void getWorkspace() {
        final UUID testWorkspaceId = prepareTestFixtures();

        final var result = sut.queryWorkspace(testWorkspaceId);

        assertInstanceOf(ExpandedWorkspaceResource.class, result);
        assertEquals(testWorkspaceId, result.workspaceId());
        Stream.concat(workspaceEventHandler.history().stream(), noteEventHandler.history().stream())
                .sorted(Comparator.comparing(MandalaEvent::getOccurredTime))
                .forEach(event -> System.out.printf("<%s> %s # %s%n", event.getOccurredTime().atZone(ZoneId.systemDefault()).toLocalDateTime(), event.getClass().getSimpleName(), event.getId()));
    }

    private UUID prepareTestFixtures() throws RepositoryException {
        final UUID workspaceId = buildWorkspaceScenario.buildWorkspace(WorkspaceContextBuilders.buildWorkspaceScenario().workspaceId(UUID.randomUUID()).workspaceName("TEST_WORKSPACE").build());
        final UUID noteId = createNoteScenario.createNote(NoteContextBuilders.createNoteScene().workspaceId(workspaceId).title("TEST_NOTE").content(List.of("TEST_CONTENT")).build());
        return workspaceId;
    }
}