package digi.joy.mandala.note.repository;

import digi.joy.mandala.infra.repository.MandalaRepository;
import digi.joy.mandala.note.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteRepository extends MandalaRepository<UUID, Note> {

    private final NoteRepositoryOperator operator;

    @Override
    public UUID deposit(Note n) {
        operator.add(NoteConverter.transform(n));
        return n.getNoteId();
    }

    @Override
    public Note withdraw(UUID id) {
        return NoteConverter.transform(operator.remove(id).orElseThrow());
    }

    public Note get(UUID id) {
        return NoteConverter.transform(operator.get(id).orElseThrow());
    }
}
