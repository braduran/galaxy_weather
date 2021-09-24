package co.com.galaxy.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class LocalCache {

    @Bean
    public Map<String, Object> getCache(){
        return new HashMap<>();
    }
}
