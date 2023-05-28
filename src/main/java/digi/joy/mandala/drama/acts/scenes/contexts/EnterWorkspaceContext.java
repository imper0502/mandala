package digi.joy.mandala.drama.acts.scenes.contexts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnterWorkspaceContext {
    private String userId;
    private String workspaceId;
}
