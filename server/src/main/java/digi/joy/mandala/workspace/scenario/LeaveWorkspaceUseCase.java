package digi.joy.mandala.workspace.scenario;

import digi.joy.mandala.infra.repository.RepositoryException;

public interface LeaveWorkspaceUseCase {
    void leaveWorkspace(LeaveWorkspaceContext context) throws RepositoryException;
}
