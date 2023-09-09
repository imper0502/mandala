package digi.joy.mandala.workspace.repository;

import digi.joy.mandala.workspace.CommittedNote;
import digi.joy.mandala.workspace.Workspace;
import digi.joy.mandala.workspace.WorkspaceSession;
import digi.joy.mandala.workspace.handler.WorkspaceSummary;

public class WorkspaceConverter {
    public static WorkspaceData transform(Workspace w) {
        WorkspaceData data = new WorkspaceData();
        data.setWorkspaceId(w.getWorkspaceId());
        data.setWorkspaceName(w.getWorkspaceName());
        data.setCommittedNotes(w.getCommittedNotes().stream().map(WorkspaceConverter::transform).toList());
        data.setWorkspaceSessions(w.getWorkspaceSessions().stream().map(WorkspaceConverter::transform).toList());

        return data;
    }

    public static Workspace transform(WorkspaceData data) {
        Workspace w = Workspace.builder()
                .workspaceId(data.getWorkspaceId())
                .workspaceName(data.getWorkspaceName())
                .build();
        data.getCommittedNotes().forEach(x -> w.commitNote(x.getNoteId()));
        data.getWorkspaceSessions().forEach(x -> w.add(x.getUserId()));

        return w;
    }

    public static CommittedNoteData transform(CommittedNote note) {
        CommittedNoteData data = new CommittedNoteData();
        data.setNoteId(note.noteId());
        return data;
    }

    public static WorkspaceSessionData transform(WorkspaceSession session) {
        WorkspaceSessionData data = new WorkspaceSessionData();
        data.setUserId(session.userId());
        data.setWorkspaceId(session.workspaceId());
        return data;
    }

    public static WorkspaceSummary wrap(WorkspaceData data) {
        return new WorkspaceSummary(
                data.getWorkspaceId(),
                data.getWorkspaceName(),
                data.getCommittedNotes(),
                data.getWorkspaceSessions()
        );
    }
}
