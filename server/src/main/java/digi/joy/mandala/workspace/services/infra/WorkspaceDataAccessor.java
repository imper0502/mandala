package digi.joy.mandala.workspace.services.infra;

import digi.joy.mandala.workspace.adapters.gateway.exception.DAOException;
import digi.joy.mandala.workspace.adapters.gateway.schema.WorkspaceData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceDataAccessor {
    void add(WorkspaceData w) throws DAOException;

    Optional<WorkspaceData> withdraw(UUID workspaceId);

    Optional<WorkspaceData> query(UUID workspaceId);

    List<WorkspaceData> queryAll();
}
