package digi.joy.mandala.drama.acts.mapper;

import digi.joy.mandala.drama.actors.Workspace;
import digi.joy.mandala.drama.actors.association.CommittedNote;
import digi.joy.mandala.drama.actors.association.WorkspaceSession;
import digi.joy.mandala.drama.adapters.api.published.WorkspaceInfo;
import digi.joy.mandala.drama.adapters.infra.schema.CommittedNoteData;
import digi.joy.mandala.drama.adapters.infra.schema.WorkspaceData;
import digi.joy.mandala.drama.adapters.infra.schema.WorkspaceSessionData;

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

    public static WorkspaceInfo wrap(WorkspaceData data) {
        return new WorkspaceInfo(
                data.getWorkspaceId(),
                data.getWorkspaceName(),
                data.getCommittedNotes(),
                data.getWorkspaceSessions()
        );
    }
}
