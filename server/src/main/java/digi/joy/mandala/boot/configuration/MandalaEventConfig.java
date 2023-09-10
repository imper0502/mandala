package digi.joy.mandala.boot.configuration;

import com.google.common.eventbus.EventBus;
import digi.joy.mandala.infra.event.MandalaEventHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MandalaEventConfig {
    @Bean
    public EventBus eventBus() {
        return new EventBus();
    }

    @Bean
    public MandalaEventHandler workspaceEventBus() {
        return new MandalaEventHandler(eventBus());
    }

    @Bean
    public MandalaEventHandler noteEventBus() {
        return new MandalaEventHandler(eventBus());
    }
}
