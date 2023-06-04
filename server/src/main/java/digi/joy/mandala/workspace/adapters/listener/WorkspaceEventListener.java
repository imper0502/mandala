package digi.joy.mandala.workspace.adapters.listener;

import com.google.common.eventbus.Subscribe;
import digi.joy.mandala.workspace.entities.event.NoteCreatedInWorkspace;
import digi.joy.mandala.workspace.services.infra.exception.RepositoryException;
import digi.joy.mandala.workspace.services.scenario.CommitNoteUseCase;
import digi.joy.mandala.workspace.services.utils.WorkspaceContextBuilders;
import digi.joy.mandala.common.services.MandalaEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller(value = "WorkspaceDaemon")
public final class WorkspaceEventListener extends MandalaEventHandler {
    private final CommitNoteUseCase commitNoteScenario;

    @Autowired
    public WorkspaceEventListener(CommitNoteUseCase commitNoteScenario) {
        this.commitNoteScenario = commitNoteScenario;
    }

    @Subscribe
    public void handle(NoteCreatedInWorkspace event) throws RepositoryException {
        var context = WorkspaceContextBuilders.commitNoteScenario()
                .workspaceId(event.getWorkspaceId())
                .noteId(event.getNoteId())
                .build();
        commitNoteScenario.commitNote(context);
    }
}
