package digi.joy.mandala.drama.acts.scenes.contexts;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class LeaveWorkspaceContext {
    private UUID sessionId;
    private String workspaceId;
    private String userId;
}
