package kr.buglifer.moaseoul.bot;

import com.squareup.moshi.Moshi;
import kr.buglifer.moaseoul.configuration.properties.ChatGPTProperties;
import kr.buglifer.moaseoul.utility.OkHttpUtility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BotService {

    private final ChatGPTProperties chatGPTProperties;

    public JSONArray generateBotCourse() {
        final String BOT_API_URL = "https://api.openai.com/v1/chat/completions";
        Response botResponse = OkHttpUtility.request(BOT_API_URL, generateHeaders(), generateRequestBody(), HttpMethod.POST);
        BotAPIResponse botAPIResponse = null;
        try {
            botAPIResponse = new Moshi.Builder()
                    .build()
                    .adapter(BotAPIResponse.class)
                    .fromJson(botResponse.body()
                                         .source());
            botResponse.close();
        } catch (Exception e) {
            return null;
        }
        if (botAPIResponse == null || botAPIResponse.getChoices() == null || botAPIResponse.getChoices().length == 0 || botAPIResponse.getChoices()[0]
                .getMessage() == null || botAPIResponse.getChoices()[0]
                .getMessage()
                .getContent() == null) {
            return null;
        }
        String botMessage = botAPIResponse.getChoices()[0]
                .getMessage()
                .getContent();
        try {
            JSONArray result = new JSONArray(botMessage);
            return result;
        } catch (JSONException e) {
            return null;
        }
    }

    private RequestBody generateRequestBody() {
        final String APPENDER_MESSAGE = " 라는 내용을 서울특별시에서 3가지 위,경도 좌표 알려줄래? 답변은 아래 같은 json array 형태로 알려줘 [{name:홍대 / 신촌 지역,latitude:37.5546, longtitude:126.9246,reason:놀기좋아서},...]";
        final String TEST_FRONT_MESSAGE = "한국 정서를 전부 느낄 수 있는 장소를 추천해줄래?";
        final String PROPERTY_MODEL = "model";
        final String PROPERTY_MESSAGES = "messages";
        final String PROPERTY_ROLE = "role";
        final String PROPERTY_CONTENT = "content";
        final String VALUE_MODEL = "gpt-3.5-turbo";
        final String VALUE_ROLE = "user";

        JSONObject jsonObjectBody = new JSONObject();

        try {
            jsonObjectBody.put(PROPERTY_MODEL, VALUE_MODEL);
            JSONArray jsonArrayMessage = new JSONArray();
            JSONObject jsonObjectMessage = new JSONObject();
            jsonObjectMessage.put(PROPERTY_ROLE, VALUE_ROLE);
            jsonObjectMessage.put(PROPERTY_CONTENT, TEST_FRONT_MESSAGE + APPENDER_MESSAGE);
            jsonArrayMessage.put(jsonObjectMessage);
            jsonObjectBody.put(PROPERTY_MESSAGES, jsonArrayMessage);
        } catch (JSONException e) {
            return null;
        }
        return RequestBody.create(jsonObjectBody.toString(), MediaType.parse("application/json"));
    }

    private Headers generateHeaders() {
        final String AUTHORIZATION_HEADER_KEY = "Authorization";
        final String BEARER_VALUE = "Bearer ";
        return new Headers.Builder().add(AUTHORIZATION_HEADER_KEY, BEARER_VALUE + chatGPTProperties.getKey())
                                    .build();
    }

    @Getter
    @Setter
    public static class BotAPIResponse {

        private String id;

        private String object;

        private Long created;

        private String model;

        private Choices[] choices;

        @Getter
        @Setter
        public static class Choices {

            private String finishReason;

            private Long index;

            private Message message;

            @Getter
            @Setter
            public static class Message {

                private String role;

                private String content;
            }
        }
    }
}
