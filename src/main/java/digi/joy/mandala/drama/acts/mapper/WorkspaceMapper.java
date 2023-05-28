package digi.joy.mandala.drama.acts.mapper;

import digi.joy.mandala.drama.adapters.api.published.WorkspaceInfo;
import digi.joy.mandala.drama.adapters.infra.schema.CommittedNoteData;
import digi.joy.mandala.drama.adapters.infra.schema.WorkspaceData;
import digi.joy.mandala.drama.adapters.infra.schema.WorkspaceSessionData;
import digi.joy.mandala.drama.actors.Workspace;
import digi.joy.mandala.drama.actors.WorkspaceSession;
import digi.joy.mandala.drama.actors.association.CommittedNote;

import java.util.List;
import java.util.UUID;

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
        data.getCommittedNotes().forEach(x -> w.commitNote(UUID.fromString(x.getNoteId())));
        List<WorkspaceSession> sessions = data.getWorkspaceSessions().stream()
                .map(x -> new WorkspaceSession(x.getUserId(), x.getWorkspaceId()))
                .toList();
        sessions.forEach(w::add);

        return w;
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
