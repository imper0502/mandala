package digi.joy.mandala.drama.acts;

import digi.joy.mandala.drama.acts.scenes.BuildWorkspace;
import digi.joy.mandala.drama.acts.scenes.EnterWorkspaceScene;
import digi.joy.mandala.drama.acts.scenes.contexts.EnterWorkspaceContext;
import digi.joy.mandala.drama.acts.scenes.contexts.InputOfBuildWorkspace;
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
    private final WorkspaceContextBuilders contextBuilders;
    private final BuildWorkspace buildWorkspace;
    private final EnterWorkspaceScene enterWorkspaceScene;

    @Autowired
    public WorkspaceActTest(WorkspaceAct actUnderTest, WorkspaceContextBuilders contextBuilders, BuildWorkspace buildWorkspace, EnterWorkspaceScene enterWorkspaceScene) {
        this.actUnderTest = actUnderTest;
        this.contextBuilders = contextBuilders;
        this.buildWorkspace = buildWorkspace;
        this.enterWorkspaceScene = enterWorkspaceScene;
    }

    @Test
    void rehearseLeaveWorkspaceScene() {
        InputOfBuildWorkspace context1 = InputOfBuildWorkspace.builder()
                .workspaceId(UUID.randomUUID().toString())
                .workspaceName("TEST_WORKSPACE")
                .build();
        buildWorkspace.execute(context1);

        EnterWorkspaceContext context2 = EnterWorkspaceContext.builder()
                .userId(UUID.randomUUID().toString())
                .workspaceId(context1.getWorkspaceId())
                .build();
        enterWorkspaceScene.play(context2);

        actUnderTest.leaveWorkspaceScene(
                contextBuilders.leaveWorkspaceScene()
                        .userId(context2.getUserId())
                        .workspaceId(context2.getWorkspaceId())
                        .build()
        );
    }
}