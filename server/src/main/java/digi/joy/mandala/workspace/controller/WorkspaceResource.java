package digi.joy.mandala.workspace.controller;

import digi.joy.mandala.workspace.CommittedNote;
import digi.joy.mandala.workspace.Workspace;
import digi.joy.mandala.workspace.WorkspaceSession;

import java.util.List;
import java.util.UUID;

public record WorkspaceResource(
        UUID workspaceId,
        String workspaceName,
        List<CommittedNote> committedNotes,
        List<WorkspaceSession> workspaceSessions
) {
    public static WorkspaceResource of(Workspace w) {
        return new WorkspaceResource(
                w.getWorkspaceId(),
                w.getWorkspaceName(),
                w.getCommittedNotes(),
                w.getWorkspaceSessions()
        );
    }

}
