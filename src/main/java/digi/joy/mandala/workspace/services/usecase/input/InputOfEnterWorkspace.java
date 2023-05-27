package digi.joy.mandala.workspace.services.usecase.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InputOfEnterWorkspace {
    private String userId;
    private String workspaceId;
}
