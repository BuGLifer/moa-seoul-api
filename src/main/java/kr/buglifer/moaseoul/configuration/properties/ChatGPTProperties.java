package kr.buglifer.moaseoul.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("chat-gpt")
@Getter
@Setter
public class ChatGPTProperties {

    private String key;
}
