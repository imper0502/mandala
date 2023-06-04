package digi.joy.mandala.note.services;

import digi.joy.mandala.note.services.scenario.context.CreateNoteContext;

public class NoteContextBuilders {

    public static CreateNoteContext.CreateNoteContextBuilder createNoteScene() {
        return CreateNoteContext.builder();
    }
}
