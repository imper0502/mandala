package digi.joy.mandala;

import digi.joy.mandala.common.adapters.api.MandalaEventHandler;
import digi.joy.mandala.common.adapters.infra.MandalaEventPublisher;
import digi.joy.mandala.drama.acts.WorkspaceContextBuilders;
import digi.joy.mandala.drama.acts.scenes.BuildWorkspaceScene;
import digi.joy.mandala.drama.acts.scenes.CreateNoteInWorkspaceScene;
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
    private final MandalaEventHandler eventHandler;

    private final BuildWorkspaceScene buildWorkspaceScene;
    private final CreateNoteInWorkspaceScene createNoteInWorkspaceScene;

    @Autowired
    public MandalaApplication(MandalaEventPublisher eventPublisher, MandalaEventHandler eventHandler, BuildWorkspaceScene buildWorkspaceScene, CreateNoteInWorkspaceScene createNoteInWorkspaceScene) {
        this.eventPublisher = eventPublisher;
        this.eventHandler = eventHandler;
        this.buildWorkspaceScene = buildWorkspaceScene;
        this.createNoteInWorkspaceScene = createNoteInWorkspaceScene;
    }

    public static void main(String[] args) {
        SpringApplication.run(MandalaApplication.class, args);
    }

    @Override
    public void run(String... arg0) {
        eventPublisher.register(eventHandler);

        UUID defaultWorkspaceId = buildWorkspaceScene.play(
                WorkspaceContextBuilders.buildWorkspaceScene()
                        .workspaceId(UUID.fromString("64f01c0d-1026-4d89-bd5e-c7fff0d4f360"))
                        .workspaceName("DEFAULT_WORKSPACE")
                        .build()
        );
        log.info("$> Default Workspace ID: {}", defaultWorkspaceId);

        createNoteInWorkspaceScene.play(
                WorkspaceContextBuilders.createNoteInWorkspaceScene()
                        .workspaceId(defaultWorkspaceId)
                        .title("TEST_NOTE")
                        .content(List.of("TEST_CONTENT"))
                        .build()
        );
    }
}
