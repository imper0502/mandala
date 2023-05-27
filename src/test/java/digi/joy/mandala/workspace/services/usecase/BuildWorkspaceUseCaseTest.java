package digi.joy.mandala.workspace.services.usecase;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.workspace.services.WorkspaceRepository;
import digi.joy.mandala.workspace.services.usecase.input.InputOfBuildWorkspace;
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
class BuildWorkspaceUseCaseTest {
    private final WorkspaceRepository workspaceRepository;
    private final MandalaEventBus eventListener;
    private BuildWorkspace sut;

    @Autowired
    public BuildWorkspaceUseCaseTest(WorkspaceRepository workspaceRepository, MandalaEventBus eventListener) {
        this.workspaceRepository = workspaceRepository;
        this.eventListener = eventListener;
    }

    @BeforeEach
    void setUp() {
        this.sut = new BuildWorkspace(workspaceRepository, eventListener);
    }

    @Test
    void BuildOneNewWorkspace() {
        InputOfBuildWorkspace readModel = sut.inputBuilder()
                .workspaceId(UUID.randomUUID().toString())
                .workspaceName("TEST_WORKSPACE")
                .build();

        assertDoesNotThrow(() -> sut.execute(readModel));
    }

}