package digi.joy.mandala.workspace.services.scenario;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.common.services.exception.RepositoryException;
import digi.joy.mandala.workspace.entities.Workspace;
import digi.joy.mandala.workspace.services.WorkspaceContextBuilders;
import digi.joy.mandala.workspace.services.WorkspaceService;
import digi.joy.mandala.workspace.services.infra.WorkspaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LeaveWorkspaceScenarioTest {
    private BuildWorkspaceUseCase buildWorkspaceScenario;
    private EnterWorkspaceUseCase enterWorkspaceScenario;
    private LeaveWorkspaceUseCase sceneUnderTest;
    private final WorkspaceRepository repository;
    private final MandalaEventBus eventListener;

    @Autowired
    public LeaveWorkspaceScenarioTest(WorkspaceRepository repository, MandalaEventBus eventListener) {
        this.repository = repository;
        this.eventListener = eventListener;
    }

    @BeforeEach
    void setUp() {
        WorkspaceService workspaceService = new WorkspaceService(repository, eventListener);
        this.buildWorkspaceScenario = workspaceService;
        this.enterWorkspaceScenario = workspaceService;
        this.sceneUnderTest = workspaceService;
    }

    @Test
    void rehearseLeaveWorkspaceScene() throws RepositoryException {
        var context1 = WorkspaceContextBuilders.buildWorkspaceScenario()
                .workspaceName("TEST_WORKSPACE")
                .build();
        UUID id = buildWorkspaceScenario.buildWorkspace(context1);

        assertInstanceOf(Workspace.class, repository.query(id));

        var context2 = WorkspaceContextBuilders.enterWorkspaceScenario()
                .userId(UUID.randomUUID())
                .workspaceId(id)
                .build();
        enterWorkspaceScenario.enterWorkspace(context2);

        assertFalse(repository.query(id).getWorkspaceSessions().isEmpty());

        var context3 = WorkspaceContextBuilders.leaveWorkspaceScenario()
                .userId(context2.getUserId())
                .workspaceId(context2.getWorkspaceId())
                .build();
        sceneUnderTest.leaveWorkspace(context3);

        assertTrue(repository.query(id).getWorkspaceSessions().isEmpty());
    }

}
