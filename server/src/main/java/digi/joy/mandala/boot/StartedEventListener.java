package digi.joy.mandala.boot;

import digi.joy.mandala.infra.dao.DAOException;
import digi.joy.mandala.infra.event.MandalaEventHandler;
import digi.joy.mandala.infra.repository.RepositoryException;
import digi.joy.mandala.note.scenario.CreateNoteUseCase;
import digi.joy.mandala.note.scenario.NoteContextBuilders;
import digi.joy.mandala.workspace.scenario.BuildWorkspaceUseCase;
import digi.joy.mandala.workspace.scenario.WorkspaceContextBuilders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequiredArgsConstructor
public class StartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    private final MandalaEventHandler workspaceEventBus;
    private final MandalaEventHandler noteEventBus;
    private final BuildWorkspaceUseCase buildWorkspaceUseCase;
    private final CreateNoteUseCase createNoteUseCase;

    @Override
    public void onApplicationEvent(@NonNull ApplicationStartedEvent event) {
        try {
            createDemoData();
        } catch (RepositoryException | DAOException e) {
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
                        .author(UUID.randomUUID())
                        .build()
        );
        log.info("$ Default Note ID: {} $", defaultNoteId);
        defaultNoteId = createNoteUseCase.createNote(
                NoteContextBuilders.createNoteScene()
                        .workspaceId(defaultWorkspaceId)
                        .title("TEST_NOTE")
                        .content(List.of("TEST_CONTENT"))
                        .author(UUID.randomUUID())
                        .build()
        );
        log.info("$ Default Note ID: {} $", defaultNoteId);
        defaultNoteId = createNoteUseCase.createNote(
                NoteContextBuilders.createNoteScene()
                        .workspaceId(defaultWorkspaceId)
                        .title("TEST_NOTE").content(List.of("TEST_CONTENT"))
                        .author(UUID.randomUUID())
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
