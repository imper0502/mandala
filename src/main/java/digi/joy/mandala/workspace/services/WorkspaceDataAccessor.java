package digi.joy.mandala.workspace.services;

import digi.joy.mandala.workspace.adapters.infra.WorkspaceData;

import java.util.List;
import java.util.Optional;

public interface WorkspaceDataAccessor {
    void add(WorkspaceData w);

    Optional<WorkspaceData> withdraw(String workspaceId);

    Optional<WorkspaceData> query(String workspaceId);

    List<WorkspaceData> queryAll();
}
