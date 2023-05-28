package digi.joy.mandala.drama.adapters.api;

import digi.joy.mandala.drama.acts.NoteDataAccessor;
import digi.joy.mandala.drama.acts.WorkspaceDataAccessor;
import digi.joy.mandala.drama.acts.mapper.WorkspaceMapper;
import digi.joy.mandala.drama.adapters.api.published.WorkspaceDetail;
import digi.joy.mandala.drama.adapters.api.published.WorkspaceSummary;
import digi.joy.mandala.drama.adapters.infra.schema.CommittedNoteData;
import digi.joy.mandala.drama.adapters.infra.schema.NoteData;
import digi.joy.mandala.drama.adapters.infra.schema.WorkspaceData;
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
    private final WorkspaceDataAccessor workspaceDataAccessor;

    private final NoteDataAccessor noteDataAccessor;

    @Autowired
    public WorkspaceQueryHandler(WorkspaceDataAccessor workspaceDataAccessor, NoteDataAccessor noteDataAccessor) {
        this.workspaceDataAccessor = workspaceDataAccessor;
        this.noteDataAccessor = noteDataAccessor;
    }

    @GetMapping("{id}")
    public WorkspaceDetail queryWorkspace(@PathVariable("id") UUID id) {
        WorkspaceData data = workspaceDataAccessor.query(id).orElseThrow();
        List<NoteData> committedNotes = noteDataAccessor.query(
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
        List<WorkspaceData> dataSet = workspaceDataAccessor.queryAll();

        return dataSet.stream().map(WorkspaceMapper::wrap).toList();
    }
}
