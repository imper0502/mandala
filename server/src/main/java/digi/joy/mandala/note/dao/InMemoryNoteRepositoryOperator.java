package digi.joy.mandala.note.dao;

import digi.joy.mandala.note.repository.NoteData;
import digi.joy.mandala.note.repository.NoteRepositoryOperator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public class InMemoryNoteRepositoryOperator implements NoteRepositoryOperator {
    private final List<NoteData> notes = new ArrayList<>();

    public void add(NoteData note) {
        notes.add(note);
    }

    public Optional<NoteData> withdraw(UUID id) {
        Optional<NoteData> note = query(id);

        note.ifPresent(notes::remove);

        return note;
    }

    @Override
    public Optional<NoteData> query(UUID id) {
        return notes.stream()
                .filter(x -> x.getNoteId().equals(id))
                .findFirst();
    }

    @Override
    public List<NoteData> query(List<UUID> list) {
        return notes.stream().filter(x -> list.contains(x.getNoteId())).toList();
    }
}
