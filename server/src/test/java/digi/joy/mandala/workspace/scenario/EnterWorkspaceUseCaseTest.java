package digi.joy.mandala.workspace.scenario;

import com.google.common.eventbus.EventBus;
import digi.joy.mandala.infra.event.MandalaEventHandler;
import digi.joy.mandala.infra.repository.RepositoryException;
import digi.joy.mandala.workspace.dao.InMemoryWorkspaceRepositoryOperator;
import digi.joy.mandala.workspace.repository.WorkspaceRepository;
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
        this.repository = new WorkspaceRepository(new InMemoryWorkspaceRepositoryOperator());
        MandalaEventHandler mandalaEventHandler = new MandalaEventHandler(new EventBus());
        WorkspaceService service = new WorkspaceService(repository, mandalaEventHandler);
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

        assertFalse(repository.get(workspaceId).getWorkspaceSessions().isEmpty());
    }
}