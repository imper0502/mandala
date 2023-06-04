package digi.joy.mandala.application.adapters.handler;

import digi.joy.mandala.application.services.infra.exception.RepositoryException;
import digi.joy.mandala.application.services.scenario.BuildWorkspaceScenario;
import digi.joy.mandala.application.services.scenario.EnterWorkspaceScenario;
import digi.joy.mandala.application.services.scenario.LeaveWorkspaceScenario;
import digi.joy.mandala.application.services.context.BuildWorkspaceContext;
import digi.joy.mandala.application.services.context.EnterWorkspaceContext;
import digi.joy.mandala.application.services.context.LeaveWorkspaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WorkspaceCommandHandler {

    private final BuildWorkspaceScenario buildWorkspaceScenario;
    private final EnterWorkspaceScenario enterWorkspaceScenario;
    private final LeaveWorkspaceScenario leaveWorkspaceScenario;

    @Autowired
    public WorkspaceCommandHandler(BuildWorkspaceScenario buildWorkspaceScenario, EnterWorkspaceScenario enterWorkspaceScenario, LeaveWorkspaceScenario leaveWorkspaceScenario) {
        this.buildWorkspaceScenario = buildWorkspaceScenario;
        this.enterWorkspaceScenario = enterWorkspaceScenario;
        this.leaveWorkspaceScenario = leaveWorkspaceScenario;
    }


    public ResponseEntity<UUID> buildWorkspaceScene(BuildWorkspaceContext context) throws RepositoryException {
        return ResponseEntity.ok(buildWorkspaceScenario.play(context));
    }

    public void enterWorkspaceScene(EnterWorkspaceContext context) throws RepositoryException {
        enterWorkspaceScenario.play(context);
    }

    public void leaveWorkspaceScene(LeaveWorkspaceContext context) throws RepositoryException {
        leaveWorkspaceScenario.play(context);
    }
}
