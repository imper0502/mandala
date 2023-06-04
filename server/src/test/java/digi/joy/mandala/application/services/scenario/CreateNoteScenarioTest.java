package digi.joy.mandala.application.services.scenario;

import digi.joy.mandala.application.services.infra.exception.RepositoryException;
import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.application.entities.Note;
import digi.joy.mandala.application.entities.Workspace;
import digi.joy.mandala.application.services.utils.NoteContextBuilders;
import digi.joy.mandala.application.services.infra.NoteRepository;
import digi.joy.mandala.application.services.utils.WorkspaceContextBuilders;
import digi.joy.mandala.application.services.infra.WorkspaceRepository;
import digi.joy.mandala.application.services.context.BuildWorkspaceContext;
import digi.joy.mandala.application.services.context.CreateNoteContext;
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

        UUID id = sceneUnderTest.createNote(readModel);

        assertInstanceOf(Note.class, noteRepository.query(id));
    }

    @Test
    void createNoteInWorkspace() throws RepositoryException {
        BuildWorkspaceContext context1 = WorkspaceContextBuilders.buildWorkspaceScenario()
                .workspaceName("TEST_WORKSPACE")
                .build();
        UUID workspaceId = buildWorkspaceScenario.buildWorkspace(context1);

        assertInstanceOf(Workspace.class, workspaceRepository.query(workspaceId));
        CreateNoteContext context2 = NoteContextBuilders.createNoteScene()
                .title("TEST_NOTE")
                .content(List.of("TEST_CONTENT"))
                .workspaceId(workspaceId)
                .build();
        UUID noteId = sceneUnderTest.createNote(context2);

        assertInstanceOf(Note.class, noteRepository.query(noteId));
        assertFalse(workspaceRepository.query(workspaceId).getCommittedNotes().isEmpty());
    }
}