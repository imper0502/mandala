package digi.joy.mandala.workspace.adapters.api.published;

import digi.joy.mandala.note.adapters.infra.NoteData;
import digi.joy.mandala.workspace.adapters.infra.WorkspaceSessionData;

import java.util.List;

public record WorkspaceDetail(
        String workspaceName,
        List<NoteData> committedNotes,
        List<WorkspaceSessionData> workspaceSessions
) {
}
