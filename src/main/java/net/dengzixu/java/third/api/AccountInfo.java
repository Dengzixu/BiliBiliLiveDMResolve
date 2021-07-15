package net.dengzixu.java.third.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class AccountInfo {
    private static final String URL = "https://api.bilibili.com/x/space/acc/info?mid=";


    public String getFace(long uid) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        Request request = new Request.Builder()
                .get()
                .url(URL + uid)
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            if (response.code() != 200) {
                return null;
            }

            String body = Objects.requireNonNull(response.body()).string();

            if ("".equals(body)) {
                return null;
            }


            ObjectMapper objectMapper = new ObjectMapper();

            Map<String, Object> bodyMap = objectMapper.readValue(body, Map.class);

            Map<String, Object> dataMap = (Map<String, Object>) bodyMap.get("data");

            return (String) dataMap.get("face");
        } catch (NullPointerException | IOException e) {
            return null;
        }

    }
}
