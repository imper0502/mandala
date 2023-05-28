package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.acts.WorkspaceContextBuilders;
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
class EnterWorkspaceSceneTest {
    private final BuildWorkspaceScene buildWorkspaceScene;
    private final WorkspaceRepository repository;
    private final MandalaEventBus eventListener;
    private EnterWorkspaceScene sut;

    @Autowired
    public EnterWorkspaceSceneTest(WorkspaceRepository repository, MandalaEventBus eventListener) {
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
        BuildWorkspaceContext context1 = WorkspaceContextBuilders.buildWorkspaceScene()
                .workspaceName("TEST_WORKSPACE")
                .build();
        UUID workspaceId = buildWorkspaceScene.play(context1);

        EnterWorkspaceContext context2 = WorkspaceContextBuilders.enterWorkspaceScene()
                .userId(UUID.randomUUID())
                .workspaceId(workspaceId)
                .build();
        sut.play(context2);
    }
}