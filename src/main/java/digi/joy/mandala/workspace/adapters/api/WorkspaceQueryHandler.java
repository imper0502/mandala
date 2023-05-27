package digi.joy.mandala.workspace.adapters.api;

import digi.joy.mandala.note.adapters.infra.NoteData;
import digi.joy.mandala.note.services.NoteDataAccessor;
import digi.joy.mandala.workspace.adapters.api.published.WorkspaceDetail;
import digi.joy.mandala.workspace.adapters.api.published.WorkspaceInfo;
import digi.joy.mandala.workspace.adapters.infra.WorkspaceData;
import digi.joy.mandala.workspace.adapters.infra.WorkspaceMapper;
import digi.joy.mandala.workspace.services.WorkspaceDataAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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
    public WorkspaceDetail queryWorkspace(@PathVariable("id") String id) {
        WorkspaceData data = workspaceDataAccessor.query(id).orElseThrow();
        List<NoteData> committedNotes = noteDataAccessor.query(
                data.getCommittedNotes().stream().map(x -> UUID.fromString(x.getNoteId())).toList()
        );
        return new WorkspaceDetail(
                data.getWorkspaceName(),
                committedNotes,
                new ArrayList<>()
        );
    }

    @GetMapping
    public Map<String, WorkspaceInfo> queryWorkspaces() {
        List<WorkspaceData> dataSet = workspaceDataAccessor.queryAll();
        Map<String, WorkspaceInfo> mapping = new HashMap<>();
        dataSet.forEach(data -> mapping.put(data.getWorkspaceId(), WorkspaceMapper.wrap(data)));

        return mapping;
    }
}
