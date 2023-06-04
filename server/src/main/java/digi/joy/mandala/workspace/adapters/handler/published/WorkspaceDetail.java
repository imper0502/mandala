package digi.joy.mandala.workspace.adapters.handler.published;

import digi.joy.mandala.note.adapters.gateway.schema.NoteData;
import digi.joy.mandala.workspace.adapters.gateway.schema.WorkspaceSessionData;

import java.util.List;

public record WorkspaceDetail(
        String workspaceName,
        List<NoteData> committedNotes,
        List<WorkspaceSessionData> workspaceSessions
) {
}
