package digi.joy.mandala;

import digi.joy.mandala.common.adapters.api.MandalaEventHandler;
import digi.joy.mandala.common.adapters.infra.MandalaEventPublisher;
import digi.joy.mandala.note.services.usecase.CreateNoteInWorkspace;
import digi.joy.mandala.workspace.services.usecase.BuildWorkspace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MandalaApplication implements CommandLineRunner {

    private final MandalaEventPublisher eventPublisher;
    private final MandalaEventHandler eventHandler;

    private final BuildWorkspace buildWorkspace;
    private final CreateNoteInWorkspace createNoteInWorkspace;

    @Autowired
    public MandalaApplication(MandalaEventPublisher eventPublisher, MandalaEventHandler eventHandler, BuildWorkspace buildWorkspace, CreateNoteInWorkspace createNoteInWorkspace) {
        this.eventPublisher = eventPublisher;
        this.eventHandler = eventHandler;
        this.buildWorkspace = buildWorkspace;
        this.createNoteInWorkspace = createNoteInWorkspace;
    }

    public static void main(String[] args) {
        SpringApplication.run(MandalaApplication.class, args);
    }

    @Override
    public void run(String... arg0) {
        eventPublisher.register(eventHandler);

        String defaultWorkspaceId = "0";

        buildWorkspace.execute(
                buildWorkspace.inputBuilder()
                        .workspaceId(defaultWorkspaceId)
                        .workspaceName("TEST_WORKSPACE")
                        .build()
        );

        createNoteInWorkspace.execute(
                createNoteInWorkspace.inputBuilder()
                        .workspaceId(defaultWorkspaceId)
                        .title("TEST_NOTE")
                        .content(List.of("TEST_CONTENT"))
                        .build()
        );
    }
}
