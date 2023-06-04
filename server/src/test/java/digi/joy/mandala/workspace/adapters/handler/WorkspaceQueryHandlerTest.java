package digi.joy.mandala.workspace.adapters.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import digi.joy.mandala.note.services.NoteContextBuilders;
import digi.joy.mandala.note.services.infra.NoteDataAccessor;
import digi.joy.mandala.note.services.scenario.CreateNoteUseCase;
import digi.joy.mandala.workspace.adapters.handler.published.WorkspaceSummary;
import digi.joy.mandala.workspace.services.WorkspaceContextBuilders;
import digi.joy.mandala.workspace.services.infra.WorkspaceDataAccessor;
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

    private final WorkspaceDataAccessor workspaceDataAccessor;

    private final NoteDataAccessor noteDataAccessor;

    private final BuildWorkspaceUseCase buildWorkspaceScenario;
    private final CreateNoteUseCase createNoteScenario;

    @Autowired
    public WorkspaceQueryHandlerTest(
            WorkspaceDataAccessor workspaceDataAccessor,
            NoteDataAccessor noteDataAccessor,
            BuildWorkspaceUseCase buildWorkspaceScenario,
            CreateNoteUseCase createNoteScenario) {
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