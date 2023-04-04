package kr.buglifer.moaseoul.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.moshi.Moshi;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import okio.Buffer;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OkHttpUtility {

    private static final OkHttpClient client = new OkHttpClient.Builder().connectTimeout(40, TimeUnit.SECONDS)
                                                                         .readTimeout(40, TimeUnit.SECONDS)
                                                                         .writeTimeout(40, TimeUnit.SECONDS)
                                                                         .build();

    public static <T> T requestWithConvert(String url, Headers headers, Object request, HttpMethod httpMethod, Class<T> responseClass) {
        ObjectMapper mapper = new ObjectMapper();
        RequestBody requestBody = null;
        try {
            if (request != null) {
                requestBody = RequestBody.create(mapper.writeValueAsString(request), MediaType.parse("application/json"));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Response response = OkHttpUtility.request(url, headers, requestBody, httpMethod);
        T responseObject = null;
        if (response != null) {
            try {
                responseObject = new Moshi.Builder()
                        .build()
                        .adapter(responseClass)
                        .fromJson(Objects.requireNonNull(response.body())
                                         .source());
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.close();
        }
        return responseObject;
    }

    public static Response request(String url, Headers headers, RequestBody requestBody, HttpMethod httpMethod) {
        Request request = createRequest(url, headers, requestBody, httpMethod);
        Response response = null;
        StringBuilder logBuilder = new StringBuilder();
        try {
            response = client.newCall(request)
                             .execute();
            log.info("RequestURL = " + url);
            log.info("RequestBody = {" + requestBodyToString(request) + "} ");
        } catch (IOException e) {
            logBuilder.append("[OKHttp Request Fail]");
            if (request.body() != null) {
                logBuilder.append(Objects.requireNonNull(request.body())
                                         .toString())
                          .append(" RequestBody = {")
                          .append(requestBodyToString(request))
                          .append("} ");
            }
            log.error(logBuilder.toString(), e);
            return response;
        }
        if ((response.body() == null) || !response.isSuccessful()) {
            logBuilder.append("[OKHttp Response is not Succesed]");
            if (request.body() != null) {
                logBuilder.append(Objects.requireNonNull(request.body())
                                         .toString())
                          .append(" RequestBody = {")
                          .append(requestBodyToString(request))
                          .append("} ");
            }
            if (response.body() != null) {
                logBuilder.append("ResponseBody = {")
                          .append(response.body()
                                          .toString())
                          .append("} ");
                try {
                    log.error(response.body()
                                      .string());
                } catch (IOException e) {
                    String message = "Response Body Reading Fail";
                    log.error(message, e);
                    return response;
                }
                response.body()
                        .close();
            }
            log.error(logBuilder.toString());
        }
        return response;
    }

    private static Request createRequest(String url, Headers headers, RequestBody requestBody, HttpMethod httpMethod) {
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null) {
            builder.headers(headers);
        }
        if (requestBody != null) {
            switch (httpMethod) {
                case POST:
                    builder.post(requestBody);
                    break;
                case PUT:
                    builder.put(requestBody);
                    break;
                case PATCH:
                    builder.patch(requestBody);
                    break;
                case OPTIONS:
                    builder.method("OPTIONS", requestBody);
                    break;
                default:
                    break;
            }
        }
        return builder.build();
    }

    private static String requestBodyToString(Request request) {
        try {
            final Request copyRequest = request.newBuilder()
                                               .build();
            if (copyRequest.body() == null) {
                return "Request Body is Null";
            }
            final Buffer buffer = new Buffer();
            copyRequest.body()
                       .writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException e) {
            String message = "Request Body Has Error";
            log.error(message, e);
            return message;
        }
    }
}
