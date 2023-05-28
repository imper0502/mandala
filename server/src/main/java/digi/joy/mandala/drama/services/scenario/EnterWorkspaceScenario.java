package digi.joy.mandala.drama.services.scenario;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.entities.Workspace;
import digi.joy.mandala.drama.services.WorkspaceRepository;
import digi.joy.mandala.drama.services.scenario.context.EnterWorkspaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnterWorkspaceScenario {
    private final WorkspaceRepository repository;

    private final MandalaEventBus eventListener;

    @Autowired
    public EnterWorkspaceScenario(WorkspaceRepository repository, MandalaEventBus eventListener) {
        this.repository = repository;
        this.eventListener = eventListener;
    }

    public void play(EnterWorkspaceContext context) {
        Workspace workspace = repository.withdraw(context.getWorkspaceId());
        eventListener.commit(
                workspace.add(context.getUserId())
        );
        repository.add(workspace);

        eventListener.postAll();
    }
}
