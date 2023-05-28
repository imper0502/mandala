package digi.joy.mandala.application.services;

import com.google.common.eventbus.Subscribe;
import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.common.services.MandalaEventHandler;
import digi.joy.mandala.application.entities.Workspace;
import digi.joy.mandala.application.entities.event.NoteCreatedInWorkspace;
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
