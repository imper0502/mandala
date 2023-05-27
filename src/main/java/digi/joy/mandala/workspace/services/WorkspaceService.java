package digi.joy.mandala.workspace.services;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.note.entities.event.NoteCreatedInWorkspace;
import digi.joy.mandala.workspace.entities.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkspaceService {
    private final WorkspaceRepository repository;

    private final MandalaEventBus eventBus;

    @Autowired
    public WorkspaceService(
            WorkspaceRepository repository, MandalaEventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
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
