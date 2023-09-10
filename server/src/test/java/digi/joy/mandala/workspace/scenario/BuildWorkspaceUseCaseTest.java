package digi.joy.mandala.workspace.scenario;

import com.google.common.eventbus.EventBus;
import digi.joy.mandala.infra.event.MandalaEventHandler;
import digi.joy.mandala.workspace.dao.InMemoryWorkspaceRepositoryOperator;
import digi.joy.mandala.workspace.repository.WorkspaceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class BuildWorkspaceUseCaseTest {
    private BuildWorkspaceUseCase useCaseUnderTest;

    @BeforeEach
    void setUp() {
        WorkspaceRepository repository = new WorkspaceRepository(new InMemoryWorkspaceRepositoryOperator());
        MandalaEventHandler mandalaEventHandler = new MandalaEventHandler(new EventBus());
        this.useCaseUnderTest = new WorkspaceService(repository, mandalaEventHandler);
    }

    @Test
    void BuildOneNewWorkspace() {
        BuildWorkspaceContext context = WorkspaceContextBuilders.buildWorkspaceScenario()
                .workspaceName("TEST_WORKSPACE")
                .build();

        UUID result = Assertions.assertDoesNotThrow(() -> useCaseUnderTest.buildWorkspace(context));

        assertInstanceOf(UUID.class, result);
    }

}