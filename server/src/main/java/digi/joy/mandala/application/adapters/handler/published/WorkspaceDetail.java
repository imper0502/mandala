package digi.joy.mandala.application.adapters.handler.published;

import digi.joy.mandala.application.adapters.gateway.schema.NoteData;
import digi.joy.mandala.application.adapters.gateway.schema.WorkspaceSessionData;

import java.util.List;

public record WorkspaceDetail(
        String workspaceName,
        List<NoteData> committedNotes,
        List<WorkspaceSessionData> workspaceSessions
) {
}
