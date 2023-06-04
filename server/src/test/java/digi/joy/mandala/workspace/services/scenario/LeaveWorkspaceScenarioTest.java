package digi.joy.mandala.workspace.services.scenario;

import digi.joy.mandala.common.services.MandalaEventPublisher;
import digi.joy.mandala.common.services.exception.RepositoryException;
import digi.joy.mandala.workspace.adapters.gateway.InMemoryWorkspaceDataAccessor;
import digi.joy.mandala.workspace.services.WorkspaceContextBuilders;
import digi.joy.mandala.workspace.services.WorkspaceService;
import digi.joy.mandala.workspace.services.infra.WorkspaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LeaveWorkspaceScenarioTest {
    private BuildWorkspaceUseCase buildWorkspaceUseCase;
    private EnterWorkspaceUseCase enterWorkspaceUseCase;
    private LeaveWorkspaceUseCase useCaseUnderTest;
    private WorkspaceRepository repository;
    private final MandalaEventPublisher eventPublisher;

    @Autowired
    public LeaveWorkspaceScenarioTest(MandalaEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @BeforeEach
    void setUp() {
        this.repository = new WorkspaceRepository(new InMemoryWorkspaceDataAccessor());
        WorkspaceService service = new WorkspaceService(repository, eventPublisher);
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
