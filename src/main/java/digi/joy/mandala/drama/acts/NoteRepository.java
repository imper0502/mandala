package digi.joy.mandala.drama.acts;

import digi.joy.mandala.drama.acts.mapper.NoteMapper;
import digi.joy.mandala.drama.actors.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NoteRepository {

    private final NoteDataAccessor dataAccessor;

    @Autowired
    public NoteRepository(NoteDataAccessor dataAccessor) {
        this.dataAccessor = dataAccessor;
    }


    public void add(Note note) {
        dataAccessor.add(NoteMapper.transform(note));
    }

    public Note withdraw(UUID id) {
        return NoteMapper.transform(dataAccessor.withdraw(id).orElseThrow());
    }
}
