package digi.joy.mandala.workspace.services.scenario;

import digi.joy.mandala.common.services.infra.exception.RepositoryException;
import digi.joy.mandala.workspace.services.scenario.context.BuildWorkspaceContext;

import java.util.UUID;

public interface BuildWorkspaceUseCase {
    UUID buildWorkspace(BuildWorkspaceContext input) throws RepositoryException;
}
