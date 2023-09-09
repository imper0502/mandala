package digi.joy.mandala.workspace.scenario;

import digi.joy.mandala.infra.event.MandalaEventPublisher;
import digi.joy.mandala.infra.exception.RepositoryException;
import digi.joy.mandala.workspace.Workspace;
import digi.joy.mandala.workspace.repository.WorkspaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class WorkspaceService implements
        BuildWorkspaceUseCase,
        CommitNoteUseCase,
        EnterWorkspaceUseCase,
        LeaveWorkspaceUseCase {

    private final WorkspaceRepository workspaceRepository;
    private final MandalaEventPublisher eventPublisher;

    @Autowired
    public WorkspaceService(WorkspaceRepository workspaceRepository, @Qualifier("workspaceEventBus") MandalaEventPublisher eventPublisher) {
        this.workspaceRepository = workspaceRepository;
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