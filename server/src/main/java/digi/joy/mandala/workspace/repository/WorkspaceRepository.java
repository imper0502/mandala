package digi.joy.mandala.workspace.repository;

import digi.joy.mandala.infra.repository.MandalaRepository;
import digi.joy.mandala.workspace.Workspace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkspaceRepository extends MandalaRepository<UUID, Workspace> {

    private final WorkspaceRepositoryOperator operator;

    @Override
    public UUID deposit(Workspace w) {
        operator.add(WorkspaceConverter.transform(w));
        return w.getWorkspaceId();
    }

    @Override
    public Workspace withdraw(UUID workspaceId) {
        return WorkspaceConverter.transform(operator.remove(workspaceId).orElseThrow());
    }

    public List<Workspace> findAll() {
        return operator.queryAll().stream().map(WorkspaceConverter::transform).toList();
    }

    public Workspace get(UUID workspaceId) {
        return WorkspaceConverter.transform(operator.get(workspaceId).orElseThrow());
    }

}
