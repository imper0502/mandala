package digi.joy.mandala.workspace.services.infra;

import digi.joy.mandala.workspace.adapters.gateway.schema.NoteData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NoteDataAccessor {
    void add(NoteData note);
    Optional<NoteData> withdraw(UUID id);
    Optional<NoteData> query(UUID id);
    List<NoteData> query(List<UUID> list);
}
