package digi.joy.mandala.common.services;

import digi.joy.mandala.common.entities.event.MandalaEvent;


public interface MandalaEventBus {
    void commit(MandalaEvent... events);

    void postAll();
}
