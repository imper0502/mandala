package digi.joy.mandala.workspace.adapters.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import digi.joy.mandala.common.services.MandalaEventPublisher;
import digi.joy.mandala.note.adapters.gateway.InMemoryNoteDataAccessor;
import digi.joy.mandala.note.services.NoteContextBuilders;
import digi.joy.mandala.note.services.NoteService;
import digi.joy.mandala.note.services.infra.NoteDataAccessor;
import digi.joy.mandala.note.services.infra.NoteRepository;
import digi.joy.mandala.note.services.scenario.CreateNoteUseCase;
import digi.joy.mandala.workspace.adapters.gateway.InMemoryWorkspaceDataAccessor;
import digi.joy.mandala.workspace.adapters.handler.published.WorkspaceSummary;
import digi.joy.mandala.workspace.services.WorkspaceContextBuilders;
import digi.joy.mandala.workspace.services.WorkspaceService;
import digi.joy.mandala.workspace.services.infra.WorkspaceDataAccessor;
import digi.joy.mandala.workspace.services.infra.WorkspaceRepository;
import digi.joy.mandala.workspace.services.scenario.BuildWorkspaceUseCase;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
class WorkspaceQueryHandlerTest {
    private WorkspaceQueryHandler handlerUnderTest;

    private BuildWorkspaceUseCase buildWorkspaceScenario;
    private CreateNoteUseCase createNoteScenario;

    private final MandalaEventPublisher eventPublisher;

    @Autowired
    public WorkspaceQueryHandlerTest(MandalaEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @BeforeEach
    void setUp() {
        WorkspaceDataAccessor workspaceDataAccessor = new InMemoryWorkspaceDataAccessor();
        NoteDataAccessor noteDataAccessor = new InMemoryNoteDataAccessor();

        this.handlerUnderTest = new WorkspaceQueryHandler(workspaceDataAccessor, noteDataAccessor);

        this.buildWorkspaceScenario = new WorkspaceService(
                new WorkspaceRepository(workspaceDataAccessor), eventPublisher
        );

        this.createNoteScenario = new NoteService(
                new NoteRepository(noteDataAccessor), eventPublisher
        );
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

        ObjectMapper mapper = new ObjectMapper();
        var jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        System.out.println("jsonStr = " + jsonStr);

        List<WorkspaceSummary> list = mapper.readValue(jsonStr, new TypeReference<>(){});
        var result2 = handlerUnderTest.queryWorkspace(list.get(0).workspaceId());
        System.out.println("result2 = " + result2);
    }
}