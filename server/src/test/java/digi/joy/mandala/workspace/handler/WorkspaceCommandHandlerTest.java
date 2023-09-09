package digi.joy.mandala.workspace.handler;

import com.google.common.eventbus.EventBus;
import digi.joy.mandala.infra.event.MandalaEventBus;
import digi.joy.mandala.workspace.dao.InMemoryWorkspaceRepositoryOperator;
import digi.joy.mandala.workspace.repository.WorkspaceRepository;
import digi.joy.mandala.workspace.scenario.BuildWorkspaceContext;
import digi.joy.mandala.workspace.scenario.WorkspaceContextBuilders;
import digi.joy.mandala.workspace.scenario.WorkspaceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorkspaceCommandHandlerTest {
    private WorkspaceCommandHandler handlerUnderTest;

    @BeforeEach
    void setUp() {
        WorkspaceRepository repository = new WorkspaceRepository(new InMemoryWorkspaceRepositoryOperator());
        MandalaEventBus mandalaEventBus = new MandalaEventBus(new EventBus());
        this.handlerUnderTest = new WorkspaceCommandHandler(
                new WorkspaceService(repository, mandalaEventBus)
        );
    }

    @Test
    void rehearseBuildWorkspaceScene() {
        BuildWorkspaceContext context = WorkspaceContextBuilders.buildWorkspaceScenario()
                .workspaceName("TEST_WORKSPACE")
                .build();

        ResponseEntity<UUID> result = assertDoesNotThrow(() -> handlerUnderTest.buildWorkspaceScene(context));

        assertTrue(result.getStatusCode().is2xxSuccessful());
    }
}