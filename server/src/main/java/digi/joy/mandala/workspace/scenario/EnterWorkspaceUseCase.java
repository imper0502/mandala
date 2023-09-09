package digi.joy.mandala.workspace.scenario;

import digi.joy.mandala.infra.repository.RepositoryException;

public interface EnterWorkspaceUseCase {
    void enterWorkspace(EnterWorkspaceContext context) throws RepositoryException;
}
