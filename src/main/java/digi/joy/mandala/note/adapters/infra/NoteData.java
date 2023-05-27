package digi.joy.mandala.note.adapters.infra;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class NoteData {
    private UUID noteId;
    private String title;
    private ZonedDateTime createDateTime;
    private List<String> content;
}
