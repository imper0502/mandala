package digi.joy.mandala;

import com.google.common.eventbus.EventBus;
import digi.joy.mandala.common.adapters.infra.MandalaEventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MandalaConfig {
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
