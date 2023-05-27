package digi.joy.mandala.workspace.adapters.api.published;

import digi.joy.mandala.workspace.adapters.infra.CommittedNoteData;
import digi.joy.mandala.workspace.adapters.infra.WorkspaceSessionData;

import java.util.List;

public record WorkspaceInfo(
        String workspaceName,
        List<CommittedNoteData> committedNotes,
        List<WorkspaceSessionData> workspaceSessions
) {
}
