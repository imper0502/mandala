package digi.joy.mandala.boot.configuration;

import com.google.common.eventbus.EventBus;
import digi.joy.mandala.infra.event.MandalaEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MandalaEventConfig {
    @Bean
    public EventBus eventBus() {
        return new EventBus();
    }

    @Bean
    public MandalaEventBus workspaceEventBus() {
        return new MandalaEventBus(eventBus());
    }

    @Bean
    public MandalaEventBus noteEventBus() {
        return new MandalaEventBus(eventBus());
    }
}
