package digi.joy.mandala.drama.adapters.infra.schema;

import lombok.Data;

import java.util.List;

@Data
public class WorkspaceData {
    private String workspaceId;

    private String workspaceName;

    private List<CommittedNoteData> committedNotes;

    private List<WorkspaceSessionData> workspaceSessions;

}
