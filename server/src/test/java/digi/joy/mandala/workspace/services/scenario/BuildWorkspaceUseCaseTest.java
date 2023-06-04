package digi.joy.mandala.workspace.services.scenario;

import com.google.common.eventbus.EventBus;
import digi.joy.mandala.common.adapters.infra.MandalaEventBus;
import digi.joy.mandala.workspace.adapters.gateway.InMemoryWorkspaceDataAccessor;
import digi.joy.mandala.workspace.services.WorkspaceContextBuilders;
import digi.joy.mandala.workspace.services.WorkspaceService;
import digi.joy.mandala.workspace.services.infra.WorkspaceRepository;
import digi.joy.mandala.workspace.services.scenario.context.BuildWorkspaceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class BuildWorkspaceUseCaseTest {
    private BuildWorkspaceUseCase useCaseUnderTest;

    @BeforeEach
    void setUp() {
        WorkspaceRepository repository = new WorkspaceRepository(new InMemoryWorkspaceDataAccessor());
        MandalaEventBus mandalaEventBus = new MandalaEventBus(new EventBus());
        this.useCaseUnderTest = new WorkspaceService(repository, mandalaEventBus);
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