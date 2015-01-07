package io.github.s0cks.jamaican.net;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import javax.net.ssl.HttpsURLConnection;

public final class URLShortener{
    private static String API_URL = "https://www.googleapis.com/urlshortener/v1/url";

    private URLShortener(){}

    public static String expand(String url)
    throws IOException{
        HttpsURLConnection connection = (HttpsURLConnection) new URL(API_URL + "?shortUrl=" + url).openConnection();
        connection.setRequestMethod("GET");
        try(InputStreamReader reader = new InputStreamReader(connection.getInputStream())){
            return new JsonParser().parse(reader).getAsJsonObject().get("longUrl").getAsString();
        }
    }

    public static String shorten(String url)
    throws IOException{
        String request = formRequest(url);
        HttpsURLConnection connection = (HttpsURLConnection) new URL(API_URL).openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.getOutputStream().write(request.getBytes(StandardCharsets.UTF_8));
        connection.getOutputStream().flush();
        connection.getOutputStream().close();

        try(InputStreamReader reader = new InputStreamReader(connection.getInputStream())){
            JsonObject obj = new JsonParser().parse(reader).getAsJsonObject();
            return obj.get("id").getAsString();
        }
    }

    private static String formRequest(String url)
    throws IOException{
        try(java.io.StringWriter writer = new java.io.StringWriter();
            JsonWriter json = new JsonWriter(writer)){

            json.beginObject().name("longUrl").value(url).endObject();
            return writer.toString();
        }
    }
}