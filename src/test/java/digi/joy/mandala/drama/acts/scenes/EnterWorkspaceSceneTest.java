package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.acts.WorkspaceContextBuilders;
import digi.joy.mandala.drama.acts.WorkspaceRepository;
import digi.joy.mandala.drama.acts.scenes.contexts.BuildWorkspaceContext;
import digi.joy.mandala.drama.acts.scenes.contexts.EnterWorkspaceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
class EnterWorkspaceSceneTest {
    private EnterWorkspaceScene sceneUnderTest;
    private final WorkspaceRepository repository;
    private final MandalaEventBus eventListener;
    private final BuildWorkspaceScene buildWorkspaceScene;

    @Autowired
    public EnterWorkspaceSceneTest(WorkspaceRepository repository, MandalaEventBus eventListener, BuildWorkspaceScene buildWorkspaceScene) {
        this.buildWorkspaceScene = buildWorkspaceScene;
        this.repository = repository;
        this.eventListener = eventListener;
    }

    @BeforeEach
    void setUp() {
        this.sceneUnderTest = new EnterWorkspaceScene(repository, eventListener);
    }

    @Test
    void EnterExistingWorkspace() {
        BuildWorkspaceContext context1 = WorkspaceContextBuilders.buildWorkspaceScene()
                .workspaceName("TEST_WORKSPACE")
                .build();
        UUID workspaceId = buildWorkspaceScene.play(context1);

        assertInstanceOf(UUID.class, workspaceId);

        EnterWorkspaceContext context2 = WorkspaceContextBuilders.enterWorkspaceScene()
                .userId(UUID.randomUUID())
                .workspaceId(workspaceId)
                .build();
        sceneUnderTest.play(context2);

        assertFalse(repository.query(workspaceId).getWorkspaceSessions().isEmpty());
    }
}