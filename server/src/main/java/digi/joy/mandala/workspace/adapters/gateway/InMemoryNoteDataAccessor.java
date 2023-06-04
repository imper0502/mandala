package digi.joy.mandala.workspace.adapters.gateway;

import digi.joy.mandala.workspace.adapters.gateway.schema.NoteData;
import digi.joy.mandala.workspace.services.infra.NoteDataAccessor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public class InMemoryNoteDataAccessor implements NoteDataAccessor {
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
