package digi.joy.mandala.application.services.scenario;

import digi.joy.mandala.application.services.context.BuildWorkspaceContext;
import digi.joy.mandala.application.services.infra.exception.RepositoryException;

import java.util.UUID;

public interface BuildWorkspaceUseCase {
    UUID buildWorkspace(BuildWorkspaceContext input) throws RepositoryException;
}
