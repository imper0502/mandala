package digi.joy.mandala.application.adapters.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import digi.joy.mandala.application.adapters.gateway.InMemoryNoteDataAccessor;
import digi.joy.mandala.application.adapters.gateway.InMemoryWorkspaceDataAccessor;
import digi.joy.mandala.application.adapters.handler.published.WorkspaceSummary;
import digi.joy.mandala.application.services.infra.NoteDataAccessor;
import digi.joy.mandala.application.services.infra.WorkspaceDataAccessor;
import digi.joy.mandala.application.services.scenario.BuildWorkspaceScenario;
import digi.joy.mandala.application.services.scenario.CreateNoteScenario;
import digi.joy.mandala.application.services.utils.NoteContextBuilders;
import digi.joy.mandala.application.services.utils.WorkspaceContextBuilders;
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

    private final WorkspaceDataAccessor workspaceDataAccessor;

    private final NoteDataAccessor noteDataAccessor;

    private final BuildWorkspaceScenario buildWorkspaceScenario;
    private final CreateNoteScenario createNoteScenario;

    @Autowired
    public WorkspaceQueryHandlerTest(
            WorkspaceDataAccessor workspaceDataAccessor,
            NoteDataAccessor noteDataAccessor,
            BuildWorkspaceScenario buildWorkspaceScenario,
            CreateNoteScenario createNoteScenario) {
        this.workspaceDataAccessor = workspaceDataAccessor;
        this.noteDataAccessor = noteDataAccessor;
        this.buildWorkspaceScenario = buildWorkspaceScenario;
        this.createNoteScenario = createNoteScenario;
    }

    @BeforeEach
    void setUp() {
        this.handlerUnderTest = new WorkspaceQueryHandler(workspaceDataAccessor, noteDataAccessor);
    }

    @SneakyThrows
    @Test
    void json() {
        UUID defaultWorkspaceId = buildWorkspaceScenario.play(
                WorkspaceContextBuilders.buildWorkspaceScenario()
                        .workspaceId(UUID.fromString("64f01c0d-1026-4d89-bd5e-c7fff0d4f360"))
                        .workspaceName("DEFAULT_WORKSPACE")
                        .build()
        );

        UUID defaultNoteId = createNoteScenario.play(
                NoteContextBuilders.createNoteScene()
                        .workspaceId(defaultWorkspaceId)
                        .title("TEST_NOTE")
                        .content(List.of("TEST_CONTENT"))
                        .build()
        );

        var result = handlerUnderTest.queryWorkspaces();

        ObjectMapper mapper = new ObjectMapper();
        var jsonStr = mapper.writeValueAsString(result);
        System.out.println("jsonStr = " + jsonStr);

        List<WorkspaceSummary> list = mapper.readValue(jsonStr, new TypeReference<>(){});
        var result2 = handlerUnderTest.queryWorkspace(list.get(0).workspaceId());
        System.out.println("result2 = " + result2);


    }
}