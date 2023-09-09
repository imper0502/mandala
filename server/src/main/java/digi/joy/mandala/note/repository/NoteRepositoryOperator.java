package digi.joy.mandala.note.repository;

import digi.joy.mandala.infra.dao.MandalaRepositoryOperator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NoteRepositoryOperator extends MandalaRepositoryOperator<UUID, NoteData> {

    Optional<NoteData> get(UUID id);

    List<NoteData> query(List<UUID> list);
}
