package digi.joy.mandala.note.services;

import digi.joy.mandala.note.adapters.infra.NoteData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NoteDataAccessor {
    void add(NoteData note);

    Optional<NoteData> withdraw(UUID id);

    List<NoteData> query(List<UUID> list);
}
