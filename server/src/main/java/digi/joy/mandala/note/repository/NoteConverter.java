package digi.joy.mandala.note.repository;

import digi.joy.mandala.note.Note;
import org.springframework.beans.BeanUtils;

public class NoteConverter {
    public static NoteData transform(Note model) {
        final NoteData data = NoteData.builder().build();
        BeanUtils.copyProperties(model, data);
        return data;
    }

    public static Note transform(NoteData data) {
        final Note model = Note.builder().build();
        BeanUtils.copyProperties(data, model);
        System.arraycopy(data.getChildren(), 0, model.getChildren(), 0, data.getChildren().length);
        return model;
    }
}
