package digi.joy.mandala.note;

import com.fasterxml.jackson.annotation.JsonFormat;
import digi.joy.mandala.note.event.NoteCreated;
import digi.joy.mandala.note.event.NoteUpdated;
import digi.joy.mandala.workspace.event.WorkspaceNoteCreated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private UUID noteId;
    private String title;
    private final List<String> content = new ArrayList<>();
    private String createdBy;
    @JsonFormat(pattern = "YYYY-MM-DD'T'hh:mm:ssXXX")
    private ZonedDateTime createdTime;
    private String updatedBy;
    @JsonFormat(pattern = "YYYY-MM-DD'T'hh:mm:ssXXX")
    private ZonedDateTime updatedTime;

    public static NoteCreated createNote(UUID noteId, String title, String author, List<String> content, Consumer<Note> action) {
        final ZonedDateTime now = ZonedDateTime.now();
        final Note note = builder()
                .noteId(noteId)
                .title(title)
                .createdBy(author)
                .createdTime(now)
                .updatedBy(author)
                .updatedTime(now)
                .build();
        note.append(content);
        action.accept(note);
        return new NoteCreated(noteId);
    }

    public static WorkspaceNoteCreated createWorkspaceNote(UUID workspaceId, UUID noteId, String title, String author, List<String> content, Consumer<Note> action) {
        final ZonedDateTime now = ZonedDateTime.now();
        final Note note = builder()
                .noteId(noteId)
                .title(title)
                .createdBy(author)
                .createdTime(now)
                .updatedBy(author)
                .updatedTime(now)
                .build();
        note.append(content);
        action.accept(note);
        return new WorkspaceNoteCreated(noteId, workspaceId);
    }

    public NoteUpdated append(List<String> newContent) {
        content.addAll(newContent);
        return new NoteUpdated(noteId);
    }
}
