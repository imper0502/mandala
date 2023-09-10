package digi.joy.mandala.note.scenario;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class CreateMandalaContext {
    private UUID workspaceId;
    private UUID author;
    private String title;
    private List<String> content;
}
