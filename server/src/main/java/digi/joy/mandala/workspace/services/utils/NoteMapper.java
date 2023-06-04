package digi.joy.mandala.workspace.services.utils;

import digi.joy.mandala.workspace.adapters.gateway.schema.NoteData;
import digi.joy.mandala.workspace.entities.Note;

public class NoteMapper {
    public static NoteData transform(Note note) {
        return NoteData.builder()
                .noteId(note.getNoteId())
                .title(note.getTitle())
                .createDateTime(note.getCreateDateTime())
                .content(note.getContent())
                .build();
    }

    public static Note transform(NoteData data) {
        Note note = Note.builder()
                .noteId(data.getNoteId())
                .title(data.getTitle())
                .createDateTime(data.getCreateDateTime())
                .build();
        note.append(data.getContent());
        return note;
    }
}
