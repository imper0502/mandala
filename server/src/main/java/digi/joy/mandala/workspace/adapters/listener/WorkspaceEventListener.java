package digi.joy.mandala.workspace.adapters.listener;

import com.google.common.eventbus.Subscribe;
import digi.joy.mandala.common.adapters.listener.MandalaEventListener;
import digi.joy.mandala.common.services.infra.exception.RepositoryException;
import digi.joy.mandala.workspace.entities.event.NoteCreatedWithWorkspaceId;
import digi.joy.mandala.workspace.services.WorkspaceContextBuilders;
import digi.joy.mandala.workspace.services.scenario.CommitNoteUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public final class WorkspaceEventListener extends MandalaEventListener {
    private final CommitNoteUseCase commitNoteUseCase;

    @Autowired
    public WorkspaceEventListener(CommitNoteUseCase commitNoteUseCase) {
        this.commitNoteUseCase = commitNoteUseCase;
    }

    @Subscribe
    public void handle(NoteCreatedWithWorkspaceId event) throws RepositoryException {
        var context = WorkspaceContextBuilders.commitNoteContextBuilder()
                .workspaceId(event.getWorkspaceId())
                .noteId(event.getNoteId())
                .build();
        commitNoteUseCase.commitNote(context);
    }
}
