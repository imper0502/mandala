package digi.joy.mandala.drama.acts;

import digi.joy.mandala.drama.acts.mapper.WorkspaceMapper;
import digi.joy.mandala.drama.actors.Workspace;
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

    public Workspace withdraw(UUID workspaceId) {
        return WorkspaceMapper.transform(dataAccessor.withdraw(workspaceId).orElseThrow());
    }
}
