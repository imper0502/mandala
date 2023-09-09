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

    @Override
    public void add(NoteData note) {
        notes.add(note);
    }

    @Override
    public Optional<NoteData> remove(UUID id) {
        Optional<NoteData> note = get(id);

        note.ifPresent(notes::remove);

        return note;
    }

    @Override
    public Optional<NoteData> get(UUID id) {
        return notes.stream()
                .filter(x -> x.getNoteId().equals(id))
                .findFirst();
    }

    @Override
    public List<NoteData> query(List<UUID> list) {
        return notes.stream().filter(x -> list.contains(x.getNoteId())).toList();
    }
}
