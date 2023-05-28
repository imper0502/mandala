package digi.joy.mandala.drama.acts;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.acts.scenes.BuildWorkspaceScene;
import digi.joy.mandala.drama.acts.scenes.EnterWorkspaceScene;
import digi.joy.mandala.drama.acts.scenes.LeaveWorkspaceScene;
import digi.joy.mandala.drama.acts.scenes.contexts.BuildWorkspaceContext;
import digi.joy.mandala.drama.acts.scenes.contexts.EnterWorkspaceContext;
import digi.joy.mandala.drama.adapters.infra.InMemoryWorkspaceDataAccessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WorkspaceActTest {
    private WorkspaceAct actUnderTest;
    private WorkspaceRepository repository;
    private final MandalaEventBus eventBus;

    @Autowired
    public WorkspaceActTest(MandalaEventBus eventBus) {
        this.eventBus = eventBus;
    }

    @BeforeEach
    void setUp() {
        this.repository = new WorkspaceRepository(new InMemoryWorkspaceDataAccessor());
        this.actUnderTest = new WorkspaceAct(
                new BuildWorkspaceScene(repository, eventBus),
                new EnterWorkspaceScene(repository, eventBus),
                new LeaveWorkspaceScene(repository, eventBus)
        );
    }

    @Test
    void rehearseBuildWorkspaceScene() {
        BuildWorkspaceContext context = WorkspaceContextBuilders.buildWorkspaceScene()
                .workspaceName("TEST_WORKSPACE")
                .build();

        UUID result = assertDoesNotThrow(() -> actUnderTest.buildWorkspaceScene(context));

        assertInstanceOf(UUID.class, result);
    }

    @Test
    void rehearseEnterWorkspaceScene() {
        BuildWorkspaceContext context1 = WorkspaceContextBuilders.buildWorkspaceScene()
                .workspaceName("TEST_WORKSPACE")
                .build();
        UUID workspaceId = actUnderTest.buildWorkspaceScene(context1);
        EnterWorkspaceContext context2 = WorkspaceContextBuilders.enterWorkspaceScene()
                .userId(UUID.randomUUID())
                .workspaceId(workspaceId)
                .build();

        assertDoesNotThrow(() -> actUnderTest.enterWorkspaceScene(context2));

        assertFalse(repository.query(workspaceId).getWorkspaceSessions().isEmpty());
    }

    @Test
    void rehearseLeaveWorkspaceScene() {
        var context1 = WorkspaceContextBuilders.buildWorkspaceScene()
                .workspaceName("TEST_WORKSPACE")
                .build();
        UUID id = actUnderTest.buildWorkspaceScene(context1);
        var context2 = WorkspaceContextBuilders.enterWorkspaceScene()
                .userId(UUID.randomUUID())
                .workspaceId(id)
                .build();
        actUnderTest.enterWorkspaceScene(context2);
        Assertions.assertFalse(repository.query(id).getWorkspaceSessions().isEmpty());
        var context3 = WorkspaceContextBuilders.leaveWorkspaceScene()
                .userId(context2.getUserId())
                .workspaceId(context2.getWorkspaceId())
                .build();

        actUnderTest.leaveWorkspaceScene(context3);

        Assertions.assertTrue(repository.query(id).getWorkspaceSessions().isEmpty());
    }
}