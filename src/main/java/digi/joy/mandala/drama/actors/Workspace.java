package digi.joy.mandala.drama.actors;

import digi.joy.mandala.drama.actors.association.CommittedNote;
import digi.joy.mandala.drama.actors.association.WorkspaceSession;
import digi.joy.mandala.drama.actors.event.WorkspaceBuilt;
import digi.joy.mandala.drama.actors.event.WorkspaceEntered;
import digi.joy.mandala.drama.actors.event.WorkspaceUpdated;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Workspace {
    private UUID workspaceId;
    private String workspaceName;
    private final List<WorkspaceSession> workspaceSessions = new ArrayList<>();
    private final List<CommittedNote> committedNotes = new ArrayList<>();

    public WorkspaceBuilt workspaceBuiltEvent() {
        return new WorkspaceBuilt(workspaceId);
    }

    public WorkspaceEntered add(UUID userId) {
        workspaceSessions.add(new WorkspaceSession(userId, workspaceId));
        return new WorkspaceEntered(workspaceId, userId);
    }

    public WorkspaceUpdated remove(UUID userId) {
        WorkspaceSession session = workspaceSessions.stream()
                .filter(x -> x.userId().equals(userId))
                .findFirst()
                .orElseThrow();
        workspaceSessions.remove(session);
        return new WorkspaceUpdated(workspaceId);
    }

    public WorkspaceUpdated commitNote(UUID noteId) {
        committedNotes.add(new CommittedNote(noteId));
        return new WorkspaceUpdated(workspaceId);
    }
}
