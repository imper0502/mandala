package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.acts.WorkspaceContextBuilders;
import digi.joy.mandala.drama.acts.WorkspaceRepository;
import digi.joy.mandala.drama.acts.scenes.contexts.BuildWorkspaceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class BuildWorkspaceSceneTest {
    private final WorkspaceRepository workspaceRepository;
    private final MandalaEventBus eventListener;
    private BuildWorkspaceScene sut;

    @Autowired
    public BuildWorkspaceSceneTest(WorkspaceRepository workspaceRepository, MandalaEventBus eventListener) {
        this.workspaceRepository = workspaceRepository;
        this.eventListener = eventListener;
    }

    @BeforeEach
    void setUp() {
        this.sut = new BuildWorkspaceScene(workspaceRepository, eventListener);
    }

    @Test
    void BuildOneNewWorkspace() {
        BuildWorkspaceContext context = WorkspaceContextBuilders.buildWorkspaceScene()
                .workspaceName("TEST_WORKSPACE")
                .build();

        assertDoesNotThrow(() -> sut.play(context));
    }

}