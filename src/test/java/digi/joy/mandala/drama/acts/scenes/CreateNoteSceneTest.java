package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.acts.NoteContextBuilders;
import digi.joy.mandala.drama.acts.NoteRepository;
import digi.joy.mandala.drama.acts.WorkspaceContextBuilders;
import digi.joy.mandala.drama.acts.scenes.contexts.BuildWorkspaceContext;
import digi.joy.mandala.drama.acts.scenes.contexts.CreateNoteContext;
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
public class CreateNoteSceneTest {
    private CreateNoteScene sceneUnderTest;
    private final NoteRepository noteRepository;
    private final MandalaEventBus eventListener;
    private final BuildWorkspaceScene buildWorkspaceScene;

    @Autowired
    public CreateNoteSceneTest(NoteRepository noteRepository, MandalaEventBus eventListener, BuildWorkspaceScene buildWorkspaceScene) {
        this.noteRepository = noteRepository;
        this.eventListener = eventListener;
        this.buildWorkspaceScene = buildWorkspaceScene;
    }

    @BeforeEach
    void setUp() {
        this.sceneUnderTest = new CreateNoteScene(noteRepository, eventListener);
    }

    @Test
    void createNote() {
        CreateNoteContext readModel = NoteContextBuilders.createNoteScene()
                .title("TEST_NOTE")
                .content(List.of("TEST_CONTENT"))
                .build();
        sceneUnderTest.play(readModel);
    }

    @Test
    void createNoteInWorkspace() {
        BuildWorkspaceContext context1 = WorkspaceContextBuilders.buildWorkspaceScene()
                .workspaceName("TEST_WORKSPACE")
                .build();
        UUID workspaceId = buildWorkspaceScene.play(context1);

        CreateNoteContext context2 = NoteContextBuilders.createNoteScene()
                .title("TEST_NOTE")
                .content(List.of("TEST_CONTENT"))
                .workspaceId(workspaceId)
                .build();
        sceneUnderTest.play(context2);
    }
}