package digi.joy.mandala.drama.acts;

import digi.joy.mandala.drama.acts.scenes.contexts.LeaveWorkspaceContext;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceContextBuilders {
    public LeaveWorkspaceContext.LeaveWorkspaceContextBuilder leaveWorkspaceScene() {
        return LeaveWorkspaceContext.builder();
    }
}
