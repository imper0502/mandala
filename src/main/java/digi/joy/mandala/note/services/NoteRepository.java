package digi.joy.mandala.note.services;

import digi.joy.mandala.note.adapters.infra.NoteMapper;
import digi.joy.mandala.note.entities.Note;
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
