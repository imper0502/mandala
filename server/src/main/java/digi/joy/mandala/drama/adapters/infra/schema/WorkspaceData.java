package digi.joy.mandala.drama.adapters.infra.schema;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class WorkspaceData {
    private UUID workspaceId;

    private String workspaceName;

    private List<CommittedNoteData> committedNotes;

    private List<WorkspaceSessionData> workspaceSessions;

}
