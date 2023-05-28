package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.actors.Note;
import digi.joy.mandala.drama.acts.NoteRepository;
import digi.joy.mandala.drama.acts.scenes.contexts.CreateNoteInWorkspaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;
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

    public UUID play(CreateNoteInWorkspaceContext context) {
        UUID noteId = Optional.ofNullable(context.getNoteId()).orElse(UUID.randomUUID());
        Note note = Note.builder()
                .noteId(noteId)
                .title(context.getTitle())
                .createDateTime(ZonedDateTime.now())
                .build();
        note.append(context.getContent());
        noteRepository.add(note);
        eventListener.commit(
                note.noteCreatedEvent(context.getWorkspaceId())
        );
        eventListener.postAll();
        return noteId;
    }
}
