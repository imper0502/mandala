package digi.joy.mandala.workspace.event;

import digi.joy.mandala.infra.event.MandalaEvent;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class WorkspaceBuilt extends MandalaEvent {
    private final UUID workspaceId;
}
