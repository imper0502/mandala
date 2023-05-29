package digi.joy.mandala.application.services.utils;

import digi.joy.mandala.application.services.context.CreateNoteContext;

public class NoteContextBuilders {

    public static CreateNoteContext.CreateNoteContextBuilder createNoteScene() {
        return CreateNoteContext.builder();
    }
}
