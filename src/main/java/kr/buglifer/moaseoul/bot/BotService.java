package kr.buglifer.moaseoul.bot;

import com.squareup.moshi.Moshi;
import kr.buglifer.moaseoul.configuration.properties.ChatGPTProperties;
import kr.buglifer.moaseoul.utility.OkHttpUtility;
import lombok.*;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class BotService {

    private final ChatGPTProperties chatGPTProperties;

    public BotCourseResponse generateBotCourse(BotCourseRequest request) {
        BotCourseResponse validateRequest = validateRequest(request);
        if (!validateRequest.isSuccess()) {
            return validateRequest;
        }
        final String BOT_API_URL = "https://api.openai.com/v1/chat/completions";
        Response botResponse = OkHttpUtility.request(BOT_API_URL, generateHeaders(), generateRequestBody(request), HttpMethod.POST);
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
            return toBotCourseResponse(result);
        } catch (JSONException e) {
            return null;
        }
    }

    private BotCourseResponse validateRequest(BotCourseRequest request) {
        if (request.getMessage() == null || request.getMessage()
                                                   .isEmpty()) {
            final String FAIL_REASON = "Message가 존재하지 않습니다.";
            return new BotCourseResponse(List.of(BotCourseResponse.BotCourseResponseBody.builder()
                                                                                        .reason(FAIL_REASON)
                                                                                        .build()), false);
        }
        return new BotCourseResponse(null, true);
    }

    private BotCourseResponse toBotCourseResponse(JSONArray jsonArray) {
        final String PROPERTY_NAME = "name";
        final String PROPERTY_LATITUDE = "latitude";
        final String PROPERTY_LONGITUDE = "longitude";
        final String PROPERTY_REASON = "reason";

        List<BotCourseResponse.BotCourseResponseBody> botCourseResponseBodyList = new ArrayList<>();
        IntStream.range(0, jsonArray.length())
                 .mapToObj(index -> {
                     try {
                         return jsonArray.getJSONObject(index);
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                     return null;
                 })
                 .forEach(object -> {
                     BotCourseResponse.BotCourseResponseBody.BotCourseResponseBodyBuilder botCourseResponseBodyBuilder = BotCourseResponse.BotCourseResponseBody.builder();
                     try {
                         botCourseResponseBodyBuilder.name((String) object.get(PROPERTY_NAME));
                         botCourseResponseBodyBuilder.latitude((BigDecimal) object.get(PROPERTY_LATITUDE));
                         botCourseResponseBodyBuilder.longitude((BigDecimal) object.get(PROPERTY_LONGITUDE));
                         botCourseResponseBodyBuilder.reason((String) object.get(PROPERTY_REASON));
                         botCourseResponseBodyList.add(botCourseResponseBodyBuilder.build());
                     } catch (JSONException e) {
                         e.printStackTrace();
                     }
                 });
        return new BotCourseResponse(botCourseResponseBodyList, true);
    }

    private RequestBody generateRequestBody(BotCourseRequest request) {
        final String APPENDER_MESSAGE = " 라는 내용을 서울특별시에서 3가지 위,경도 좌표 알려줄래? 답변은 아래 같은 json array 형태로 알려줘 [{name:홍대 / 신촌 지역,latitude:37.5546, longitude:126.9246,reason:놀기좋아서},...]";
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
            jsonObjectMessage.put(PROPERTY_CONTENT, request.getMessage() + APPENDER_MESSAGE);
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

    @Getter
    @Setter
    public static class BotCourseRequest {

        private String message;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BotCourseResponse {

        List<BotCourseResponseBody> results;

        private boolean success;

        @Getter
        @Setter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor

        public static class BotCourseResponseBody {

            private String name;

            private BigDecimal latitude;

            private BigDecimal longitude;

            private String reason;
        }
    }
}
