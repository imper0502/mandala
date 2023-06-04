package digi.joy.mandala.application.services.scenario;

import digi.joy.mandala.application.services.context.EnterWorkspaceContext;
import digi.joy.mandala.application.services.infra.exception.RepositoryException;

public interface EnterWorkspaceUseCase {
    void enterWorkspace(EnterWorkspaceContext context) throws RepositoryException;
}
