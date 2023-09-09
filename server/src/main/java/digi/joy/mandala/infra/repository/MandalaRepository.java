package digi.joy.mandala.infra.repository;

import digi.joy.mandala.infra.dao.DAOException;

public abstract class MandalaRepository<Id, Entity> {
    public abstract Id deposit(Entity entity) throws RepositoryException, DAOException;

    public abstract Entity withdraw(Id id);
}
