package digi.joy.mandala.workspace.controller;

import digi.joy.mandala.note.Note;
import digi.joy.mandala.note.repository.NoteRepository;
import digi.joy.mandala.workspace.CommittedNote;
import digi.joy.mandala.workspace.Workspace;
import digi.joy.mandala.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "v1/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceRepository workspaceRepository;
    private final NoteRepository noteRepository;

    @GetMapping
    public List<WorkspaceResource> queryWorkspaces() {
        return workspaceRepository.findAll()
                .stream()
                .map(WorkspaceResource::of)
                .toList();
    }

    @GetMapping("{id}")
    public SpreadWorkspaceResource queryWorkspace(@PathVariable("id") UUID id) {
        final Workspace workspace = workspaceRepository.get(id);
        final List<Note> committedNotes = noteRepository.find(workspace.getCommittedNotes().stream().map(CommittedNote::noteId).toList());
        return SpreadWorkspaceResource.of(workspace, committedNotes);
    }
}
