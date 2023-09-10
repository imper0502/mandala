package digi.joy.mandala.infra.event;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public abstract class MandalaEvent {
    protected final UUID id = UUID.randomUUID();
    protected final Instant occurredTime = Instant.now();
}
