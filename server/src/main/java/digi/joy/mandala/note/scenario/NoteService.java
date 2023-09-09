package digi.joy.mandala.note.scenario;

import digi.joy.mandala.infra.event.MandalaEventPublisher;
import digi.joy.mandala.note.Note;
import digi.joy.mandala.note.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class NoteService implements CreateNoteUseCase {
    private final NoteRepository noteRepository;
    private final MandalaEventPublisher eventPublisher;

    @Autowired
    public NoteService(NoteRepository noteRepository, @Qualifier("noteEventBus") MandalaEventPublisher eventPublisher) {
        this.noteRepository = noteRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public UUID createNote(CreateNoteContext context) {
        UUID noteId = Optional.ofNullable(context.getNoteId()).orElse(UUID.randomUUID());
        String author = UUID.randomUUID().toString();
        ZonedDateTime now = ZonedDateTime.now();
        Note note = Note.builder()
                .noteId(noteId)
                .title(context.getTitle())
                .createdBy(author)
                .createdTime(now)
                .updatedBy(author)
                .updatedTime(now)
                .build();
        note.append(context.getContent());

        noteRepository.deposit(note);

        Optional<UUID> workspaceId = Optional.ofNullable(context.getWorkspaceId());
        workspaceId.ifPresentOrElse(
                id -> eventPublisher.commit(note.noteCreatedEvent(id)),
                () -> eventPublisher.commit(note.noteCreatedEvent())
        );

        eventPublisher.postAll();
        return noteId;
    }
}
