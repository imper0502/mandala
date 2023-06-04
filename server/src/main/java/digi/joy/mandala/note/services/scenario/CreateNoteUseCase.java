package digi.joy.mandala.note.services.scenario;

import digi.joy.mandala.note.services.scenario.context.CreateNoteContext;

import java.util.UUID;

public interface CreateNoteUseCase {
    UUID createNote(CreateNoteContext context);
}
