package digi.joy.mandala.workspace.scenario;

import digi.joy.mandala.infra.exception.RepositoryException;

import java.util.UUID;

public interface BuildWorkspaceUseCase {
    UUID buildWorkspace(BuildWorkspaceContext input) throws RepositoryException;
}
