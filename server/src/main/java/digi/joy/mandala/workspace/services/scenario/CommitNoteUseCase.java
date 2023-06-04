package digi.joy.mandala.workspace.services.scenario;

import digi.joy.mandala.common.services.infra.exception.RepositoryException;
import digi.joy.mandala.workspace.services.scenario.context.CommitNoteContext;

public interface CommitNoteUseCase {
    void commitNote(CommitNoteContext context) throws RepositoryException;
}
