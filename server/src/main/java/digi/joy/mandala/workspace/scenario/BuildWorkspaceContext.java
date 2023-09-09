package digi.joy.mandala.workspace.scenario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BuildWorkspaceContext {
    private UUID workspaceId;
    private String workspaceName;
}
