package digi.joy.mandala.workspace.services.scenario;

import digi.joy.mandala.workspace.services.context.LeaveWorkspaceContext;
import digi.joy.mandala.workspace.services.infra.exception.RepositoryException;

public interface LeaveWorkspaceUseCase {
    void leaveWorkspace(LeaveWorkspaceContext context) throws RepositoryException;
}
