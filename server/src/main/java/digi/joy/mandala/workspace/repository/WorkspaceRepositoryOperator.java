package digi.joy.mandala.workspace.repository;

import digi.joy.mandala.infra.exception.DAOException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceRepositoryOperator {
    void add(WorkspaceData w) throws DAOException;

    Optional<WorkspaceData> withdraw(UUID workspaceId);

    Optional<WorkspaceData> query(UUID workspaceId);

    List<WorkspaceData> queryAll();
}
