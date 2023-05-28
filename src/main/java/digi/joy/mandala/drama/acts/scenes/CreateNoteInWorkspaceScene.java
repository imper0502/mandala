package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.actors.Note;
import digi.joy.mandala.drama.acts.NoteRepository;
import digi.joy.mandala.drama.acts.scenes.contexts.CreateNoteInWorkspaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class CreateNoteInWorkspaceScene {
    private final NoteRepository noteRepository;

    private final MandalaEventBus eventListener;

    @Autowired
    public CreateNoteInWorkspaceScene(NoteRepository noteRepository, MandalaEventBus eventListener) {
        this.noteRepository = noteRepository;
        this.eventListener = eventListener;
    }

    public void play(CreateNoteInWorkspaceContext input) {
        Note note = Note.builder()
                .noteId(UUID.randomUUID())
                .title(input.getTitle())
                .createDateTime(ZonedDateTime.now())
                .build();
        note.append(input.getContent());
        noteRepository.add(note);
        eventListener.commit(
                note.noteCreatedEvent(input.getWorkspaceId())
        );
        eventListener.postAll();
    }
}
