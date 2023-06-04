package digi.joy.mandala;

import digi.joy.mandala.application.adapters.listener.WorkspaceEventListener;
import digi.joy.mandala.application.services.infra.exception.RepositoryException;
import digi.joy.mandala.application.services.scenario.BuildWorkspaceUseCase;
import digi.joy.mandala.application.services.scenario.CreateNoteUseCase;
import digi.joy.mandala.common.adapters.infra.MandalaEventPublisher;
import digi.joy.mandala.application.services.utils.NoteContextBuilders;
import digi.joy.mandala.application.services.utils.WorkspaceContextBuilders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.UUID;
@Slf4j
@SpringBootApplication
public class MandalaApplication implements CommandLineRunner {

    private final MandalaEventPublisher eventPublisher;
    private final WorkspaceEventListener workspaceEventListener;
    private final BuildWorkspaceUseCase buildWorkspaceScenario;
    private final CreateNoteUseCase createNoteScenario;

    @Autowired
    public MandalaApplication(
            MandalaEventPublisher eventPublisher,
            WorkspaceEventListener workspaceEventListener,
            BuildWorkspaceUseCase buildWorkspaceScenario,
            CreateNoteUseCase createNoteScenario) {
        this.eventPublisher = eventPublisher;
        this.workspaceEventListener = workspaceEventListener;
        this.buildWorkspaceScenario = buildWorkspaceScenario;
        this.createNoteScenario = createNoteScenario;
    }

    public static void main(String[] args) {
        SpringApplication.run(MandalaApplication.class, args);
    }

    @Override
    public void run(String... arg0) throws RepositoryException {
        eventPublisher.register(workspaceEventListener);

        UUID defaultWorkspaceId = buildWorkspaceScenario.buildWorkspace(
                WorkspaceContextBuilders.buildWorkspaceScenario()
                        .workspaceId(UUID.fromString("64f01c0d-1026-4d89-bd5e-c7fff0d4f360"))
                        .workspaceName("DEFAULT_WORKSPACE")
                        .build()
        );
        log.info("$ Default Workspace ID: {} $", defaultWorkspaceId);

        UUID defaultNoteId = createNoteScenario.createNote(
                NoteContextBuilders.createNoteScene()
                        .workspaceId(defaultWorkspaceId)
                        .title("TEST_NOTE")
                        .content(List.of("TEST_CONTENT"))
                        .build()
        );
        log.info("$ Default Note ID: {} $", defaultNoteId);

        log.info("$ Event History $");
        eventPublisher.history().forEach(
                event -> log.info("* Event: {} #{}", event.getClass().getSimpleName(), event.hashCode())
        );
    }
}
