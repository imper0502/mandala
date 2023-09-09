package digi.joy.mandala.workspace.scenario;

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

    public static CommitNoteContext.CommitNoteContextBuilder commitNoteContextBuilder() {
        return CommitNoteContext.builder();
    }
}
