package digi.joy.mandala.note;

import digi.joy.mandala.note.event.NoteCreated;
import digi.joy.mandala.note.event.NoteUpdated;
import digi.joy.mandala.workspace.event.NoteCreatedWithWorkspaceId;
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

    public NoteCreatedWithWorkspaceId noteCreatedEvent(UUID workspaceId) {
        return new NoteCreatedWithWorkspaceId(noteId, workspaceId);
    }
}
