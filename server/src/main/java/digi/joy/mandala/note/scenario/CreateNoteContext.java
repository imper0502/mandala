package digi.joy.mandala.note.scenario;

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
public class CreateNoteContext {
    private UUID workspaceId;
    private UUID noteId;
    private UUID author;
    private String title;
    private List<String> content;
}
