package digi.joy.mandala.workspace.scenario;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LeaveWorkspaceContext {
    private UUID workspaceId;
    private UUID userId;
}
