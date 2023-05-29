package digi.joy.mandala.application.services.context;

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
