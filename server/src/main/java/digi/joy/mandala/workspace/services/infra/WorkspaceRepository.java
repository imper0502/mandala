package digi.joy.mandala.workspace.services.infra;

import digi.joy.mandala.workspace.adapters.gateway.exception.DAOException;
import digi.joy.mandala.workspace.entities.Workspace;
import digi.joy.mandala.workspace.services.infra.exception.RepositoryException;
import digi.joy.mandala.workspace.services.utils.WorkspaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WorkspaceRepository {

    private final WorkspaceDataAccessor dataAccessor;

    @Autowired
    public WorkspaceRepository(WorkspaceDataAccessor dataAccessor) {
        this.dataAccessor = dataAccessor;
    }

    public UUID add(Workspace w) throws RepositoryException {
        try {
            dataAccessor.add(WorkspaceMapper.transform(w));
        } catch (DAOException e) {
            throw new RepositoryException();
        }
        return w.getWorkspaceId();
    }

    public Workspace query(UUID workspaceId) {
        return WorkspaceMapper.transform(dataAccessor.query(workspaceId).orElseThrow());
    }

    public Workspace withdraw(UUID workspaceId) {
        return WorkspaceMapper.transform(dataAccessor.withdraw(workspaceId).orElseThrow());
    }
}
