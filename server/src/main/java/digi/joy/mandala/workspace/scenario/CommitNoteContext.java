package digi.joy.mandala.workspace.scenario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommitNoteContext {
    private UUID noteId;
    private UUID workspaceId;
}
