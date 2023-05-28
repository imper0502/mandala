package digi.joy.mandala.application.adapters.api.published;

import digi.joy.mandala.application.adapters.infra.schema.CommittedNoteData;
import digi.joy.mandala.application.adapters.infra.schema.WorkspaceSessionData;

import java.util.List;
import java.util.UUID;

public record WorkspaceSummary(
        UUID workspaceId,
        String workspaceName,
        List<CommittedNoteData> committedNotes,
        List<WorkspaceSessionData> workspaceSessions
) {
}
