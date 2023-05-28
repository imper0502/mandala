package digi.joy.mandala.drama.actors.event;

import digi.joy.mandala.common.entities.event.MandalaEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class WorkspaceUpdated extends MandalaEvent {
    private final UUID workspaceId;
}
