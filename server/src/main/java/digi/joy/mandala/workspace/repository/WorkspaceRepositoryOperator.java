package digi.joy.mandala.workspace.repository;

import digi.joy.mandala.infra.dao.MandalaRepositoryOperator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceRepositoryOperator extends MandalaRepositoryOperator<UUID, WorkspaceData> {

    Optional<WorkspaceData> get(UUID workspaceId);

    List<WorkspaceData> queryAll();
}
