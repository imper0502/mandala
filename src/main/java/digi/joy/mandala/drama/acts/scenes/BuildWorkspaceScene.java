package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.actors.Workspace;
import digi.joy.mandala.drama.acts.WorkspaceRepository;
import digi.joy.mandala.drama.acts.scenes.contexts.BuildWorkspaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BuildWorkspaceScene {

    private final WorkspaceRepository repository;
    private final MandalaEventBus eventListener;

    @Autowired
    public BuildWorkspaceScene(WorkspaceRepository repository, MandalaEventBus eventListener) {
        this.repository = repository;
        this.eventListener = eventListener;
    }

    public UUID play(BuildWorkspaceContext input) {
        UUID workspaceId = Optional.ofNullable(input.getWorkspaceId()).orElse(UUID.randomUUID());
        Workspace workspace = Workspace.builder()
                .workspaceId(workspaceId)
                .workspaceName(input.getWorkspaceName())
                .build();

        repository.add(workspace);
        eventListener.commit(workspace.workspaceBuiltEvent());
        eventListener.postAll();

        return workspaceId;
    }
}
