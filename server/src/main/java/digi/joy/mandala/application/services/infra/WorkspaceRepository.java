package digi.joy.mandala.application.services.infra;

import digi.joy.mandala.application.entities.Workspace;
import digi.joy.mandala.application.services.utils.WorkspaceMapper;
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

    public UUID add(Workspace w) {
        dataAccessor.add(WorkspaceMapper.transform(w));
        return w.getWorkspaceId();
    }

    public Workspace query(UUID workspaceId) {
        return WorkspaceMapper.transform(dataAccessor.query(workspaceId).orElseThrow());
    }

    public Workspace withdraw(UUID workspaceId) {
        return WorkspaceMapper.transform(dataAccessor.withdraw(workspaceId).orElseThrow());
    }
}
