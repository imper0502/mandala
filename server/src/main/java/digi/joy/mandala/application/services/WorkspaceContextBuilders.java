package digi.joy.mandala.application.services;

import digi.joy.mandala.application.services.scenario.context.BuildWorkspaceContext;
import digi.joy.mandala.application.services.scenario.context.EnterWorkspaceContext;
import digi.joy.mandala.application.services.scenario.context.LeaveWorkspaceContext;
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
