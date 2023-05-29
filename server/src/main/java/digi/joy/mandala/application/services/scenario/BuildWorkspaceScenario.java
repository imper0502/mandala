package digi.joy.mandala.application.services.scenario;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.application.entities.Workspace;
import digi.joy.mandala.application.services.infra.WorkspaceRepository;
import digi.joy.mandala.application.services.context.BuildWorkspaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BuildWorkspaceScenario {

    private final WorkspaceRepository repository;
    private final MandalaEventBus eventListener;

    @Autowired
    public BuildWorkspaceScenario(WorkspaceRepository repository, MandalaEventBus eventListener) {
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
