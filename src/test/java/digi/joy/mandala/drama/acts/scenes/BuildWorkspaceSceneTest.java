package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.acts.WorkspaceContextBuilders;
import digi.joy.mandala.drama.acts.WorkspaceRepository;
import digi.joy.mandala.drama.acts.scenes.contexts.BuildWorkspaceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
class BuildWorkspaceSceneTest {
    private BuildWorkspaceScene sceneUnderTest;
    private final WorkspaceRepository workspaceRepository;
    private final MandalaEventBus eventListener;

    @Autowired
    public BuildWorkspaceSceneTest(WorkspaceRepository workspaceRepository, MandalaEventBus eventListener) {
        this.workspaceRepository = workspaceRepository;
        this.eventListener = eventListener;
    }

    @BeforeEach
    void setUp() {
        this.sceneUnderTest = new BuildWorkspaceScene(workspaceRepository, eventListener);
    }

    @Test
    void BuildOneNewWorkspace() {
        BuildWorkspaceContext context = WorkspaceContextBuilders.buildWorkspaceScene()
                .workspaceName("TEST_WORKSPACE")
                .build();

        UUID result = assertDoesNotThrow(() -> sceneUnderTest.play(context));

        assertInstanceOf(UUID.class, result);
    }

}