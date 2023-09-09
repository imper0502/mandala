package digi.joy.mandala.workspace.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.EventBus;
import digi.joy.mandala.infra.event.MandalaEventBus;
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

import java.util.List;
import java.util.UUID;


class WorkspaceQueryHandlerTest {
    private WorkspaceQueryHandler handlerUnderTest;

    private BuildWorkspaceUseCase buildWorkspaceScenario;
    private CreateNoteUseCase createNoteScenario;

    private MandalaEventBus workspaceEventBus;


    @BeforeEach
    void setUp() {
        WorkspaceRepositoryOperator workspaceRepositoryOperator = new InMemoryWorkspaceRepositoryOperator();
        NoteRepositoryOperator noteRepositoryOperator = new InMemoryNoteRepositoryOperator();

        this.handlerUnderTest = new WorkspaceQueryHandler(workspaceRepositoryOperator, noteRepositoryOperator);

        this.workspaceEventBus = new MandalaEventBus(new EventBus());
        WorkspaceService workspaceService = new WorkspaceService(
                new WorkspaceRepository(workspaceRepositoryOperator), workspaceEventBus
        );

        this.buildWorkspaceScenario = workspaceService;

        MandalaEventBus noteEventBus = new MandalaEventBus(new EventBus());
        this.createNoteScenario = new NoteService(
                new NoteRepository(noteRepositoryOperator), noteEventBus
        );

        noteEventBus.register(new WorkspaceEventListener(workspaceService));
    }

    @SneakyThrows
    @Test
    void json() {
        UUID defaultWorkspaceId = buildWorkspaceScenario.buildWorkspace(
                WorkspaceContextBuilders.buildWorkspaceScenario()
                        .workspaceId(UUID.randomUUID())
                        .workspaceName("TEST_WORKSPACE")
                        .build()
        );

        UUID defaultNoteId = createNoteScenario.createNote(
                NoteContextBuilders.createNoteScene()
                        .workspaceId(defaultWorkspaceId)
                        .title("TEST_NOTE")
                        .content(List.of("TEST_CONTENT"))
                        .build()
        );

        var result = handlerUnderTest.queryWorkspaces();

        workspaceEventBus.history();
        ObjectMapper mapper = new ObjectMapper();
        var jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        System.out.println("jsonStr = " + jsonStr);

        List<WorkspaceSummary> list = mapper.readValue(jsonStr, new TypeReference<>(){});
        var result2 = handlerUnderTest.queryWorkspace(list.get(0).workspaceId());
        System.out.println("result2 = " + result2);
    }
}