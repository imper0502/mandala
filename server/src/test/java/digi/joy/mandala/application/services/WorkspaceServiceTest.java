package digi.joy.mandala.application.services;

import digi.joy.mandala.application.adapters.handler.WorkspaceCommandHandler;
import digi.joy.mandala.application.services.utils.WorkspaceContextBuilders;
import digi.joy.mandala.application.services.infra.WorkspaceRepository;
import digi.joy.mandala.common.adapters.infra.MandalaEventPublisher;
import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.application.adapters.gateway.InMemoryWorkspaceDataAccessor;
import digi.joy.mandala.application.services.scenario.BuildWorkspaceScenario;
import digi.joy.mandala.application.services.scenario.EnterWorkspaceScenario;
import digi.joy.mandala.application.services.scenario.LeaveWorkspaceScenario;
import digi.joy.mandala.application.services.context.BuildWorkspaceContext;
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
                new BuildWorkspaceScenario(repository, eventBus),
                new EnterWorkspaceScenario(repository, eventBus),
                new LeaveWorkspaceScenario(repository, eventBus)
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