package digi.joy.mandala.note.repository;

import digi.joy.mandala.note.StructuredNote;
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
    private StructuredNote parent;
    private StructuredNote[] children;
    private UUID createdBy;
    private ZonedDateTime createdTime;
    private UUID updatedBy;
    private ZonedDateTime updatedTime;
}
