package digi.joy.mandala.workspace.adapters.handler;

import digi.joy.mandala.common.services.MandalaEventPublisher;
import digi.joy.mandala.workspace.adapters.gateway.InMemoryWorkspaceDataAccessor;
import digi.joy.mandala.workspace.services.WorkspaceContextBuilders;
import digi.joy.mandala.workspace.services.WorkspaceService;
import digi.joy.mandala.workspace.services.infra.WorkspaceRepository;
import digi.joy.mandala.workspace.services.scenario.context.BuildWorkspaceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class WorkspaceCommandHandlerTest {
    private WorkspaceCommandHandler handlerUnderTest;

    private final MandalaEventPublisher eventPublisher;

    @Autowired
    WorkspaceCommandHandlerTest(@Qualifier("workspaceEventBus") MandalaEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @BeforeEach
    void setUp() {
        WorkspaceRepository repository = new WorkspaceRepository(new InMemoryWorkspaceDataAccessor());
        this.handlerUnderTest = new WorkspaceCommandHandler(
                new WorkspaceService(repository, eventPublisher)
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