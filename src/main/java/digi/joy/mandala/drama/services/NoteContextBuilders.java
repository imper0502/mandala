package digi.joy.mandala.drama.services;

import digi.joy.mandala.drama.services.scenario.context.CreateNoteContext;

public class NoteContextBuilders {

    public static CreateNoteContext.CreateNoteContextBuilder createNoteScene() {
        return CreateNoteContext.builder();
    }
}
