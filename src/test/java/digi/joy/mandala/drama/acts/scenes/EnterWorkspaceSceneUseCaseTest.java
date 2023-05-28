package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.acts.WorkspaceRepository;
import digi.joy.mandala.drama.acts.scenes.contexts.InputOfBuildWorkspace;
import digi.joy.mandala.drama.acts.scenes.contexts.EnterWorkspaceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class EnterWorkspaceSceneUseCaseTest {
    private final BuildWorkspace buildWorkspace;
    private final WorkspaceRepository repository;
    private final MandalaEventBus eventListener;
    private EnterWorkspaceScene sut;

    @Autowired
    public EnterWorkspaceSceneUseCaseTest(WorkspaceRepository repository, MandalaEventBus eventListener) {
        this.repository = repository;
        this.eventListener = eventListener;
        this.buildWorkspace = new BuildWorkspace(repository, eventListener);
    }

    @BeforeEach
    void setUp() {
        this.sut = new EnterWorkspaceScene(repository, eventListener);
    }

    @Test
    void EnterExistingWorkspace() {
        InputOfBuildWorkspace context1 = InputOfBuildWorkspace.builder()
                .workspaceId(UUID.randomUUID().toString())
                .workspaceName("TEST_WORKSPACE")
                .build();
        buildWorkspace.execute(context1);
        EnterWorkspaceContext context2 = EnterWorkspaceContext.builder()
                .userId(UUID.randomUUID().toString())
                .workspaceId(context1.getWorkspaceId())
                .build();

        sut.play(context2);
    }
}