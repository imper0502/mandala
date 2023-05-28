package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.acts.WorkspaceRepository;
import digi.joy.mandala.drama.acts.scenes.contexts.BuildWorkspaceContext;
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
    private final BuildWorkspaceScene buildWorkspaceScene;
    private final WorkspaceRepository repository;
    private final MandalaEventBus eventListener;
    private EnterWorkspaceScene sut;

    @Autowired
    public EnterWorkspaceSceneUseCaseTest(WorkspaceRepository repository, MandalaEventBus eventListener) {
        this.repository = repository;
        this.eventListener = eventListener;
        this.buildWorkspaceScene = new BuildWorkspaceScene(repository, eventListener);
    }

    @BeforeEach
    void setUp() {
        this.sut = new EnterWorkspaceScene(repository, eventListener);
    }

    @Test
    void EnterExistingWorkspace() {
        BuildWorkspaceContext context1 = BuildWorkspaceContext.builder()
                .workspaceId(UUID.randomUUID())
                .workspaceName("TEST_WORKSPACE")
                .build();
        buildWorkspaceScene.play(context1);
        EnterWorkspaceContext context2 = EnterWorkspaceContext.builder()
                .userId(UUID.randomUUID())
                .workspaceId(context1.getWorkspaceId())
                .build();

        sut.play(context2);
    }
}