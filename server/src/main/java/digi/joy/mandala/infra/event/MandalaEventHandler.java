package digi.joy.mandala.infra.event;

import com.google.common.eventbus.EventBus;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class MandalaEventHandler implements MandalaEventPublisher {

    private final EventBus eventBus;
    private final List<MandalaEvent> events = new ArrayList<>();
    private final List<MandalaEvent> eventHistory = new ArrayList<>();

    @Override
    public void commit(MandalaEvent... events) {
        Collections.addAll(this.events, events);
    }

    @Override
    public void postAll() {
        List.copyOf(events).forEach(event -> {
            eventBus.post(event);
            eventHistory.add(event);
        });
        events.clear();
    }

    public void register(MandalaEventListener eventListener) {
        eventBus.register(eventListener);
    }

    public List<MandalaEvent> history() {
        return eventHistory;
    }

    public void cleanHistory() {
        eventHistory.clear();
    }
}
