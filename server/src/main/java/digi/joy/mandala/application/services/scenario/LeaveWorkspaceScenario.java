package digi.joy.mandala.application.services.scenario;

import digi.joy.mandala.application.services.infra.exception.RepositoryException;
import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.application.entities.Workspace;
import digi.joy.mandala.application.services.infra.WorkspaceRepository;
import digi.joy.mandala.application.services.context.LeaveWorkspaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveWorkspaceScenario {
    private final WorkspaceRepository repository;
    private final MandalaEventBus eventBus;

    @Autowired
    public LeaveWorkspaceScenario(WorkspaceRepository repository, MandalaEventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    public void play(LeaveWorkspaceContext context) throws RepositoryException {
        Workspace w = repository.withdraw(context.getWorkspaceId());
        eventBus.commit(
                w.remove(context.getUserId())
        );
        repository.add(w);
        eventBus.postAll();
    }
}
