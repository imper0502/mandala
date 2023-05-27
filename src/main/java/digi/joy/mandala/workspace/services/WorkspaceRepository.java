package digi.joy.mandala.workspace.services;

import digi.joy.mandala.workspace.adapters.infra.WorkspaceMapper;
import digi.joy.mandala.workspace.entities.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceRepository {

    private final WorkspaceDataAccessor dataAccessor;

    @Autowired
    public WorkspaceRepository(WorkspaceDataAccessor dataAccessor) {
        this.dataAccessor = dataAccessor;
    }

    public String add(Workspace w) {
        dataAccessor.add(WorkspaceMapper.transform(w));
        return w.getWorkspaceId();
    }

    public Workspace withdraw(String workspaceId) {
        return WorkspaceMapper.transform(dataAccessor.withdraw(workspaceId).orElseThrow());
    }
}
