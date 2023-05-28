package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.actors.Note;
import digi.joy.mandala.drama.acts.NoteRepository;
import digi.joy.mandala.drama.acts.scenes.contexts.InputOfCreateNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class CreateNote {

    private final NoteRepository repository;
    private final MandalaEventBus eventListener;

    @Autowired
    public CreateNote(NoteRepository repository, MandalaEventBus eventListener) {
        this.repository = repository;
        this.eventListener = eventListener;
    }

    public void execute(InputOfCreateNote input) {
        Note note = Note.builder()
                .noteId(UUID.randomUUID())
                .createDateTime(ZonedDateTime.now())
                .title(input.getTitle())
                .build();
        note.append(input.getContent());

        repository.add(note);

        eventListener.commit(note.noteCreatedEvent());
        eventListener.postAll();
    }
}
