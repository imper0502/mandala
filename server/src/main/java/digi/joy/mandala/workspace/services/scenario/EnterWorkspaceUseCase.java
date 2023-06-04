package digi.joy.mandala.workspace.services.scenario;

import digi.joy.mandala.workspace.services.context.EnterWorkspaceContext;
import digi.joy.mandala.workspace.services.infra.exception.RepositoryException;

public interface EnterWorkspaceUseCase {
    void enterWorkspace(EnterWorkspaceContext context) throws RepositoryException;
}
