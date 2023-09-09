package digi.joy.mandala.note.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NoteRepositoryOperator {
    void add(NoteData note);

    Optional<NoteData> withdraw(UUID id);

    Optional<NoteData> query(UUID id);

    List<NoteData> query(List<UUID> list);
}
