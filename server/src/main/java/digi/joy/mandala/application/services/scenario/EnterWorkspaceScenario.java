package digi.joy.mandala.application.services.scenario;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.application.entities.Workspace;
import digi.joy.mandala.application.services.infra.WorkspaceRepository;
import digi.joy.mandala.application.services.context.EnterWorkspaceContext;
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
