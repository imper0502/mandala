package digi.joy.mandala.workspace.services.scenario;

import digi.joy.mandala.workspace.services.context.CommitNoteContext;
import digi.joy.mandala.workspace.services.infra.exception.RepositoryException;

public interface CommitNoteUseCase {
    void commitNote(CommitNoteContext context) throws RepositoryException;
}
