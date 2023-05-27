package digi.joy.mandala.note.entities.event;

import digi.joy.mandala.common.entities.event.MandalaEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class NoteCreatedInWorkspace extends MandalaEvent {
    private final UUID noteId;
    private final String workspaceId;
}
