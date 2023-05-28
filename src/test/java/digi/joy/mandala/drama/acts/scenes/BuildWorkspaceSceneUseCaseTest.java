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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class BuildWorkspaceSceneUseCaseTest {
    private final WorkspaceRepository workspaceRepository;
    private final MandalaEventBus eventListener;
    private final WorkspaceContextBuilders workspaceContextBuilders;
    private BuildWorkspaceScene sut;

    @Autowired
    public BuildWorkspaceSceneUseCaseTest(WorkspaceRepository workspaceRepository, MandalaEventBus eventListener, WorkspaceContextBuilders workspaceContextBuilders) {
        this.workspaceRepository = workspaceRepository;
        this.eventListener = eventListener;
        this.workspaceContextBuilders = workspaceContextBuilders;
    }

    @BeforeEach
    void setUp() {
        this.sut = new BuildWorkspaceScene(workspaceRepository, eventListener);
    }

    @Test
    void BuildOneNewWorkspace() {
        BuildWorkspaceContext readModel = workspaceContextBuilders.buildWorkspaceScene()
                .workspaceId(UUID.randomUUID())
                .workspaceName("TEST_WORKSPACE")
                .build();

        assertDoesNotThrow(() -> sut.play(readModel));
    }

}