package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.drama.acts.scenes.contexts.InputOfCreateNoteInWorkspace;
import digi.joy.mandala.drama.acts.scenes.contexts.InputOfBuildWorkspace;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CreateNoteInWorkspaceUseCaseTest {
    private CreateNoteInWorkspace caseUnderTest;

    private BuildWorkspace buildWorkspace;

    @Autowired
    public CreateNoteInWorkspaceUseCaseTest(CreateNoteInWorkspace caseUnderTest, BuildWorkspace buildWorkspace) {
        this.caseUnderTest = caseUnderTest;
        this.buildWorkspace = buildWorkspace;
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void createNoteInWorkspace() {
        InputOfBuildWorkspace input = InputOfBuildWorkspace.builder()
                .workspaceId(UUID.randomUUID().toString())
                .workspaceName("TEST_WORKSPACE")
                .build();
        buildWorkspace.execute(input);

        InputOfCreateNoteInWorkspace readModel = InputOfCreateNoteInWorkspace.builder()
                .title("TEST_NOTE")
                .content(List.of("TEST_CONTENT"))
                .workspaceId(input.getWorkspaceId())
                .build();
        caseUnderTest.execute(readModel);
    }
}