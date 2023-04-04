package kr.buglifer.moaseoul.utility;

import lombok.Getter;
import lombok.Setter;
import okhttp3.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

class OkHttpUtilityTest {

    @Test
    void request_method는_HttpRequest후_response를_수신받을수있다() {
        // Given
        String url = "https://naver.com";
        HttpMethod httpMethod = HttpMethod.GET;

        // When
        Response response = OkHttpUtility.request(url, null, null, httpMethod);

        // Then
        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.isSuccessful());
    }

    @Test
    void requestWithConvert는_ResponseObject에따라_HttpRequest후_response로_변환해준다() {
        // Given
        String url = "https://entertain.naver.com/ranking/page.json?&type=hit_total&date=20230405";
        HttpMethod httpMethod = HttpMethod.GET;

        // When
        NaverNewsResposne naverNewsResposne = OkHttpUtility.requestWithConvert(url, null, null, httpMethod, NaverNewsResposne.class);

        // Then
        Assertions.assertNotNull(naverNewsResposne);
        Assertions.assertNotNull(naverNewsResposne.getNewsListHtml());
        Assertions.assertNotNull(naverNewsResposne.getCount());
        Assertions.assertNotNull(naverNewsResposne.getPage());
        Assertions.assertNotNull(naverNewsResposne.getDate());
    }

    @Getter
    @Setter
    public static class NaverNewsResposne {
        private String newsListHtml;

        private String date;

        private Integer count;

        private Integer page;
    }
}
