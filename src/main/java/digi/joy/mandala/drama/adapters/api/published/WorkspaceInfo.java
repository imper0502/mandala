package digi.joy.mandala.drama.adapters.api.published;

import digi.joy.mandala.drama.adapters.infra.schema.CommittedNoteData;
import digi.joy.mandala.drama.adapters.infra.schema.WorkspaceSessionData;

import java.util.List;

public record WorkspaceInfo(
        String workspaceName,
        List<CommittedNoteData> committedNotes,
        List<WorkspaceSessionData> workspaceSessions
) {
}
