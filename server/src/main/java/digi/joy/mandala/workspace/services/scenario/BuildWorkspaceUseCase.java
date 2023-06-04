package digi.joy.mandala.workspace.services.scenario;

import digi.joy.mandala.workspace.services.context.BuildWorkspaceContext;
import digi.joy.mandala.workspace.services.infra.exception.RepositoryException;

import java.util.UUID;

public interface BuildWorkspaceUseCase {
    UUID buildWorkspace(BuildWorkspaceContext input) throws RepositoryException;
}
