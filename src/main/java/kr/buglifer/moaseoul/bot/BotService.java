package kr.buglifer.moaseoul.bot;

import kr.buglifer.moaseoul.utility.OkHttpUtility;
import lombok.*;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpMethod;

@RequiredArgsConstructor
public class BotService {

    public String generateBotCourse() {
        final String BOT_API_URL = "https://api.openai.com/v1/chat/completions";
        Response botResponse = OkHttpUtility.request(BOT_API_URL, generateHeaders(), generateRequestBody(), HttpMethod.POST);

        try {

            botResponse.close();
        } catch (Exception e) {
            return null;
        }
        return "OK";
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
            JSONObject jsonObjectMessage = new JSONObject();
            jsonObjectMessage.put(PROPERTY_ROLE, VALUE_ROLE);
            jsonObjectMessage.put(PROPERTY_CONTENT, TEST_FRONT_MESSAGE + APPENDER_MESSAGE);
            jsonObjectBody.put(PROPERTY_MESSAGES, jsonObjectMessage);
        } catch (JSONException e) {
            return null;
        }
        return RequestBody.create(jsonObjectBody.toString(), MediaType.parse("application/json"));
    }

    private Headers generateHeaders() {
        final String AUTHORIZATION_HEADER_KEY = "Authorization";
        final String BEARER_VALUE = "Bearer ";
        final String BOT_KEY = "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";
        return new Headers.Builder().add(AUTHORIZATION_HEADER_KEY, BEARER_VALUE + BOT_KEY)
                                    .build();
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BotAPIResponse {

    }
}
