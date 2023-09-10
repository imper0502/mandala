package digi.joy.mandala.workspace.listener;

import com.google.common.eventbus.Subscribe;
import digi.joy.mandala.infra.event.MandalaEventListener;
import digi.joy.mandala.infra.repository.RepositoryException;
import digi.joy.mandala.workspace.event.NoteCreatedWithWorkspaceId;
import digi.joy.mandala.workspace.scenario.CommitNoteContext;
import digi.joy.mandala.workspace.scenario.CommitNoteUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public final class WorkspaceEventListener extends MandalaEventListener {

    private final CommitNoteUseCase commitNoteUseCase;

    @Subscribe
    public void onNoteCreatedWithWorkspaceId(NoteCreatedWithWorkspaceId event) throws RepositoryException {
        commitNoteUseCase.commitNote(
                CommitNoteContext.builder()
                        .workspaceId(event.getWorkspaceId())
                        .noteId(event.getNoteId())
                        .build());
    }
}
