package digi.joy.mandala.workspace.services.scenario;

import digi.joy.mandala.common.services.exception.RepositoryException;
import digi.joy.mandala.workspace.services.scenario.context.EnterWorkspaceContext;

public interface EnterWorkspaceUseCase {
    void enterWorkspace(EnterWorkspaceContext context) throws RepositoryException;
}
