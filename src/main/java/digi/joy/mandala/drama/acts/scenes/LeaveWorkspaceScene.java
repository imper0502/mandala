package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.actors.Workspace;
import digi.joy.mandala.drama.acts.WorkspaceRepository;
import digi.joy.mandala.drama.acts.scenes.contexts.LeaveWorkspaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveWorkspaceScene {
    private final WorkspaceRepository repository;
    private final MandalaEventBus eventBus;

    @Autowired
    public LeaveWorkspaceScene(WorkspaceRepository repository, MandalaEventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    public void play(LeaveWorkspaceContext context) {
        Workspace w = repository.withdraw(context.getWorkspaceId());
        eventBus.commit(
                w.remove(context.getUserId())
        );
        repository.add(w);
        eventBus.postAll();
    }
}
