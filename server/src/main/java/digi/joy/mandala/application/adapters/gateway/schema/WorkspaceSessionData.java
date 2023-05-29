package digi.joy.mandala.application.adapters.gateway.schema;

import lombok.Data;

import java.util.UUID;

@Data
public class WorkspaceSessionData {
    private UUID UserId;
    private UUID WorkspaceId;
}
