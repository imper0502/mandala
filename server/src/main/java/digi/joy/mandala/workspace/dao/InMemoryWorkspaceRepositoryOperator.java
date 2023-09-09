package digi.joy.mandala.workspace.dao;

import digi.joy.mandala.infra.dao.DAOException;
import digi.joy.mandala.workspace.repository.WorkspaceData;
import digi.joy.mandala.workspace.repository.WorkspaceRepositoryOperator;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryWorkspaceRepositoryOperator implements WorkspaceRepositoryOperator {
    private final List<WorkspaceData> workspaces = new ArrayList<>();

    @Override
    public void add(WorkspaceData w) {
        if (get(w.getWorkspaceId()).isPresent()) throw new DAOException();
        workspaces.add(w);
    }

    @Override
    public Optional<WorkspaceData> remove(UUID workspaceId) {
        Optional<WorkspaceData> w = get(workspaceId);
        w.ifPresent(workspaces::remove);

        return w;
    }

    public Optional<WorkspaceData> get(UUID workspaceId) {
        return workspaces.stream()
                .filter(workspace -> workspace.getWorkspaceId().equals(workspaceId))
                .findFirst();
    }

    public List<WorkspaceData> queryAll() {
        return workspaces;
    }
}
