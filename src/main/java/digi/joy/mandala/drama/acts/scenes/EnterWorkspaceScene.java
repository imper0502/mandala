package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.actors.Workspace;
import digi.joy.mandala.drama.actors.WorkspaceSession;
import digi.joy.mandala.drama.acts.WorkspaceRepository;
import digi.joy.mandala.drama.acts.scenes.contexts.EnterWorkspaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EnterWorkspaceScene {
    private final WorkspaceRepository repository;

    private final MandalaEventBus eventListener;

    @Autowired
    public EnterWorkspaceScene(WorkspaceRepository repository, MandalaEventBus eventListener) {
        this.repository = repository;
        this.eventListener = eventListener;
    }

    public void play(EnterWorkspaceContext context) {
        WorkspaceSession workspaceSession = WorkspaceSession.builder()
                .userId(context.getUserId())
                .workspaceId(context.getWorkspaceId())
                .build();
        Workspace workspace = repository.withdraw(context.getWorkspaceId());

        eventListener.commit(
                workspace.add(workspaceSession)
        );
        repository.add(workspace);

        eventListener.postAll();
    }
}
