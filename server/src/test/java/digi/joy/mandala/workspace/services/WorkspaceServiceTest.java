package digi.joy.mandala.workspace.services;

import digi.joy.mandala.workspace.adapters.handler.WorkspaceCommandHandler;
import digi.joy.mandala.workspace.services.scenario.WorkspaceService;
import digi.joy.mandala.workspace.services.utils.WorkspaceContextBuilders;
import digi.joy.mandala.workspace.services.infra.WorkspaceRepository;
import digi.joy.mandala.common.adapters.infra.MandalaEventPublisher;
import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.workspace.adapters.gateway.InMemoryWorkspaceDataAccessor;
import digi.joy.mandala.workspace.services.context.BuildWorkspaceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WorkspaceServiceTest {
    private WorkspaceCommandHandler controllerUnderTest;

    @BeforeEach
    void setUp() {
        WorkspaceRepository repository = new WorkspaceRepository(new InMemoryWorkspaceDataAccessor());
        MandalaEventBus eventBus = new MandalaEventPublisher();
        this.controllerUnderTest = new WorkspaceCommandHandler(
                new WorkspaceService(repository, null, eventBus)
        );
    }

    @Test
    void rehearseBuildWorkspaceScene() {
        BuildWorkspaceContext context = WorkspaceContextBuilders.buildWorkspaceScenario()
                .workspaceName("TEST_WORKSPACE")
                .build();

        ResponseEntity<UUID> result = assertDoesNotThrow(() -> controllerUnderTest.buildWorkspaceScene(context));

        assertTrue(result.getStatusCode().is2xxSuccessful());
    }
}