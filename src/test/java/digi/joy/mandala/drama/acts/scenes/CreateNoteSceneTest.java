package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.actors.Note;
import digi.joy.mandala.drama.actors.Workspace;
import digi.joy.mandala.drama.acts.NoteContextBuilders;
import digi.joy.mandala.drama.acts.NoteRepository;
import digi.joy.mandala.drama.acts.WorkspaceContextBuilders;
import digi.joy.mandala.drama.acts.WorkspaceRepository;
import digi.joy.mandala.drama.acts.scenes.contexts.BuildWorkspaceContext;
import digi.joy.mandala.drama.acts.scenes.contexts.CreateNoteContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
public class CreateNoteSceneTest {
    private CreateNoteScene sceneUnderTest;
    private final NoteRepository noteRepository;
    private final WorkspaceRepository workspaceRepository;
    private final MandalaEventBus eventListener;
    private final BuildWorkspaceScene buildWorkspaceScene;

    @Autowired
    public CreateNoteSceneTest(NoteRepository noteRepository, WorkspaceRepository workspaceRepository, MandalaEventBus eventListener, BuildWorkspaceScene buildWorkspaceScene) {
        this.noteRepository = noteRepository;
        this.workspaceRepository = workspaceRepository;
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

        UUID id = sceneUnderTest.play(readModel);

        assertInstanceOf(Note.class, noteRepository.query(id));
    }

    @Test
    void createNoteInWorkspace() {
        BuildWorkspaceContext context1 = WorkspaceContextBuilders.buildWorkspaceScene()
                .workspaceName("TEST_WORKSPACE")
                .build();
        UUID workspaceId = buildWorkspaceScene.play(context1);

        assertInstanceOf(Workspace.class, workspaceRepository.query(workspaceId));
        CreateNoteContext context2 = NoteContextBuilders.createNoteScene()
                .title("TEST_NOTE")
                .content(List.of("TEST_CONTENT"))
                .workspaceId(workspaceId)
                .build();
        UUID noteId = sceneUnderTest.play(context2);

        assertInstanceOf(Note.class, noteRepository.query(noteId));
        assertFalse(workspaceRepository.query(workspaceId).getCommittedNotes().isEmpty());
    }
}