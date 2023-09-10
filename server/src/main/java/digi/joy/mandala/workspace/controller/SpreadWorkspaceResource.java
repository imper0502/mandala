package digi.joy.mandala.workspace.controller;

import digi.joy.mandala.note.Note;
import digi.joy.mandala.workspace.Workspace;
import digi.joy.mandala.workspace.WorkspaceSession;

import java.util.List;
import java.util.UUID;

public record SpreadWorkspaceResource(
        UUID workspaceId,
        String workspaceName,
        List<Note> committedNotes,
        List<WorkspaceSession> workspaceSessions
) {
    public static SpreadWorkspaceResource of(Workspace w, List<Note> committedNotes) {
        return new SpreadWorkspaceResource(
                w.getWorkspaceId(),
                w.getWorkspaceName(),
                committedNotes,
                w.getWorkspaceSessions()
        );
    }
}