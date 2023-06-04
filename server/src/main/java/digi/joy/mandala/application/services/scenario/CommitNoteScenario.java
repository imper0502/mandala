package digi.joy.mandala.application.services.scenario;

import digi.joy.mandala.application.services.context.CommitNoteContext;
import digi.joy.mandala.application.services.infra.WorkspaceRepository;
import digi.joy.mandala.application.services.infra.exception.RepositoryException;
import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.application.entities.Workspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public final class CommitNoteScenario {
    private final WorkspaceRepository repository;
    private final MandalaEventBus eventBus;

    @Autowired
    public CommitNoteScenario(WorkspaceRepository repository, MandalaEventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    public void play(CommitNoteContext context) throws RepositoryException {
        Workspace workspace = repository.withdraw(context.getWorkspaceId());
        eventBus.commit(
                workspace.commitNote(context.getNoteId())
        );
        repository.add(workspace);
        eventBus.postAll();
    }
}
