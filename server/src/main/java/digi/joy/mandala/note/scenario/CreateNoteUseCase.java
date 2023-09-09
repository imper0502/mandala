package digi.joy.mandala.note.scenario;

import java.util.UUID;

public interface CreateNoteUseCase {
    UUID createNote(CreateNoteContext context);
}
