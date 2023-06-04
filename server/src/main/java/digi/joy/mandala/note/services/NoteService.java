package digi.joy.mandala.note.services;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.note.entities.Note;
import digi.joy.mandala.note.services.infra.NoteRepository;
import digi.joy.mandala.note.services.scenario.CreateNoteUseCase;
import digi.joy.mandala.note.services.scenario.context.CreateNoteContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class NoteService implements CreateNoteUseCase {
    private final NoteRepository noteRepository;
    private final MandalaEventBus eventPublisher;

    @Autowired
    public NoteService(NoteRepository noteRepository, MandalaEventBus eventPublisher) {
        this.noteRepository = noteRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public UUID createNote(CreateNoteContext context) {
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
                id -> eventPublisher.commit(note.noteCreatedEvent(id)),
                () -> eventPublisher.commit(note.noteCreatedEvent())
        );

        eventPublisher.postAll();
        return noteId;
    }
}
