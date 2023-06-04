package digi.joy.mandala.workspace.services.scenario;

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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
class BuildWorkspaceUseCaseTest {
    private BuildWorkspaceUseCase useCaseUnderTest;
    private final MandalaEventPublisher eventPublisher;

    @Autowired
    public BuildWorkspaceUseCaseTest(@Qualifier("workspaceEventBus") MandalaEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @BeforeEach
    void setUp() {
        WorkspaceRepository repository = new WorkspaceRepository(new InMemoryWorkspaceDataAccessor());
        this.useCaseUnderTest = new WorkspaceService(repository, eventPublisher);
    }

    @Test
    void BuildOneNewWorkspace() {
        BuildWorkspaceContext context = WorkspaceContextBuilders.buildWorkspaceScenario()
                .workspaceName("TEST_WORKSPACE")
                .build();

        UUID result = assertDoesNotThrow(() -> useCaseUnderTest.buildWorkspace(context));

        assertInstanceOf(UUID.class, result);
    }

}