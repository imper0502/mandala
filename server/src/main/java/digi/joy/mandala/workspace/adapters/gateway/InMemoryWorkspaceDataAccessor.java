package digi.joy.mandala.workspace.adapters.gateway;

import digi.joy.mandala.workspace.adapters.gateway.exception.DAOException;
import digi.joy.mandala.workspace.adapters.gateway.schema.WorkspaceData;
import digi.joy.mandala.workspace.services.infra.WorkspaceDataAccessor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryWorkspaceDataAccessor implements WorkspaceDataAccessor {
    private final List<WorkspaceData> workspaces = new ArrayList<>();

    public void add(WorkspaceData w) throws DAOException {
        if (query(w.getWorkspaceId()).isPresent()) throw new DAOException();
        workspaces.add(w);
    }

    public Optional<WorkspaceData> withdraw(UUID workspaceId) {
        Optional<WorkspaceData> w = query(workspaceId);
        w.ifPresent(workspaces::remove);

        return w;
    }

    public Optional<WorkspaceData> query(UUID workspaceId) {
        return workspaces.stream()
                .filter(workspace -> workspace.getWorkspaceId().equals(workspaceId))
                .findFirst();
    }

    public List<WorkspaceData> queryAll() {
        return workspaces;
    }
}
