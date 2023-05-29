package digi.joy.mandala.application.services.utils;

import digi.joy.mandala.application.adapters.handler.published.WorkspaceSummary;
import digi.joy.mandala.application.adapters.gateway.schema.CommittedNoteData;
import digi.joy.mandala.application.adapters.gateway.schema.WorkspaceData;
import digi.joy.mandala.application.adapters.gateway.schema.WorkspaceSessionData;
import digi.joy.mandala.application.entities.Workspace;
import digi.joy.mandala.application.entities.association.CommittedNote;
import digi.joy.mandala.application.entities.association.WorkspaceSession;

public class WorkspaceMapper {
    public static WorkspaceData transform(Workspace w) {
        WorkspaceData data = new WorkspaceData();
        data.setWorkspaceId(w.getWorkspaceId());
        data.setWorkspaceName(w.getWorkspaceName());
        data.setCommittedNotes(w.getCommittedNotes().stream().map(WorkspaceMapper::transform).toList());
        data.setWorkspaceSessions(w.getWorkspaceSessions().stream().map(WorkspaceMapper::transform).toList());

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
