package digi.joy.mandala.application.services;

import digi.joy.mandala.application.adapters.infra.schema.NoteData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NoteDataAccessor {
    void add(NoteData note);
    Optional<NoteData> withdraw(UUID id);
    Optional<NoteData> query(UUID id);
    List<NoteData> query(List<UUID> list);
}
