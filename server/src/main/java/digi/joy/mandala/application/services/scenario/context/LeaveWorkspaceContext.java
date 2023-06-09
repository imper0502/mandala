package digi.joy.mandala.application.services.scenario.context;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LeaveWorkspaceContext {
    private UUID workspaceId;
    private UUID userId;
}
