package digi.joy.mandala.workspace.services.scenario;

import digi.joy.mandala.workspace.services.context.CreateNoteContext;

import java.util.UUID;

public interface CreateNoteUseCase {
    UUID createNote(CreateNoteContext context);
}
