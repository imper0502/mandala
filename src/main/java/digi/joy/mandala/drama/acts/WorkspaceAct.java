package digi.joy.mandala.drama.acts;

import digi.joy.mandala.drama.acts.scenes.BuildWorkspaceScene;
import digi.joy.mandala.drama.acts.scenes.EnterWorkspaceScene;
import digi.joy.mandala.drama.acts.scenes.LeaveWorkspaceScene;
import digi.joy.mandala.drama.acts.scenes.contexts.BuildWorkspaceContext;
import digi.joy.mandala.drama.acts.scenes.contexts.EnterWorkspaceContext;
import digi.joy.mandala.drama.acts.scenes.contexts.LeaveWorkspaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WorkspaceAct {

    private final BuildWorkspaceScene buildWorkspaceScene;
    private final EnterWorkspaceScene enterWorkspaceScene;
    private final LeaveWorkspaceScene leaveWorkspaceScene;

    @Autowired
    public WorkspaceAct(BuildWorkspaceScene buildWorkspaceScene, EnterWorkspaceScene enterWorkspaceScene, LeaveWorkspaceScene leaveWorkspaceScene) {
        this.buildWorkspaceScene = buildWorkspaceScene;
        this.enterWorkspaceScene = enterWorkspaceScene;
        this.leaveWorkspaceScene = leaveWorkspaceScene;
    }


    public UUID buildWorkspaceScene(BuildWorkspaceContext context) {
        return buildWorkspaceScene.play(context);
    }

    public void enterWorkspaceScene(EnterWorkspaceContext context) {
        enterWorkspaceScene.play(context);
    }

    public void leaveWorkspaceScene(LeaveWorkspaceContext context) {
        leaveWorkspaceScene.play(context);
    }
}
