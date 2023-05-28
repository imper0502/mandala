package digi.joy.mandala.drama.services;

import digi.joy.mandala.drama.services.scenario.BuildWorkspaceScenario;
import digi.joy.mandala.drama.services.scenario.EnterWorkspaceScenario;
import digi.joy.mandala.drama.services.scenario.LeaveWorkspaceScenario;
import digi.joy.mandala.drama.services.scenario.context.BuildWorkspaceContext;
import digi.joy.mandala.drama.services.scenario.context.EnterWorkspaceContext;
import digi.joy.mandala.drama.services.scenario.context.LeaveWorkspaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WorkspaceService {

    private final BuildWorkspaceScenario buildWorkspaceScenario;
    private final EnterWorkspaceScenario enterWorkspaceScenario;
    private final LeaveWorkspaceScenario leaveWorkspaceScenario;

    @Autowired
    public WorkspaceService(BuildWorkspaceScenario buildWorkspaceScenario, EnterWorkspaceScenario enterWorkspaceScenario, LeaveWorkspaceScenario leaveWorkspaceScenario) {
        this.buildWorkspaceScenario = buildWorkspaceScenario;
        this.enterWorkspaceScenario = enterWorkspaceScenario;
        this.leaveWorkspaceScenario = leaveWorkspaceScenario;
    }


    public UUID buildWorkspaceScene(BuildWorkspaceContext context) {
        return buildWorkspaceScenario.play(context);
    }

    public void enterWorkspaceScene(EnterWorkspaceContext context) {
        enterWorkspaceScenario.play(context);
    }

    public void leaveWorkspaceScene(LeaveWorkspaceContext context) {
        leaveWorkspaceScenario.play(context);
    }
}
