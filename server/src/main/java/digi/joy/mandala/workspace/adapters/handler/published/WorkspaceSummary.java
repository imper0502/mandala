package digi.joy.mandala.workspace.adapters.handler.published;

import digi.joy.mandala.workspace.adapters.gateway.schema.CommittedNoteData;
import digi.joy.mandala.workspace.adapters.gateway.schema.WorkspaceSessionData;

import java.util.List;
import java.util.UUID;

public record WorkspaceSummary(
        UUID workspaceId,
        String workspaceName,
        List<CommittedNoteData> committedNotes,
        List<WorkspaceSessionData> workspaceSessions
) {
}
