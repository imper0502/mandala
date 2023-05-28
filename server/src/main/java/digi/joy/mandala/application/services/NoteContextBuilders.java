package digi.joy.mandala.application.services;

import digi.joy.mandala.application.services.scenario.context.CreateNoteContext;

public class NoteContextBuilders {

    public static CreateNoteContext.CreateNoteContextBuilder createNoteScene() {
        return CreateNoteContext.builder();
    }
}
