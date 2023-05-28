package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.drama.acts.WorkspaceContextBuilders;
import digi.joy.mandala.drama.acts.scenes.contexts.BuildWorkspaceContext;
import digi.joy.mandala.drama.acts.scenes.contexts.CreateNoteInWorkspaceContext;
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
class CreateNoteInWorkspaceSceneTest {
    private final CreateNoteInWorkspaceScene caseUnderTest;

    private final BuildWorkspaceScene buildWorkspaceScene;

    @Autowired
    public CreateNoteInWorkspaceSceneTest(CreateNoteInWorkspaceScene caseUnderTest, BuildWorkspaceScene buildWorkspaceScene) {
        this.caseUnderTest = caseUnderTest;
        this.buildWorkspaceScene = buildWorkspaceScene;
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void createNoteInWorkspace() {
        BuildWorkspaceContext context1 = WorkspaceContextBuilders.buildWorkspaceScene()
                .workspaceName("TEST_WORKSPACE")
                .build();
        UUID workspaceId = buildWorkspaceScene.play(context1);

        CreateNoteInWorkspaceContext context2 = WorkspaceContextBuilders.createNoteInWorkspaceScene()
                .title("TEST_NOTE")
                .content(List.of("TEST_CONTENT"))
                .workspaceId(workspaceId)
                .build();
        caseUnderTest.play(context2);
    }
}