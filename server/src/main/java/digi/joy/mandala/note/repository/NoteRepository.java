package digi.joy.mandala.note.repository;

import digi.joy.mandala.note.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteRepository {

    private final NoteRepositoryOperator operator;

    public void add(Note note) {
        operator.add(NoteConverter.transform(note));
    }

    public Note withdraw(UUID id) {
        return NoteConverter.transform(operator.withdraw(id).orElseThrow());
    }

    public Note query(UUID id) {
        return NoteConverter.transform(operator.query(id).orElseThrow());
    }
}
