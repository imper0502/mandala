package digi.joy.mandala.workspace.repository;

import digi.joy.mandala.infra.exception.DAOException;
import digi.joy.mandala.infra.exception.RepositoryException;
import digi.joy.mandala.workspace.Workspace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkspaceRepository {

    private final WorkspaceRepositoryOperator operator;

    public UUID add(Workspace w) throws RepositoryException {
        try {
            operator.add(WorkspaceConverter.transform(w));
        } catch (DAOException e) {
            throw new RepositoryException();
        }
        return w.getWorkspaceId();
    }

    public Workspace query(UUID workspaceId) {
        return WorkspaceConverter.transform(operator.query(workspaceId).orElseThrow());
    }

    public Workspace withdraw(UUID workspaceId) {
        return WorkspaceConverter.transform(operator.withdraw(workspaceId).orElseThrow());
    }
}
