package digi.joy.mandala.workspace.listener;

import com.google.common.eventbus.Subscribe;
import digi.joy.mandala.infra.event.MandalaEventListener;
import digi.joy.mandala.infra.repository.RepositoryException;
import digi.joy.mandala.workspace.event.NoteCreatedWithWorkspaceId;
import digi.joy.mandala.workspace.scenario.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public final class WorkspaceEventListener extends MandalaEventListener {

    private final BuildWorkspaceUseCase buildWorkspaceUseCase;
    private final EnterWorkspaceUseCase enterWorkspaceUseCase;
    private final CommitNoteUseCase commitNoteUseCase;
    private final LeaveWorkspaceUseCase leaveWorkspaceUseCase;

    public ResponseEntity<UUID> buildWorkspaceScene(BuildWorkspaceContext context) throws RepositoryException {
        return ResponseEntity.ok(buildWorkspaceUseCase.buildWorkspace(context));
    }

    public void enterWorkspaceScene(EnterWorkspaceContext context) throws RepositoryException {
        enterWorkspaceUseCase.enterWorkspace(context);
    }

    public void leaveWorkspaceScene(LeaveWorkspaceContext context) throws RepositoryException {
        leaveWorkspaceUseCase.leaveWorkspace(context);
    }

    @Subscribe
    public void on(NoteCreatedWithWorkspaceId event) throws RepositoryException {
        var context = WorkspaceContextBuilders.commitNoteContextBuilder()
                .workspaceId(event.getWorkspaceId())
                .noteId(event.getNoteId())
                .build();
        commitNoteUseCase.commitNote(context);
    }
}
