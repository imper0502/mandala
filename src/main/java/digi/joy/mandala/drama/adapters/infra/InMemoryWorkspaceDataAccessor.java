package digi.joy.mandala.drama.adapters.infra;

import digi.joy.mandala.drama.acts.WorkspaceDataAccessor;
import digi.joy.mandala.drama.adapters.infra.schema.WorkspaceData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InMemoryWorkspaceDataAccessor implements WorkspaceDataAccessor {
    private final List<WorkspaceData> workspaces = new ArrayList<>();

    public void add(WorkspaceData w) {
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
