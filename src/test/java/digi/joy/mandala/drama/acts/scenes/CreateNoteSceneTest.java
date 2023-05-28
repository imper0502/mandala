package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.acts.NoteContextBuilders;
import digi.joy.mandala.drama.acts.NoteRepository;
import digi.joy.mandala.drama.acts.scenes.contexts.CreateNoteContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CreateNoteSceneTest {

    private final NoteRepository noteRepository;
    private final MandalaEventBus eventListener;
    private CreateNoteScene caseUnderTest;

    @Autowired
    public CreateNoteSceneTest(NoteRepository noteRepository, MandalaEventBus eventListener) {
        this.noteRepository = noteRepository;
        this.eventListener = eventListener;
    }

    @BeforeEach
    void setUp() {
        this.caseUnderTest = new CreateNoteScene(noteRepository, eventListener);
    }

    @Test
    void createNote() {
        CreateNoteContext readModel = NoteContextBuilders.createNoteScene()
                .title("TEST_NOTE")
                .content(List.of("TEST_CONTENT"))
                .build();
        caseUnderTest.play(readModel);
    }
}