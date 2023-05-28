package digi.joy.mandala.drama.acts;

import digi.joy.mandala.drama.acts.scenes.BuildWorkspaceScene;
import digi.joy.mandala.drama.acts.scenes.EnterWorkspaceScene;
import digi.joy.mandala.drama.acts.scenes.contexts.EnterWorkspaceContext;
import digi.joy.mandala.drama.acts.scenes.contexts.BuildWorkspaceContext;
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
    private final BuildWorkspaceScene buildWorkspaceScene;
    private final EnterWorkspaceScene enterWorkspaceScene;

    @Autowired
    public WorkspaceActTest(WorkspaceAct actUnderTest, WorkspaceContextBuilders contextBuilders, BuildWorkspaceScene buildWorkspaceScene, EnterWorkspaceScene enterWorkspaceScene) {
        this.actUnderTest = actUnderTest;
        this.contextBuilders = contextBuilders;
        this.buildWorkspaceScene = buildWorkspaceScene;
        this.enterWorkspaceScene = enterWorkspaceScene;
    }

    @Test
    void rehearseLeaveWorkspaceScene() {
        BuildWorkspaceContext context1 = BuildWorkspaceContext.builder()
                .workspaceName("TEST_WORKSPACE")
                .build();
        UUID id = buildWorkspaceScene.play(context1);

        EnterWorkspaceContext context2 = EnterWorkspaceContext.builder()
                .userId(UUID.randomUUID())
                .workspaceId(id)
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