package digi.joy.mandala.workspace.scenario;

import com.google.common.eventbus.EventBus;
import digi.joy.mandala.infra.event.MandalaEventBus;
import digi.joy.mandala.infra.exception.RepositoryException;
import digi.joy.mandala.workspace.dao.InMemoryWorkspaceRepositoryOperator;
import digi.joy.mandala.workspace.repository.WorkspaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LeaveWorkspaceScenarioTest {
    private BuildWorkspaceUseCase buildWorkspaceUseCase;
    private EnterWorkspaceUseCase enterWorkspaceUseCase;
    private LeaveWorkspaceUseCase useCaseUnderTest;
    private WorkspaceRepository repository;

    @BeforeEach
    void setUp() {
        this.repository = new WorkspaceRepository(new InMemoryWorkspaceRepositoryOperator());
        MandalaEventBus mandalaEventBus = new MandalaEventBus(new EventBus());
        WorkspaceService service = new WorkspaceService(repository, mandalaEventBus);
        this.buildWorkspaceUseCase = service;
        this.enterWorkspaceUseCase = service;
        this.useCaseUnderTest = service;
    }

    @Test
    void rehearseLeaveWorkspaceScene() throws RepositoryException {
        UUID userId = UUID.randomUUID();
        UUID workspaceId = buildWorkspaceUseCase.buildWorkspace(
                WorkspaceContextBuilders.buildWorkspaceScenario()
                        .workspaceName("TEST_WORKSPACE")
                        .build()
        );
        enterWorkspaceUseCase.enterWorkspace(
                WorkspaceContextBuilders.enterWorkspaceScenario()
                        .userId(userId)
                        .workspaceId(workspaceId)
                        .build()
        );
        var context = WorkspaceContextBuilders.leaveWorkspaceScenario()
                .userId(userId)
                .workspaceId(workspaceId)
                .build();

        useCaseUnderTest.leaveWorkspace(context);

        assertTrue(repository.query(workspaceId).getWorkspaceSessions().isEmpty());
    }

}
