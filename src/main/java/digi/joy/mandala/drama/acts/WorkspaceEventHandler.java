package digi.joy.mandala.drama.acts;

import com.google.common.eventbus.Subscribe;
import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.common.services.MandalaEventHandler;
import digi.joy.mandala.drama.actors.Workspace;
import digi.joy.mandala.drama.actors.event.NoteCreatedInWorkspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "WorkspaceDaemon")
public final class WorkspaceEventHandler extends MandalaEventHandler {
    private final WorkspaceRepository repository;
    private final MandalaEventBus eventBus;

    @Autowired
    public WorkspaceEventHandler(WorkspaceRepository repository, MandalaEventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Subscribe
    public void handle(NoteCreatedInWorkspace event) {
        Workspace workspace = repository.withdraw(event.getWorkspaceId());
        eventBus.commit(
                workspace.commitNote(event.getNoteId())
        );
        repository.add(workspace);
        eventBus.postAll();
    }
}
