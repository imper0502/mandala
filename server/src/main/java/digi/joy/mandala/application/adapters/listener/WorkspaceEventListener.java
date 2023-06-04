package digi.joy.mandala.application.adapters.listener;

import com.google.common.eventbus.Subscribe;
import digi.joy.mandala.application.entities.event.NoteCreatedInWorkspace;
import digi.joy.mandala.application.services.infra.exception.RepositoryException;
import digi.joy.mandala.application.services.scenario.CommitNoteScenario;
import digi.joy.mandala.application.services.utils.WorkspaceContextBuilders;
import digi.joy.mandala.common.services.MandalaEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller(value = "WorkspaceDaemon")
public final class WorkspaceEventListener extends MandalaEventHandler {
    private final CommitNoteScenario commitNoteScenario;

    @Autowired
    public WorkspaceEventListener(CommitNoteScenario commitNoteScenario) {
        this.commitNoteScenario = commitNoteScenario;
    }

    @Subscribe
    public void handle(NoteCreatedInWorkspace event) throws RepositoryException {
        var context = WorkspaceContextBuilders.commitNoteScenario()
                .workspaceId(event.getWorkspaceId())
                .noteId(event.getNoteId())
                .build();
        commitNoteScenario.play(context);
    }
}
