package digi.joy.mandala.workspace.handler;

import digi.joy.mandala.infra.repository.RepositoryException;
import digi.joy.mandala.workspace.scenario.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WorkspaceCommandHandler {

    private final BuildWorkspaceUseCase buildWorkspaceScenario;
    private final EnterWorkspaceUseCase enterWorkspaceScenario;
    private final LeaveWorkspaceUseCase leaveWorkspaceScenario;

    @Autowired
    public WorkspaceCommandHandler(WorkspaceService workspaceService) {
        this.buildWorkspaceScenario = workspaceService;
        this.enterWorkspaceScenario = workspaceService;
        this.leaveWorkspaceScenario = workspaceService;
    }


    public ResponseEntity<UUID> buildWorkspaceScene(BuildWorkspaceContext context) throws RepositoryException {
        return ResponseEntity.ok(buildWorkspaceScenario.buildWorkspace(context));
    }

    public void enterWorkspaceScene(EnterWorkspaceContext context) throws RepositoryException {
        enterWorkspaceScenario.enterWorkspace(context);
    }

    public void leaveWorkspaceScene(LeaveWorkspaceContext context) throws RepositoryException {
        leaveWorkspaceScenario.leaveWorkspace(context);
    }
}
