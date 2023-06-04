package digi.joy.mandala.common.adapters.infra;

import com.google.common.eventbus.EventBus;
import digi.joy.mandala.common.adapters.listener.MandalaEventListener;
import digi.joy.mandala.common.entities.event.MandalaEvent;
import digi.joy.mandala.common.services.infra.MandalaEventPublisher;

import java.util.ArrayList;
import java.util.List;

public class MandalaEventBus implements MandalaEventPublisher {
    private final EventBus eventBus;
    private final List<MandalaEvent> events = new ArrayList<>();
    private final List<MandalaEvent> eventHistory = new ArrayList<>();

    public MandalaEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void commit(MandalaEvent... events) {
        this.events.addAll(List.of(events));
    }

    @Override
    public void postAll() {
        List<MandalaEvent> committedEvents = List.copyOf(events);
        events.clear();
        eventHistory.addAll(committedEvents);
        committedEvents.forEach(eventBus::post);
    }

    public void register(MandalaEventListener eventHandler) {
        eventBus.register(eventHandler);
    }

    public List<MandalaEvent> history() {
        return eventHistory;
    }

    public void cleanHistory() {
        eventHistory.clear();
    }
}
