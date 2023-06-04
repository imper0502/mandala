package digi.joy.mandala.common.adapters.handler;

import com.google.common.eventbus.EventBus;
import digi.joy.mandala.common.adapters.listener.MandalaEventListener;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MandalaEventHandler {
    private final EventBus eventBus;

    private final MandalaEventListener workspaceEventListener;

    @Autowired
    public MandalaEventHandler(
            EventBus eventBus,
            MandalaEventListener workspaceEventListener) {
        this.eventBus = eventBus;
        this.workspaceEventListener = workspaceEventListener;
    }

    @PostConstruct
    public void init() {
        eventBus.register(workspaceEventListener);
    }

    @PreDestroy
    public void destroy() {
        eventBus.unregister(workspaceEventListener);
    }
}
