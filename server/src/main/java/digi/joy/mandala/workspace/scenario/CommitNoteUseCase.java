package digi.joy.mandala.workspace.scenario;

import digi.joy.mandala.infra.exception.RepositoryException;

public interface CommitNoteUseCase {
    void commitNote(CommitNoteContext context) throws RepositoryException;
}
