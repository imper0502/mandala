package digi.joy.mandala.application.services.scenario;

import digi.joy.mandala.application.services.infra.exception.RepositoryException;
import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.application.services.utils.WorkspaceContextBuilders;
import digi.joy.mandala.application.services.infra.WorkspaceRepository;
import digi.joy.mandala.application.services.context.BuildWorkspaceContext;
import digi.joy.mandala.application.services.context.EnterWorkspaceContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
class EnterWorkspaceScenarioTest {
    private EnterWorkspaceScenario sceneUnderTest;
    private final WorkspaceRepository repository;
    private final MandalaEventBus eventListener;
    private final BuildWorkspaceScenario buildWorkspaceScenario;

    @Autowired
    public EnterWorkspaceScenarioTest(WorkspaceRepository repository, MandalaEventBus eventListener, BuildWorkspaceScenario buildWorkspaceScenario) {
        this.buildWorkspaceScenario = buildWorkspaceScenario;
        this.repository = repository;
        this.eventListener = eventListener;
    }

    @BeforeEach
    void setUp() {
        this.sceneUnderTest = new EnterWorkspaceScenario(repository, eventListener);
    }

    @Test
    void EnterExistingWorkspace() throws RepositoryException {
        BuildWorkspaceContext context1 = WorkspaceContextBuilders.buildWorkspaceScenario()
                .workspaceName("TEST_WORKSPACE")
                .build();
        UUID workspaceId = buildWorkspaceScenario.play(context1);

        assertInstanceOf(UUID.class, workspaceId);

        EnterWorkspaceContext context2 = WorkspaceContextBuilders.enterWorkspaceScenario()
                .userId(UUID.randomUUID())
                .workspaceId(workspaceId)
                .build();
        sceneUnderTest.play(context2);

        assertFalse(repository.query(workspaceId).getWorkspaceSessions().isEmpty());
    }
}