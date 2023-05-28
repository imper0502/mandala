package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.actors.Workspace;
import digi.joy.mandala.drama.acts.WorkspaceRepository;
import digi.joy.mandala.drama.acts.scenes.contexts.BuildWorkspaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildWorkspaceScene {

    private final WorkspaceRepository repository;
    private final MandalaEventBus eventListener;

    @Autowired
    public BuildWorkspaceScene(WorkspaceRepository repository, MandalaEventBus eventListener) {
        this.repository = repository;
        this.eventListener = eventListener;
    }

    public void play(BuildWorkspaceContext input) {
        Workspace workspace = Workspace.builder()
                .workspaceId(input.getWorkspaceId())
                .workspaceName(input.getWorkspaceName())
                .build();

        repository.add(workspace);
        eventListener.commit(workspace.workspaceBuiltEvent());
        eventListener.postAll();
    }
}
