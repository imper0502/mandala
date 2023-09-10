package digi.joy.mandala.note.scenario;

import digi.joy.mandala.infra.event.MandalaEventPublisher;
import digi.joy.mandala.note.Note;
import digi.joy.mandala.note.event.NoteCreated;
import digi.joy.mandala.note.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

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
        UUID author = Optional.of(context.getAuthor()).orElseThrow();
        eventPublisher.commit(
                Note.createNote(
                        context.getWorkspaceId(),
                        noteId,
                        context.getTitle(),
                        author,
                        context.getContent(),
                        noteRepository::deposit
                )
        );
        eventPublisher.postAll();
        return noteId;
    }

    public UUID createMandala(CreateMandalaContext context) {
        UUID noteId = UUID.randomUUID();
        UUID author = Optional.of(context.getAuthor()).orElseThrow();

        eventPublisher.commit(Note.createSpreadMandalaNote(
                context.getWorkspaceId(),
                noteId,
                context.getTitle(),
                author,
                context.getContent(),
                noteRepository::deposit
        ).toArray(new NoteCreated[0]));

        eventPublisher.postAll();
        return noteId;
    }
}
