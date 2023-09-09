package digi.joy.mandala.workspace.handler;

import digi.joy.mandala.note.repository.NoteData;
import digi.joy.mandala.workspace.repository.WorkspaceSessionData;

import java.util.List;

public record WorkspaceDetail(
        String workspaceName,
        List<NoteData> committedNotes,
        List<WorkspaceSessionData> workspaceSessions
) {
}
