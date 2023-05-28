package digi.joy.mandala.drama.acts;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.actors.event.NoteCreatedInWorkspace;
import digi.joy.mandala.drama.actors.Workspace;
import digi.joy.mandala.drama.acts.scenes.contexts.LeaveWorkspaceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceAct {
    private final WorkspaceRepository repository;

    private final MandalaEventBus eventBus;

    @Autowired
    public WorkspaceAct(
            WorkspaceRepository repository, MandalaEventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }


    public void leaveWorkspaceScene(LeaveWorkspaceContext context) {
        Workspace w = repository.withdraw(context.getWorkspaceId());
        eventBus.commit(
                w.remove(context.getUserId())
        );
        repository.add(w);
        eventBus.postAll();
    }

    public void handle(NoteCreatedInWorkspace event) {
        Workspace workspace = repository.withdraw(event.getWorkspaceId());
        eventBus.commit(
                workspace.commitNote(event.getNoteId())
        );
        repository.add(workspace);
        eventBus.postAll();
    }
}
