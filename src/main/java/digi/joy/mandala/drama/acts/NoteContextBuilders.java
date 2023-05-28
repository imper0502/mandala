package digi.joy.mandala.drama.acts;

import digi.joy.mandala.drama.acts.scenes.contexts.CreateNoteContext;

public class NoteContextBuilders {

    public static CreateNoteContext.CreateNoteContextBuilder createNoteScene() {
        return CreateNoteContext.builder();
    }
}
