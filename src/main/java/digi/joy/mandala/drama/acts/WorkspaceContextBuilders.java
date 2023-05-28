package digi.joy.mandala.drama.acts;

import digi.joy.mandala.drama.acts.scenes.contexts.BuildWorkspaceContext;
import digi.joy.mandala.drama.acts.scenes.contexts.CreateNoteInWorkspaceContext;
import digi.joy.mandala.drama.acts.scenes.contexts.LeaveWorkspaceContext;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceContextBuilders {

    public BuildWorkspaceContext.BuildWorkspaceContextBuilder buildWorkspaceScene() {
        return BuildWorkspaceContext.builder();
    }

    public LeaveWorkspaceContext.LeaveWorkspaceContextBuilder leaveWorkspaceScene() {
        return LeaveWorkspaceContext.builder();
    }

    public CreateNoteInWorkspaceContext.CreateNoteInWorkspaceContextBuilder createNoteInWorkspaceScene() {
        return CreateNoteInWorkspaceContext.builder();
    }
}
