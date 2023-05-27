package digi.joy.mandala.workspace.services.usecase;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.workspace.services.WorkspaceRepository;
import digi.joy.mandala.workspace.services.usecase.input.InputOfBuildWorkspace;
import digi.joy.mandala.workspace.services.usecase.input.InputOfEnterWorkspace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class EnterWorkspaceUseCaseTest {
    private final BuildWorkspace buildWorkspace;
    private final WorkspaceRepository repository;
    private final MandalaEventBus eventListener;
    private EnterWorkspace sut;

    @Autowired
    public EnterWorkspaceUseCaseTest(WorkspaceRepository repository, MandalaEventBus eventListener) {
        this.repository = repository;
        this.eventListener = eventListener;
        this.buildWorkspace = new BuildWorkspace(repository, eventListener);
    }

    @BeforeEach
    void setUp() {
        this.sut = new EnterWorkspace(repository, eventListener);
    }

    @Test
    void EnterExistingWorkspace() {
        InputOfBuildWorkspace input = InputOfBuildWorkspace.builder()
                .workspaceId(UUID.randomUUID().toString())
                .workspaceName("TEST_WORKSPACE")
                .build();
        buildWorkspace.execute(input);
        InputOfEnterWorkspace readModel = InputOfEnterWorkspace.builder()
                .userId(UUID.randomUUID().toString())
                .workspaceId(input.getWorkspaceId())
                .build();

        sut.execute(readModel);
    }
}