package digi.joy.mandala.drama.services.scenario;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.entities.Workspace;
import digi.joy.mandala.drama.services.WorkspaceContextBuilders;
import digi.joy.mandala.drama.services.WorkspaceRepository;
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
    void rehearseLeaveWorkspaceScene() {
        var context1 = WorkspaceContextBuilders.buildWorkspaceScene()
                .workspaceName("TEST_WORKSPACE")
                .build();
        UUID id = buildWorkspaceScenario.play(context1);

        assertInstanceOf(Workspace.class, repository.query(id));

        var context2 = WorkspaceContextBuilders.enterWorkspaceScene()
                .userId(UUID.randomUUID())
                .workspaceId(id)
                .build();
        enterWorkspaceScenario.play(context2);

        assertFalse(repository.query(id).getWorkspaceSessions().isEmpty());

        var context3 = WorkspaceContextBuilders.leaveWorkspaceScene()
                .userId(context2.getUserId())
                .workspaceId(context2.getWorkspaceId())
                .build();
        sceneUnderTest.play(context3);

        assertTrue(repository.query(id).getWorkspaceSessions().isEmpty());
    }

}
