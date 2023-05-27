package digi.joy.mandala.common.adapters.infra;

import com.google.common.eventbus.EventBus;
import digi.joy.mandala.common.adapters.api.MandalaEventHandler;
import digi.joy.mandala.common.entities.event.MandalaEvent;
import digi.joy.mandala.common.services.MandalaEventBus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MandalaEventPublisher implements MandalaEventBus {
    private final EventBus eventBus = new EventBus();
    private final List<MandalaEvent> events = new ArrayList<>();

    @Override
    public void commit(MandalaEvent... events) {
        this.events.addAll(List.of(events));
    }

    @Override
    public void postAll() {
        List<MandalaEvent> committedEvents = List.copyOf(events);
        events.clear();
        committedEvents.forEach(eventBus::post);
    }

    public void register(MandalaEventHandler eventHandler) {
        eventBus.register(eventHandler);
    }
}
