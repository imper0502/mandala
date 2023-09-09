package digi.joy.mandala.workspace.repository;

import lombok.Data;

import java.util.UUID;

@Data
public class WorkspaceSessionData {
    private UUID UserId;
    private UUID WorkspaceId;
}
