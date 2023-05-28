package digi.joy.mandala.application.services;

import digi.joy.mandala.application.adapters.infra.schema.WorkspaceData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceDataAccessor {
    void add(WorkspaceData w);

    Optional<WorkspaceData> withdraw(UUID workspaceId);

    Optional<WorkspaceData> query(UUID workspaceId);

    List<WorkspaceData> queryAll();
}
