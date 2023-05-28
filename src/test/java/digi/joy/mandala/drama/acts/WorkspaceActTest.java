package digi.joy.mandala.drama.acts;

import digi.joy.mandala.drama.acts.scenes.BuildWorkspaceScene;
import digi.joy.mandala.drama.acts.scenes.EnterWorkspaceScene;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class WorkspaceActTest {
    private final WorkspaceAct actUnderTest;
    private final BuildWorkspaceScene buildWorkspaceScene;
    private final EnterWorkspaceScene enterWorkspaceScene;
    private final WorkspaceRepository repository;

    @Autowired
    public WorkspaceActTest(WorkspaceAct actUnderTest, BuildWorkspaceScene buildWorkspaceScene, EnterWorkspaceScene enterWorkspaceScene, WorkspaceRepository repository) {
        this.actUnderTest = actUnderTest;
        this.buildWorkspaceScene = buildWorkspaceScene;
        this.enterWorkspaceScene = enterWorkspaceScene;
        this.repository = repository;
    }

    @Test
    void rehearseLeaveWorkspaceScene() {
        var context1 = WorkspaceContextBuilders.buildWorkspaceScene()
                .workspaceName("TEST_WORKSPACE")
                .build();
        UUID id = buildWorkspaceScene.play(context1);

        var context2 = WorkspaceContextBuilders.enterWorkspaceScene()
                .userId(UUID.randomUUID())
                .workspaceId(id)
                .build();
        enterWorkspaceScene.play(context2);

        Assertions.assertFalse(repository.query(id).getWorkspaceSessions().isEmpty());

        var context3 = WorkspaceContextBuilders.leaveWorkspaceScene()
                .userId(context2.getUserId())
                .workspaceId(context2.getWorkspaceId())
                .build();
        actUnderTest.leaveWorkspaceScene(context3);

        Assertions.assertTrue(repository.query(id).getWorkspaceSessions().isEmpty());
    }
}