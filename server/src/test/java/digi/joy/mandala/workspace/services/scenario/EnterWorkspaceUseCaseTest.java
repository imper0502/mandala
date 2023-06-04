package digi.joy.mandala.workspace.services.scenario;

import com.google.common.eventbus.EventBus;
import digi.joy.mandala.common.adapters.infra.MandalaEventBus;
import digi.joy.mandala.common.services.exception.RepositoryException;
import digi.joy.mandala.workspace.adapters.gateway.InMemoryWorkspaceDataAccessor;
import digi.joy.mandala.workspace.services.WorkspaceContextBuilders;
import digi.joy.mandala.workspace.services.WorkspaceService;
import digi.joy.mandala.workspace.services.infra.WorkspaceRepository;
import digi.joy.mandala.workspace.services.scenario.context.EnterWorkspaceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;

class EnterWorkspaceUseCaseTest {
    private BuildWorkspaceUseCase buildWorkspaceUseCase;
    private EnterWorkspaceUseCase useCaseUnderTest;
    private WorkspaceRepository repository;

    @BeforeEach
    void setUp() {
        this.repository = new WorkspaceRepository(new InMemoryWorkspaceDataAccessor());
        MandalaEventBus mandalaEventBus = new MandalaEventBus(new EventBus());
        WorkspaceService service = new WorkspaceService(repository, mandalaEventBus);
        this.useCaseUnderTest = service;
        this.buildWorkspaceUseCase = service;
    }

    @Test
    void EnterExistingWorkspace() throws RepositoryException {
        UUID workspaceId = buildWorkspaceUseCase.buildWorkspace(
                WorkspaceContextBuilders.buildWorkspaceScenario()
                        .workspaceName("TEST_WORKSPACE")
                        .build()
        );
        EnterWorkspaceContext context = WorkspaceContextBuilders.enterWorkspaceScenario()
                .userId(UUID.randomUUID())
                .workspaceId(workspaceId)
                .build();

        useCaseUnderTest.enterWorkspace(context);

        assertFalse(repository.query(workspaceId).getWorkspaceSessions().isEmpty());
    }
}