package digi.joy.mandala.drama.entities.event;

import digi.joy.mandala.common.entities.event.MandalaEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class WorkspaceEntered extends MandalaEvent {
    private final UUID workspaceId;
    private final UUID userId;
}
