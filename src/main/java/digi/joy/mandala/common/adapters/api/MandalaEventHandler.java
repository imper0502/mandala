package digi.joy.mandala.common.adapters.api;

import com.google.common.eventbus.Subscribe;
import digi.joy.mandala.drama.actors.event.NoteCreatedInWorkspace;
import digi.joy.mandala.drama.acts.WorkspaceAct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MandalaEventHandler {
    private final WorkspaceAct workspaceAct;

    @Autowired
    public MandalaEventHandler(WorkspaceAct workspaceAct) {
        this.workspaceAct = workspaceAct;
    }

    @Subscribe
    public void when(NoteCreatedInWorkspace event) {
        workspaceAct.handle(event);
    }
}
