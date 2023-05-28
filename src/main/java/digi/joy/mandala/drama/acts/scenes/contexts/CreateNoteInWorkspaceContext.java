package digi.joy.mandala.drama.acts.scenes.contexts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNoteInWorkspaceContext {
    private String title;
    private List<String> content;
    private UUID noteId;
    private UUID workspaceId;
}
