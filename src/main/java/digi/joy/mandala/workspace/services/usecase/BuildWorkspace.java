package digi.joy.mandala.workspace.services.usecase;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.workspace.entities.Workspace;
import digi.joy.mandala.workspace.services.WorkspaceRepository;
import digi.joy.mandala.workspace.services.usecase.input.InputOfBuildWorkspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildWorkspace {

    private final WorkspaceRepository repository;
    private final MandalaEventBus eventListener;

    @Autowired
    public BuildWorkspace(WorkspaceRepository repository, MandalaEventBus eventListener) {
        this.repository = repository;
        this.eventListener = eventListener;
    }

    public void execute(InputOfBuildWorkspace input) {
        Workspace workspace = Workspace.builder()
                .workspaceId(input.getWorkspaceId())
                .workspaceName(input.getWorkspaceName())
                .build();

        repository.add(workspace);
        eventListener.commit(workspace.workspaceBuiltEvent());
        eventListener.postAll();
    }

    public InputOfBuildWorkspace.InputOfBuildWorkspaceBuilder inputBuilder() {
        return InputOfBuildWorkspace.builder();
    }

}
