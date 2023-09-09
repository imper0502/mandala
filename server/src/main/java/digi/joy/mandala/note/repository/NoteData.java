package digi.joy.mandala.note.repository;

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
    private List<String> content;
    private String createdBy;
    private ZonedDateTime createdTime;
    private String updatedBy;
    private ZonedDateTime updatedTime;
}
