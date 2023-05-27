package digi.joy.mandala.workspace.entities;

import digi.joy.mandala.workspace.entities.association.CommittedNote;
import digi.joy.mandala.workspace.entities.event.WorkspaceBuilt;
import digi.joy.mandala.workspace.entities.event.WorkspaceEntered;
import digi.joy.mandala.workspace.entities.event.WorkspaceUpdated;
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
        return new WorkspaceEntered(workspaceId, workspaceSession.getSessionId());
    }

    public WorkspaceBuilt workspaceBuiltEvent() {
        return new WorkspaceBuilt(workspaceId);
    }

    public WorkspaceUpdated commitNote(UUID noteId) {
        committedNotes.add(new CommittedNote(noteId));
        return new WorkspaceUpdated(workspaceId);
    }
}
