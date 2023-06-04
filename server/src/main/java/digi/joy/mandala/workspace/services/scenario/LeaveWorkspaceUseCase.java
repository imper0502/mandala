package digi.joy.mandala.workspace.services.scenario;

import digi.joy.mandala.common.services.infra.exception.RepositoryException;
import digi.joy.mandala.workspace.services.scenario.context.LeaveWorkspaceContext;

public interface LeaveWorkspaceUseCase {
    void leaveWorkspace(LeaveWorkspaceContext context) throws RepositoryException;
}
