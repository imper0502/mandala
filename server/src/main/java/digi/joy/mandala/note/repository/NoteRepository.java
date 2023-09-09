package digi.joy.mandala.note.repository;

import digi.joy.mandala.infra.repository.MandalaRepository;
import digi.joy.mandala.note.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Note> find(List<UUID> ids) {
        return operator.query(ids)
                .stream()
                .map(NoteConverter::transform)
                .toList();
    }

    public Note get(UUID id) {
        return NoteConverter.transform(operator.get(id).orElseThrow());
    }
}
