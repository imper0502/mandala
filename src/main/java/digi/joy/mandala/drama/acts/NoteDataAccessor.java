package digi.joy.mandala.drama.acts;

import digi.joy.mandala.drama.adapters.infra.schema.NoteData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NoteDataAccessor {
    void add(NoteData note);

    Optional<NoteData> withdraw(UUID id);

    List<NoteData> query(List<UUID> list);
}
