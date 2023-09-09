package digi.joy.mandala.workspace.handler;

import digi.joy.mandala.note.repository.NoteData;
import digi.joy.mandala.note.repository.NoteRepositoryOperator;
import digi.joy.mandala.workspace.repository.CommittedNoteData;
import digi.joy.mandala.workspace.repository.WorkspaceConverter;
import digi.joy.mandala.workspace.repository.WorkspaceData;
import digi.joy.mandala.workspace.repository.WorkspaceRepositoryOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "v1/workspaces")
public class WorkspaceQueryHandler {
    private final WorkspaceRepositoryOperator workspaceRepositoryOperator;

    private final NoteRepositoryOperator noteRepositoryOperator;

    @Autowired
    public WorkspaceQueryHandler(WorkspaceRepositoryOperator workspaceRepositoryOperator, NoteRepositoryOperator noteRepositoryOperator) {
        this.workspaceRepositoryOperator = workspaceRepositoryOperator;
        this.noteRepositoryOperator = noteRepositoryOperator;
    }

    @GetMapping("{id}")
    public WorkspaceDetail queryWorkspace(@PathVariable("id") UUID id) {
        WorkspaceData data = workspaceRepositoryOperator.query(id).orElseThrow();
        List<NoteData> committedNotes = noteRepositoryOperator.query(
                data.getCommittedNotes().stream().map(CommittedNoteData::getNoteId).toList()
        );
        return new WorkspaceDetail(
                data.getWorkspaceName(),
                committedNotes,
                new ArrayList<>()
        );
    }

    @GetMapping
    public List<WorkspaceSummary> queryWorkspaces() {
        List<WorkspaceData> dataSet = workspaceRepositoryOperator.queryAll();

        return dataSet.stream().map(WorkspaceConverter::wrap).toList();
    }
}
