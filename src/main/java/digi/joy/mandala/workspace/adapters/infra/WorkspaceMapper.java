package digi.joy.mandala.workspace.adapters.infra;

import digi.joy.mandala.workspace.adapters.api.published.WorkspaceInfo;
import digi.joy.mandala.workspace.entities.Workspace;
import digi.joy.mandala.workspace.entities.WorkspaceSession;
import digi.joy.mandala.workspace.entities.association.CommittedNote;

public class WorkspaceMapper {
    public static WorkspaceData transform(Workspace w) {
        WorkspaceData data = new WorkspaceData();
        data.setWorkspaceId(w.getWorkspaceId());
        data.setWorkspaceName(w.getWorkspaceName());
        data.setCommittedNotes(w.getCommittedNotes().stream().map(WorkspaceMapper::transform).toList());
        data.setWorkspaceSessions(w.getWorkspaceSessions().stream().map(WorkspaceMapper::transform).toList());

        return data;
    }

    public static Workspace transform(WorkspaceData workspaceData) {

        return Workspace.builder()
                .workspaceId(workspaceData.getWorkspaceId())
                .workspaceName(workspaceData.getWorkspaceName())
                .build();
    }

    public static CommittedNoteData transform(CommittedNote note) {
        CommittedNoteData data = new CommittedNoteData();
        data.setNoteId(note.noteId().toString());
        return data;
    }

    public static WorkspaceSessionData transform(WorkspaceSession session) {
        WorkspaceSessionData data = new WorkspaceSessionData();
        data.setUserId(session.getUserId());
        data.setWorkspaceId(session.getWorkspaceId());
        return data;
    }

    public static WorkspaceInfo wrap(WorkspaceData data) {
        return new WorkspaceInfo(data.getWorkspaceName(), data.getCommittedNotes(), data.getWorkspaceSessions());
    }
}
