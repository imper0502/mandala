package digi.joy.mandala.workspace.entities.event;

import digi.joy.mandala.common.entities.event.MandalaEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class NoteCreatedWithWorkspaceId extends MandalaEvent {
    private final UUID noteId;
    private final UUID workspaceId;
}
