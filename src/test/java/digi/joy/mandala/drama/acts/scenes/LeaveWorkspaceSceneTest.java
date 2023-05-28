package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.actors.Workspace;
import digi.joy.mandala.drama.acts.WorkspaceContextBuilders;
import digi.joy.mandala.drama.acts.WorkspaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LeaveWorkspaceSceneTest {
    private LeaveWorkspaceScene sceneUnderTest;
    private final WorkspaceRepository repository;
    private final MandalaEventBus eventListener;
    private final BuildWorkspaceScene buildWorkspaceScene;
    private final EnterWorkspaceScene enterWorkspaceScene;

    @Autowired
    public LeaveWorkspaceSceneTest(WorkspaceRepository repository, MandalaEventBus eventListener, BuildWorkspaceScene buildWorkspaceScene, EnterWorkspaceScene enterWorkspaceScene) {
        this.repository = repository;
        this.eventListener = eventListener;
        this.buildWorkspaceScene = buildWorkspaceScene;
        this.enterWorkspaceScene = enterWorkspaceScene;
    }

    @BeforeEach
    void setUp() {
        this.sceneUnderTest = new LeaveWorkspaceScene(repository, eventListener);
    }

    @Test
    void rehearseLeaveWorkspaceScene() {
        var context1 = WorkspaceContextBuilders.buildWorkspaceScene()
                .workspaceName("TEST_WORKSPACE")
                .build();
        UUID id = buildWorkspaceScene.play(context1);

        assertInstanceOf(Workspace.class, repository.query(id));

        var context2 = WorkspaceContextBuilders.enterWorkspaceScene()
                .userId(UUID.randomUUID())
                .workspaceId(id)
                .build();
        enterWorkspaceScene.play(context2);

        assertFalse(repository.query(id).getWorkspaceSessions().isEmpty());

        var context3 = WorkspaceContextBuilders.leaveWorkspaceScene()
                .userId(context2.getUserId())
                .workspaceId(context2.getWorkspaceId())
                .build();
        sceneUnderTest.play(context3);

        assertTrue(repository.query(id).getWorkspaceSessions().isEmpty());
    }

}
