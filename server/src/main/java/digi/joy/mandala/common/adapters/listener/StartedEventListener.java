package digi.joy.mandala.common.adapters.listener;

import digi.joy.mandala.common.adapters.infra.MandalaEventBus;
import digi.joy.mandala.common.services.infra.exception.RepositoryException;
import digi.joy.mandala.note.services.NoteContextBuilders;
import digi.joy.mandala.note.services.scenario.CreateNoteUseCase;
import digi.joy.mandala.workspace.services.WorkspaceContextBuilders;
import digi.joy.mandala.workspace.services.scenario.BuildWorkspaceUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Slf4j
@Component
@Profile("!test")
public class StartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    private final MandalaEventBus workspaceEventBus;
    private final MandalaEventBus noteEventBus;
    private final BuildWorkspaceUseCase buildWorkspaceUseCase;
    private final CreateNoteUseCase createNoteUseCase;

    @Autowired
    public StartedEventListener(
            MandalaEventBus workspaceEventBus,
            MandalaEventBus noteEventBus,
            BuildWorkspaceUseCase buildWorkspaceUseCase,
            CreateNoteUseCase createNoteUseCase) {
        this.workspaceEventBus = workspaceEventBus;
        this.noteEventBus = noteEventBus;
        this.buildWorkspaceUseCase = buildWorkspaceUseCase;
        this.createNoteUseCase = createNoteUseCase;
    }

    @Override
    public void onApplicationEvent(@NonNull ApplicationStartedEvent event) {
        try {
            createDemoData();
        } catch (RepositoryException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDemoData() throws RepositoryException {
        UUID defaultWorkspaceId = buildWorkspaceUseCase.buildWorkspace(
                WorkspaceContextBuilders.buildWorkspaceScenario()
                        .workspaceId(UUID.fromString("64f01c0d-1026-4d89-bd5e-c7fff0d4f360"))
                        .workspaceName("DEFAULT_WORKSPACE")
                        .build()
        );
        log.info("$ Default Workspace ID: {} $", defaultWorkspaceId);
        log.info("$ Workspace Event History $");
        workspaceEventBus.history().forEach(
                event -> log.info("* Event: {} #{}", event.getClass().getSimpleName(), event.hashCode())
        );

        UUID defaultNoteId = createNoteUseCase.createNote(
                NoteContextBuilders.createNoteScene()
                        .workspaceId(defaultWorkspaceId)
                        .title("TEST_NOTE")
                        .content(List.of("TEST_CONTENT"))
                        .build()
        );
        log.info("$ Default Note ID: {} $", defaultNoteId);
        defaultNoteId = createNoteUseCase.createNote(
                NoteContextBuilders.createNoteScene()
                        .workspaceId(defaultWorkspaceId)
                        .title("TEST_NOTE")
                        .content(List.of("TEST_CONTENT"))
                        .build()
        );
        log.info("$ Default Note ID: {} $", defaultNoteId);
        defaultNoteId = createNoteUseCase.createNote(
                NoteContextBuilders.createNoteScene()
                        .workspaceId(defaultWorkspaceId)
                        .title("TEST_NOTE")
                        .content(List.of("TEST_CONTENT"))
                        .build()
        );
        log.info("$ Default Note ID: {} $", defaultNoteId);

        log.info("$ Note Event History $");
        noteEventBus.history().forEach(
                event -> log.info("* Event: {} #{}", event.getClass().getSimpleName(), event.hashCode())
        );

        log.info("$ Workspace Event History $");
        workspaceEventBus.history().forEach(
                event -> log.info("* Event: {} #{}", event.getClass().getSimpleName(), event.hashCode())
        );
    }
}
