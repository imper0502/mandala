package digi.joy.mandala.application.services.scenario;

import digi.joy.mandala.application.services.infra.exception.RepositoryException;
import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.application.entities.Workspace;
import digi.joy.mandala.application.services.utils.WorkspaceContextBuilders;
import digi.joy.mandala.application.services.infra.WorkspaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LeaveWorkspaceScenarioTest {
    private LeaveWorkspaceScenario sceneUnderTest;
    private final WorkspaceRepository repository;
    private final MandalaEventBus eventListener;
    private final BuildWorkspaceScenario buildWorkspaceScenario;
    private final EnterWorkspaceScenario enterWorkspaceScenario;

    @Autowired
    public LeaveWorkspaceScenarioTest(WorkspaceRepository repository, MandalaEventBus eventListener, BuildWorkspaceScenario buildWorkspaceScenario, EnterWorkspaceScenario enterWorkspaceScenario) {
        this.repository = repository;
        this.eventListener = eventListener;
        this.buildWorkspaceScenario = buildWorkspaceScenario;
        this.enterWorkspaceScenario = enterWorkspaceScenario;
    }

    @BeforeEach
    void setUp() {
        this.sceneUnderTest = new LeaveWorkspaceScenario(repository, eventListener);
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
