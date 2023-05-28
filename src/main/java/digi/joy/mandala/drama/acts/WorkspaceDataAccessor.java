package digi.joy.mandala.drama.acts;

import digi.joy.mandala.drama.adapters.infra.schema.WorkspaceData;

import java.util.List;
import java.util.Optional;

public interface WorkspaceDataAccessor {
    void add(WorkspaceData w);

    Optional<WorkspaceData> withdraw(String workspaceId);

    Optional<WorkspaceData> query(String workspaceId);

    List<WorkspaceData> queryAll();
}
