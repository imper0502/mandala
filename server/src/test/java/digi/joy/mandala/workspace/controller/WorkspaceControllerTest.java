package digi.joy.mandala.workspace.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.eventbus.EventBus;
import digi.joy.mandala.infra.event.MandalaEventHandler;
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


class WorkspaceControllerTest {
    private WorkspaceController sut;

    private BuildWorkspaceUseCase buildWorkspaceScenario;
    private CreateNoteUseCase createNoteScenario;

    private MandalaEventHandler workspaceEventBus;


    @BeforeEach
    void setUp() {
        final WorkspaceRepositoryOperator workspaceRepositoryOperator = new InMemoryWorkspaceRepositoryOperator();
        final WorkspaceRepository workspaceRepository = new WorkspaceRepository(workspaceRepositoryOperator);
        final NoteRepositoryOperator noteRepositoryOperator = new InMemoryNoteRepositoryOperator();
        final NoteRepository noteRepository = new NoteRepository(noteRepositoryOperator);
        this.sut = new WorkspaceController(workspaceRepository, noteRepository);


        this.workspaceEventBus = new MandalaEventHandler(new EventBus());
        final WorkspaceService workspaceService = new WorkspaceService(
                new WorkspaceRepository(workspaceRepositoryOperator), workspaceEventBus
        );
        this.buildWorkspaceScenario = workspaceService;


        final MandalaEventHandler noteEventBus = new MandalaEventHandler(new EventBus());
        noteEventBus.register(new WorkspaceEventListener(workspaceService, workspaceService, workspaceService, workspaceService));
        this.createNoteScenario = new NoteService(noteRepository, noteEventBus);
    }

    @SneakyThrows
    @Test
    void json() {
        final UUID defaultWorkspaceId = buildWorkspaceScenario.buildWorkspace(
                WorkspaceContextBuilders.buildWorkspaceScenario()
                        .workspaceId(UUID.randomUUID())
                        .workspaceName("TEST_WORKSPACE")
                        .build()
        );

        final UUID defaultNoteId = createNoteScenario.createNote(
                NoteContextBuilders.createNoteScene()
                        .workspaceId(defaultWorkspaceId)
                        .title("TEST_NOTE")
                        .content(List.of("TEST_CONTENT"))
                        .build()
        );

        final var result = sut.queryWorkspaces();

        workspaceEventBus.history();
        final ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        final var jsonStr = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
        System.out.println("jsonStr = " + jsonStr);

        List<WorkspaceResource> list = mapper.readValue(jsonStr, new TypeReference<>() {
        });

        final var result2 = sut.queryWorkspace(list.get(0).workspaceId());

        final var jsonStr2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result2);
        System.out.println("jsonStr2 = " + jsonStr2);
    }
}