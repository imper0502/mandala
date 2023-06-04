package digi.joy.mandala.common.services;

import digi.joy.mandala.common.entities.event.MandalaEvent;


public interface MandalaEventPublisher {
    void commit(MandalaEvent... events);

    void postAll();
}
