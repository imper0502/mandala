package digi.joy.mandala.drama.adapters.infra;

import digi.joy.mandala.drama.adapters.infra.schema.WorkspaceData;
import digi.joy.mandala.drama.acts.WorkspaceDataAccessor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryWorkspaceDataAccessor implements WorkspaceDataAccessor {
    private final List<WorkspaceData> workspaces = new ArrayList<>();

    public void add(WorkspaceData w) {
        workspaces.add(w);
    }

    public Optional<WorkspaceData> withdraw(String workspaceId) {
        Optional<WorkspaceData> w = query(workspaceId);

        w.ifPresent(workspaces::remove);

        return w;
    }

    public Optional<WorkspaceData> query(String workspaceId) {
        return workspaces.stream()
                .filter(workspace -> workspace.getWorkspaceId().equals(workspaceId))
                .findFirst();
    }

    public List<WorkspaceData> queryAll() {
        return workspaces;
    }
}
