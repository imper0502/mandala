package digi.joy.mandala.workspace.services.utils;

import digi.joy.mandala.workspace.services.context.BuildWorkspaceContext;
import digi.joy.mandala.workspace.services.context.CommitNoteContext;
import digi.joy.mandala.workspace.services.context.EnterWorkspaceContext;
import digi.joy.mandala.workspace.services.context.LeaveWorkspaceContext;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceContextBuilders {

    public static BuildWorkspaceContext.BuildWorkspaceContextBuilder buildWorkspaceScenario() {
        return BuildWorkspaceContext.builder();
    }

    public static EnterWorkspaceContext.EnterWorkspaceContextBuilder enterWorkspaceScenario() {
        return EnterWorkspaceContext.builder();
    }

    public static LeaveWorkspaceContext.LeaveWorkspaceContextBuilder leaveWorkspaceScenario() {
        return LeaveWorkspaceContext.builder();
    }

    public static CommitNoteContext.CommitNoteContextBuilder commitNoteScenario() {
        return CommitNoteContext.builder();
    }
}
