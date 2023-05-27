package digi.joy.mandala.workspace.entities.event;

import digi.joy.mandala.common.entities.event.MandalaEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class WorkspaceUpdated extends MandalaEvent {
    private final String workspaceId;
}
