package digi.joy.mandala.drama.actors;

import digi.joy.mandala.drama.actors.event.NoteCreated;
import digi.joy.mandala.drama.actors.event.NoteCreatedInWorkspace;
import digi.joy.mandala.drama.actors.event.NoteUpdated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private UUID noteId;
    private String title;
    private final List<String> content = new ArrayList<>();
    private ZonedDateTime createDateTime;

    public NoteCreated noteCreatedEvent() {
        return new NoteCreated(noteId);
    }

    public NoteUpdated append(List<String> newContent) {
        content.addAll(newContent);
        return new NoteUpdated(noteId);
    }

    public NoteCreatedInWorkspace noteCreatedEvent(UUID workspaceId) {
        return new NoteCreatedInWorkspace(noteId, workspaceId);
    }
}
