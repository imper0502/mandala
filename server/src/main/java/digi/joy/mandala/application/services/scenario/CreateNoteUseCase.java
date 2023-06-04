package digi.joy.mandala.application.services.scenario;

import digi.joy.mandala.application.services.context.CreateNoteContext;

import java.util.UUID;

public interface CreateNoteUseCase {
    UUID createNote(CreateNoteContext context);
}
