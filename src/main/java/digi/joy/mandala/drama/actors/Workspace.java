package digi.joy.mandala.drama.actors;

import digi.joy.mandala.drama.actors.association.CommittedNote;
import digi.joy.mandala.drama.actors.event.WorkspaceBuilt;
import digi.joy.mandala.drama.actors.event.WorkspaceEntered;
import digi.joy.mandala.drama.actors.event.WorkspaceUpdated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Workspace {
    private final List<WorkspaceSession> workspaceSessions = new ArrayList<>();
    private final List<CommittedNote> committedNotes = new ArrayList<>();
    private String workspaceId;
    private String workspaceName;

    public WorkspaceEntered add(WorkspaceSession workspaceSession) {
        workspaceSessions.add(workspaceSession);
        return new WorkspaceEntered(workspaceId, workspaceSession.getUserId());
    }

    public WorkspaceBuilt workspaceBuiltEvent() {
        return new WorkspaceBuilt(workspaceId);
    }

    public WorkspaceUpdated commitNote(UUID noteId) {
        committedNotes.add(new CommittedNote(noteId));
        return new WorkspaceUpdated(workspaceId);
    }

    public WorkspaceUpdated remove(String userId) {
        WorkspaceSession session = workspaceSessions.stream()
                .filter(x -> x.getUserId().equals(userId))
                .findFirst()
                .orElseThrow();
        workspaceSessions.remove(session);
        return new WorkspaceUpdated(workspaceId);
    }
}
