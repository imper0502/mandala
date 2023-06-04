package digi.joy.mandala.workspace.services.scenario.context;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnterWorkspaceContext {
    private UUID userId;
    private UUID workspaceId;
}
