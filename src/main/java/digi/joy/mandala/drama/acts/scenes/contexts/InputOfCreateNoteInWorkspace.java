package digi.joy.mandala.drama.acts.scenes.contexts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InputOfCreateNoteInWorkspace {
    private String title;
    private List<String> content;
    private String workspaceId;
}
