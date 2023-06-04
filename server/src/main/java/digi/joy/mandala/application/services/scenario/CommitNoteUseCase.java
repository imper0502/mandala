package digi.joy.mandala.application.services.scenario;

import digi.joy.mandala.application.services.context.CommitNoteContext;
import digi.joy.mandala.application.services.infra.exception.RepositoryException;

public interface CommitNoteUseCase {
    void commitNote(CommitNoteContext context) throws RepositoryException;
}
