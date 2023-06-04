package digi.joy.mandala.workspace.services.utils;

import digi.joy.mandala.workspace.services.context.CreateNoteContext;

public class NoteContextBuilders {

    public static CreateNoteContext.CreateNoteContextBuilder createNoteScene() {
        return CreateNoteContext.builder();
    }
}
