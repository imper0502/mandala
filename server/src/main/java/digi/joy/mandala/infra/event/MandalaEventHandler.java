package digi.joy.mandala.infra.event;

import com.google.common.eventbus.EventBus;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MandalaEventHandler {
    private final EventBus eventBus;
    private final MandalaEventListener workspaceEventListener;

    @PostConstruct
    public void init() {
        eventBus.register(workspaceEventListener);
    }

    @PreDestroy
    public void destroy() {
        eventBus.unregister(workspaceEventListener);
    }
}
