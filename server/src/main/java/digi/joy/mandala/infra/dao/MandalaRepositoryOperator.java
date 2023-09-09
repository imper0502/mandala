package digi.joy.mandala.infra.dao;

import java.util.Optional;

public interface MandalaRepositoryOperator<Id, Data> {
    void add(Data data);

    Optional<Data> remove(Id id);
}
