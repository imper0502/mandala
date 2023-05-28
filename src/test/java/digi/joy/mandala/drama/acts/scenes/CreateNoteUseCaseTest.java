package digi.joy.mandala.drama.acts.scenes;

import digi.joy.mandala.common.services.MandalaEventBus;
import digi.joy.mandala.drama.acts.NoteRepository;
import digi.joy.mandala.drama.acts.scenes.contexts.InputOfCreateNote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class CreateNoteUseCaseTest {

    private final NoteRepository noteRepository;
    private final MandalaEventBus eventListener;
    private CreateNote caseUnderTest;

    @Autowired
    public CreateNoteUseCaseTest(NoteRepository noteRepository, MandalaEventBus eventListener) {
        this.noteRepository = noteRepository;
        this.eventListener = eventListener;
    }

    @BeforeEach
    void setUp() {
        this.caseUnderTest = new CreateNote(noteRepository, eventListener);
    }

    @Test
    void createNote() {
        InputOfCreateNote readModel = InputOfCreateNote.builder()
                .title("TEST_NOTE")
                .content(List.of("TEST_CONTENT"))
                .build();
        caseUnderTest.execute(readModel);
    }
}