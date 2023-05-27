package digi.joy.mandala.workspace.services.usecase;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.workspace.entities.Workspace;
import digi.joy.mandala.workspace.entities.WorkspaceSession;
import digi.joy.mandala.workspace.services.WorkspaceRepository;
import digi.joy.mandala.workspace.services.usecase.input.InputOfEnterWorkspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EnterWorkspace {
    private final WorkspaceRepository repository;

    private final MandalaEventBus eventListener;

    @Autowired
    public EnterWorkspace(WorkspaceRepository repository, MandalaEventBus eventListener) {
        this.repository = repository;
        this.eventListener = eventListener;
    }

    public void execute(InputOfEnterWorkspace input) {
        WorkspaceSession workspaceSession = WorkspaceSession.builder()
                .sessionId(UUID.randomUUID())
                .userId(input.getUserId())
                .workspaceId(input.getWorkspaceId())
                .build();

        Workspace workspace = repository.withdraw(input.getWorkspaceId());
        eventListener.commit(workspace.add(workspaceSession));
        repository.add(workspace);
        eventListener.postAll();
    }
}
