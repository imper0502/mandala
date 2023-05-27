package digi.joy.mandala.note.services.usecase;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.note.entities.Note;
import digi.joy.mandala.note.services.NoteRepository;
import digi.joy.mandala.note.services.usecase.input.InputOfCreateNoteInWorkspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class CreateNoteInWorkspace {
    private final NoteRepository noteRepository;

    private final MandalaEventBus eventListener;

    @Autowired
    public CreateNoteInWorkspace(NoteRepository noteRepository, MandalaEventBus eventListener) {
        this.noteRepository = noteRepository;
        this.eventListener = eventListener;
    }

    public void execute(InputOfCreateNoteInWorkspace input) {
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

    public InputOfCreateNoteInWorkspace.InputOfCreateNoteInWorkspaceBuilder inputBuilder() {
        return InputOfCreateNoteInWorkspace.builder();
    }
}
