package digi.joy.mandala.drama.adapters.api.published;

import digi.joy.mandala.drama.adapters.infra.schema.NoteData;
import digi.joy.mandala.drama.adapters.infra.schema.WorkspaceSessionData;

import java.util.List;

public record WorkspaceDetail(
        String workspaceName,
        List<NoteData> committedNotes,
        List<WorkspaceSessionData> workspaceSessions
) {
}
