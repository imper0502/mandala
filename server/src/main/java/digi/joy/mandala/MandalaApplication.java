package digi.joy.mandala;

import digi.joy.mandala.common.adapters.infra.MandalaEventPublisher;
import digi.joy.mandala.drama.services.NoteContextBuilders;
import digi.joy.mandala.drama.services.WorkspaceContextBuilders;
import digi.joy.mandala.drama.services.WorkspaceEventHandler;
import digi.joy.mandala.drama.services.scenario.BuildWorkspaceScenario;
import digi.joy.mandala.drama.services.scenario.CreateNoteScenario;
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
    private final WorkspaceEventHandler workspaceEventHandler;
    private final BuildWorkspaceScenario buildWorkspaceScenario;
    private final CreateNoteScenario createNoteScenario;

    @Autowired
    public MandalaApplication(
            MandalaEventPublisher eventPublisher,
            WorkspaceEventHandler workspaceEventHandler,
            BuildWorkspaceScenario buildWorkspaceScenario,
            CreateNoteScenario createNoteScenario) {
        this.eventPublisher = eventPublisher;
        this.workspaceEventHandler = workspaceEventHandler;
        this.buildWorkspaceScenario = buildWorkspaceScenario;
        this.createNoteScenario = createNoteScenario;
    }

    public static void main(String[] args) {
        SpringApplication.run(MandalaApplication.class, args);
    }

    @Override
    public void run(String... arg0) {
        eventPublisher.register(workspaceEventHandler);

        UUID defaultWorkspaceId = buildWorkspaceScenario.play(
                WorkspaceContextBuilders.buildWorkspaceScene()
                        .workspaceId(UUID.fromString("64f01c0d-1026-4d89-bd5e-c7fff0d4f360"))
                        .workspaceName("DEFAULT_WORKSPACE")
                        .build()
        );
        log.info("$ Default Workspace ID: {} $", defaultWorkspaceId);

        UUID defaultNoteId = createNoteScenario.play(
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
