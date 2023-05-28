package digi.joy.mandala.application.services.scenario;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.application.entities.Note;
import digi.joy.mandala.application.entities.Workspace;
import digi.joy.mandala.application.services.NoteContextBuilders;
import digi.joy.mandala.application.services.NoteRepository;
import digi.joy.mandala.application.services.WorkspaceContextBuilders;
import digi.joy.mandala.application.services.WorkspaceRepository;
import digi.joy.mandala.application.services.scenario.context.BuildWorkspaceContext;
import digi.joy.mandala.application.services.scenario.context.CreateNoteContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest
public class CreateNoteScenarioTest {
    private CreateNoteScenario sceneUnderTest;
    private final NoteRepository noteRepository;
    private final WorkspaceRepository workspaceRepository;
    private final MandalaEventBus eventListener;
    private final BuildWorkspaceScenario buildWorkspaceScenario;

    @Autowired
    public CreateNoteScenarioTest(NoteRepository noteRepository, WorkspaceRepository workspaceRepository, MandalaEventBus eventListener, BuildWorkspaceScenario buildWorkspaceScenario) {
        this.noteRepository = noteRepository;
        this.workspaceRepository = workspaceRepository;
        this.eventListener = eventListener;
        this.buildWorkspaceScenario = buildWorkspaceScenario;
    }

    @BeforeEach
    void setUp() {
        this.sceneUnderTest = new CreateNoteScenario(noteRepository, eventListener);
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
        UUID workspaceId = buildWorkspaceScenario.play(context1);

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