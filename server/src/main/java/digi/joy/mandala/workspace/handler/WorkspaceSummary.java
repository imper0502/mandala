package digi.joy.mandala.workspace.handler;

import digi.joy.mandala.workspace.repository.CommittedNoteData;
import digi.joy.mandala.workspace.repository.WorkspaceSessionData;

import java.util.List;
import java.util.UUID;

public record WorkspaceSummary(
        UUID workspaceId,
        String workspaceName,
        List<CommittedNoteData> committedNotes,
        List<WorkspaceSessionData> workspaceSessions
) {
}
