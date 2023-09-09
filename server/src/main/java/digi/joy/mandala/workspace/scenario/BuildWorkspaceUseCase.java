package digi.joy.mandala.workspace.scenario;

import digi.joy.mandala.infra.repository.RepositoryException;

import java.util.UUID;

public interface BuildWorkspaceUseCase {
    UUID buildWorkspace(BuildWorkspaceContext input) throws RepositoryException;
}
