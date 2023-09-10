package digi.joy.mandala.note;

import com.fasterxml.jackson.annotation.JsonFormat;
import digi.joy.mandala.note.event.NoteCreated;
import digi.joy.mandala.note.event.NoteUpdated;
import digi.joy.mandala.workspace.event.WorkspaceNoteCreated;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class Note {
    private UUID noteId;
    private String title;
    private final List<String> content = new ArrayList<>();
    private StructuredNote parent;
    private final StructuredNote[] children = new StructuredNote[8];
    private UUID createdBy;
    @JsonFormat(pattern = "YYYY-MM-DD'T'hh:mm:ssXXX")
    private ZonedDateTime createdTime;
    private UUID updatedBy;
    @JsonFormat(pattern = "YYYY-MM-DD'T'hh:mm:ssXXX")
    private ZonedDateTime updatedTime;

    public static NoteCreated createNote(UUID noteId, String title, UUID author, List<String> content, Consumer<Note> action) {
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

    public static WorkspaceNoteCreated createWorkspaceNote(UUID workspaceId, UUID noteId, String title, UUID author, List<String> content, Consumer<Note> action) {
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

    public static List<WorkspaceNoteCreated> createMandalaNote(
            UUID workspaceId,
            UUID noteId,
            String title,
            UUID author,
            List<String> content,
            Consumer<Note> action) {
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
        Arrays.setAll(note.children, i -> StructuredNote.builder()
                .parentId(noteId)
                .childId(UUID.randomUUID())
                .build());

        final List<WorkspaceNoteCreated> events = Arrays.stream(note.children)
                .map(child -> createWorkspaceNote(workspaceId, child.getChildId(), "", author, Collections.emptyList(), x -> {
                    x.setParent(StructuredNote.builder().parentId(noteId).childId(x.getNoteId()).build());
                    action.accept(x);
                }))
                .collect(Collectors.toList());
        action.accept(note);
        events.add(new WorkspaceNoteCreated(noteId, workspaceId));
        return events;
    }
}
