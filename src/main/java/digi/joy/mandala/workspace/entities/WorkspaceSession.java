package digi.joy.mandala.workspace.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceSession {
    private UUID sessionId;
    private String userId;
    private String workspaceId;
}
