package digi.joy.mandala.application.services;

import digi.joy.mandala.application.entities.Note;
import digi.joy.mandala.application.services.mapper.NoteMapper;
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

    public Note query(UUID id) {
        return NoteMapper.transform(dataAccessor.query(id).orElseThrow());
    }
}
