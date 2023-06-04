package digi.joy.mandala.application.services.scenario;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.application.services.utils.WorkspaceContextBuilders;
import digi.joy.mandala.application.services.infra.WorkspaceRepository;
import digi.joy.mandala.application.services.context.BuildWorkspaceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
class BuildWorkspaceScenarioTest {
    private BuildWorkspaceScenario sceneUnderTest;
    private final WorkspaceRepository workspaceRepository;
    private final MandalaEventBus eventListener;

    @Autowired
    public BuildWorkspaceScenarioTest(WorkspaceRepository workspaceRepository, MandalaEventBus eventListener) {
        this.workspaceRepository = workspaceRepository;
        this.eventListener = eventListener;
    }

    @BeforeEach
    void setUp() {
        this.sceneUnderTest = new BuildWorkspaceScenario(workspaceRepository, eventListener);
    }

    @Test
    void BuildOneNewWorkspace() {
        BuildWorkspaceContext context = WorkspaceContextBuilders.buildWorkspaceScenario()
                .workspaceName("TEST_WORKSPACE")
                .build();

        UUID result = assertDoesNotThrow(() -> sceneUnderTest.buildWorkspace(context));

        assertInstanceOf(UUID.class, result);
    }

}