package digi.joy.mandala.application.services.scenario;

import digi.joy.mandala.application.entities.Note;
import digi.joy.mandala.application.entities.Workspace;
import digi.joy.mandala.application.services.context.*;
import digi.joy.mandala.application.services.infra.NoteRepository;
import digi.joy.mandala.application.services.infra.WorkspaceRepository;
import digi.joy.mandala.application.services.infra.exception.RepositoryException;
import digi.joy.mandala.application.services.scenario.*;
import digi.joy.mandala.common.services.MandalaEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class WorkspaceService implements
        BuildWorkspaceUseCase,
        CommitNoteUseCase,
        CreateNoteUseCase,
        EnterWorkspaceUseCase,
        LeaveWorkspaceUseCase {

    private final WorkspaceRepository workspaceRepository;
    private final NoteRepository noteRepository;
    private final MandalaEventBus eventPublisher;

    @Autowired
    public WorkspaceService(WorkspaceRepository workspaceRepository, NoteRepository noteRepository, MandalaEventBus eventPublisher) {
        this.workspaceRepository = workspaceRepository;
        this.noteRepository = noteRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public UUID buildWorkspace(BuildWorkspaceContext context) throws RepositoryException {
        UUID workspaceId = Optional.ofNullable(context.getWorkspaceId()).orElse(UUID.randomUUID());
        Workspace workspace = Workspace.builder()
                .workspaceId(workspaceId)
                .workspaceName(context.getWorkspaceName())
                .build();

        workspaceRepository.add(workspace);
        eventPublisher.commit(workspace.workspaceBuiltEvent());
        eventPublisher.postAll();

        return workspaceId;
    }

    @Override
    public void commitNote(CommitNoteContext context) throws RepositoryException {
        Workspace workspace = workspaceRepository.withdraw(context.getWorkspaceId());
        eventPublisher.commit(
                workspace.commitNote(context.getNoteId())
        );
        workspaceRepository.add(workspace);
        eventPublisher.postAll();
    }

    @Override
    public UUID createNote(CreateNoteContext context) {
        UUID noteId = Optional.ofNullable(context.getNoteId()).orElse(UUID.randomUUID());
        Note note = Note.builder()
                .noteId(noteId)
                .title(context.getTitle())
                .createDateTime(ZonedDateTime.now())
                .build();
        note.append(context.getContent());

        noteRepository.add(note);

        Optional<UUID> workspaceId = Optional.ofNullable(context.getWorkspaceId());
        workspaceId.ifPresentOrElse(
                id -> eventPublisher.commit(note.noteCreatedEvent(id)),
                () -> eventPublisher.commit(note.noteCreatedEvent())
        );

        eventPublisher.postAll();
        return noteId;
    }

    @Override
    public void enterWorkspace(EnterWorkspaceContext context) throws RepositoryException {
        Workspace workspace = workspaceRepository.withdraw(context.getWorkspaceId());
        eventPublisher.commit(
                workspace.add(context.getUserId())
        );
        workspaceRepository.add(workspace);

        eventPublisher.postAll();
    }

    @Override
    public void leaveWorkspace(LeaveWorkspaceContext context) throws RepositoryException {
        Workspace w = workspaceRepository.withdraw(context.getWorkspaceId());
        eventPublisher.commit(
                w.remove(context.getUserId())
        );
        workspaceRepository.add(w);
        eventPublisher.postAll();
    }
}
