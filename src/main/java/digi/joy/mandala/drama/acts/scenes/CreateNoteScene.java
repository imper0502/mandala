package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.actors.Note;
import digi.joy.mandala.drama.acts.NoteRepository;
import digi.joy.mandala.drama.acts.scenes.contexts.CreateNoteContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CreateNoteScene {
    private final NoteRepository noteRepository;

    private final MandalaEventBus eventListener;

    @Autowired
    public CreateNoteScene(NoteRepository noteRepository, MandalaEventBus eventListener) {
        this.noteRepository = noteRepository;
        this.eventListener = eventListener;
    }

    public UUID play(CreateNoteContext context) {
        UUID noteId = Optional.ofNullable(context.getNoteId()).orElse(UUID.randomUUID());
        Note note = Note.builder()
                .noteId(noteId)
                .title(context.getTitle())
                .createDateTime(ZonedDateTime.now())
                .build();
        note.append(context.getContent());

        noteRepository.add(note);

        Optional<UUID> workspaceId = Optional.ofNullable(context.getWorkspaceId());
        workspaceId.ifPresentOrElse(
                id -> eventListener.commit(note.noteCreatedEvent(id)),
                () -> eventListener.commit(note.noteCreatedEvent())
        );

        eventListener.postAll();
        return noteId;
    }
}
