package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.drama.acts.scenes.contexts.CreateNoteInWorkspaceContext;
import digi.joy.mandala.drama.acts.scenes.contexts.BuildWorkspaceContext;
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
class CreateNoteSceneInWorkspaceUseCaseTest {
    private CreateNoteInWorkspaceScene caseUnderTest;

    private BuildWorkspaceScene buildWorkspaceScene;

    @Autowired
    public CreateNoteSceneInWorkspaceUseCaseTest(CreateNoteInWorkspaceScene caseUnderTest, BuildWorkspaceScene buildWorkspaceScene) {
        this.caseUnderTest = caseUnderTest;
        this.buildWorkspaceScene = buildWorkspaceScene;
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void createNoteInWorkspace() {
        BuildWorkspaceContext input = BuildWorkspaceContext.builder()
                .workspaceId(UUID.randomUUID())
                .workspaceName("TEST_WORKSPACE")
                .build();
        buildWorkspaceScene.play(input);

        CreateNoteInWorkspaceContext readModel = CreateNoteInWorkspaceContext.builder()
                .title("TEST_NOTE")
                .content(List.of("TEST_CONTENT"))
                .workspaceId(input.getWorkspaceId())
                .build();
        caseUnderTest.play(readModel);
    }
}