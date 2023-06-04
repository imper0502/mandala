package digi.joy.mandala.note.services.scenario.context;

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
    private String title;
    private List<String> content;
    private UUID noteId;
    private UUID workspaceId;
}
