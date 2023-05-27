package digi.joy.mandala.workspace.services.usecase.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InputOfBuildWorkspace {
    private String workspaceId;
    private String workspaceName;
}
