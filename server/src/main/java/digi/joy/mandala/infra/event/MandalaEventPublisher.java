package digi.joy.mandala.infra.event;

public interface MandalaEventPublisher {
    void commit(MandalaEvent... events);

    void postAll();
}
