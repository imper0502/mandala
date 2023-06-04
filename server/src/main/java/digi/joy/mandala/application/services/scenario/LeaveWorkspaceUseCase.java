package digi.joy.mandala.application.services.scenario;

import digi.joy.mandala.application.services.context.LeaveWorkspaceContext;
import digi.joy.mandala.application.services.infra.exception.RepositoryException;

public interface LeaveWorkspaceUseCase {
    void leaveWorkspace(LeaveWorkspaceContext context) throws RepositoryException;
}
