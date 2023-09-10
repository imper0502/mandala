package digi.joy.mandala.boot;

import digi.joy.mandala.infra.dao.DAOException;
import digi.joy.mandala.infra.event.MandalaEvent;
import digi.joy.mandala.infra.event.MandalaEventHandler;
import digi.joy.mandala.infra.repository.RepositoryException;
import digi.joy.mandala.infra.util.MandalaUtils;
import digi.joy.mandala.note.repository.NoteRepository;
import digi.joy.mandala.note.scenario.CreateMandalaContext;
import digi.joy.mandala.note.scenario.CreateNoteUseCase;
import digi.joy.mandala.note.scenario.NoteContextBuilders;
import digi.joy.mandala.note.scenario.NoteService;
import digi.joy.mandala.workspace.controller.WorkspaceController;
import digi.joy.mandala.workspace.listener.WorkspaceEventListener;
import digi.joy.mandala.workspace.scenario.BuildWorkspaceUseCase;
import digi.joy.mandala.workspace.scenario.WorkspaceContextBuilders;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;


@Slf4j
@Component
@Profile("!test")
public class StartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    private final MandalaEventHandler workspaceEventHandler;
    private final MandalaEventHandler noteEventHandler;
    private final BuildWorkspaceUseCase buildWorkspaceUseCase;
    private final CreateNoteUseCase createNoteUseCase;
    private final NoteService noteService;
    private final WorkspaceController workspaceController;
    private final NoteRepository noteRepository;
    private final WorkspaceEventListener workspaceEventListener;

    public StartedEventListener(
            @Qualifier("workspaceEventBus") MandalaEventHandler workspaceEventHandler,
            @Qualifier("noteEventBus") MandalaEventHandler noteEventHandler,
            BuildWorkspaceUseCase buildWorkspaceUseCase,
            CreateNoteUseCase createNoteUseCase,
            NoteService noteService, WorkspaceController workspaceController, NoteRepository noteRepository, WorkspaceEventListener workspaceEventListener) {
        this.workspaceEventHandler = workspaceEventHandler;
        this.noteEventHandler = noteEventHandler;
        this.buildWorkspaceUseCase = buildWorkspaceUseCase;
        this.createNoteUseCase = createNoteUseCase;
        this.noteService = noteService;
        this.workspaceController = workspaceController;
        this.noteRepository = noteRepository;
        this.workspaceEventListener = workspaceEventListener;
    }

    @Override
    public void onApplicationEvent(@NonNull ApplicationStartedEvent event) {
        noteEventHandler.register(workspaceEventListener);
        try {
            createDemoData();
        } catch (RepositoryException | DAOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    private void createDemoData() throws RepositoryException {
        UUID defaultWorkspaceId = buildWorkspaceUseCase.buildWorkspace(
                WorkspaceContextBuilders.buildWorkspaceScenario()
                        .workspaceId(UUID.fromString("64f01c0d-1026-4d89-bd5e-c7fff0d4f360"))
                        .workspaceName("DEFAULT_WORKSPACE")
                        .build()
        );
        log.info("$ Default Workspace ID: {} $", defaultWorkspaceId);
        log.info("$ Workspace Event History $");
        workspaceEventHandler.history().forEach(
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

        log.info("$ Create Expanded Mandala $");

        final var defaultMandalaId = noteService.createMandala(CreateMandalaContext.builder()
                .workspaceId(defaultWorkspaceId)
                .title("Default Mandala")
                .author(UUID.randomUUID())
                .content(Collections.emptyList())
                .build()
        );

        log.info("$ Event History $");
        Stream.concat(workspaceEventHandler.history().stream(), noteEventHandler.history().stream())
                .sorted(Comparator.comparing(MandalaEvent::getOccurredTime))
                .forEach(event -> System.out.printf("<%s> %s # %s%n", event.getOccurredTime().atZone(ZoneId.systemDefault()).toLocalDateTime(), event.getClass().getSimpleName(), event.getId()));

        final String workspaceJson = MandalaUtils.getObjectWriter().writeValueAsString(workspaceController.queryWorkspace(defaultWorkspaceId));
        System.out.println("workspaceJson = " + workspaceJson);
        final String mandalaJson = MandalaUtils.getObjectWriter().writeValueAsString(noteRepository.get(defaultMandalaId));
        System.out.println("mandalaJson = " + mandalaJson);
    }
}
