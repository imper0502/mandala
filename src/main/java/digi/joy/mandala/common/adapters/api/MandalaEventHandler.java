package digi.joy.mandala.common.adapters.api;

import com.google.common.eventbus.Subscribe;
import digi.joy.mandala.note.entities.event.NoteCreatedInWorkspace;
import digi.joy.mandala.workspace.services.WorkspaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MandalaEventHandler {
    private final WorkspaceService workspaceService;

    @Autowired
    public MandalaEventHandler(WorkspaceService workspaceService) {
        this.workspaceService = workspaceService;
    }

    @Subscribe
    public void when(NoteCreatedInWorkspace event) {
        workspaceService.handle(event);
    }
}
