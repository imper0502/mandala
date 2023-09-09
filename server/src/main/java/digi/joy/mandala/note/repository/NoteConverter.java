package digi.joy.mandala.note.repository;

import digi.joy.mandala.note.Note;

public class NoteConverter {
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
