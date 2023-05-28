package digi.joy.mandala.drama.acts;

import digi.joy.mandala.drama.acts.scenes.contexts.BuildWorkspaceContext;
import digi.joy.mandala.drama.acts.scenes.contexts.EnterWorkspaceContext;
import digi.joy.mandala.drama.acts.scenes.contexts.LeaveWorkspaceContext;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceContextBuilders {

    public static BuildWorkspaceContext.BuildWorkspaceContextBuilder buildWorkspaceScene() {
        return BuildWorkspaceContext.builder();
    }

    public static EnterWorkspaceContext.EnterWorkspaceContextBuilder enterWorkspaceScene() {
        return EnterWorkspaceContext.builder();
    }

    public static LeaveWorkspaceContext.LeaveWorkspaceContextBuilder leaveWorkspaceScene() {
        return LeaveWorkspaceContext.builder();
    }
}
