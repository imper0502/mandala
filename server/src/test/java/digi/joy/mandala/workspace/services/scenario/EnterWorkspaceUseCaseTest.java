package digi.joy.mandala.workspace.services.scenario;

import digi.joy.mandala.common.services.MandalaEventPublisher;
import digi.joy.mandala.common.services.exception.RepositoryException;
import digi.joy.mandala.workspace.adapters.gateway.InMemoryWorkspaceDataAccessor;
import digi.joy.mandala.workspace.services.WorkspaceContextBuilders;
import digi.joy.mandala.workspace.services.WorkspaceService;
import digi.joy.mandala.workspace.services.infra.WorkspaceRepository;
import digi.joy.mandala.workspace.services.scenario.context.EnterWorkspaceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class EnterWorkspaceUseCaseTest {
    private BuildWorkspaceUseCase buildWorkspaceUseCase;
    private EnterWorkspaceUseCase useCaseUnderTest;
    private WorkspaceRepository repository;
    private final MandalaEventPublisher eventListener;

    @Autowired
    public EnterWorkspaceUseCaseTest(@Qualifier("workspaceEventBus") MandalaEventPublisher eventListener) {

        this.eventListener = eventListener;
    }

    @BeforeEach
    void setUp() {
        this.repository = new WorkspaceRepository(new InMemoryWorkspaceDataAccessor());
        WorkspaceService service = new WorkspaceService(repository, eventListener);
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