package digi.joy.mandala.application.adapters.api.published;

import digi.joy.mandala.application.adapters.infra.schema.NoteData;
import digi.joy.mandala.application.adapters.infra.schema.WorkspaceSessionData;

import java.util.List;

public record WorkspaceDetail(
        String workspaceName,
        List<NoteData> committedNotes,
        List<WorkspaceSessionData> workspaceSessions
) {
}
