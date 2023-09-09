package digi.joy.mandala.note.event;

import digi.joy.mandala.infra.event.MandalaEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class NoteCreated extends MandalaEvent {
    private final UUID noteId;
}
