package API;
/*      ***USAGE EXAMPLE***

        API.WeatherAPI s = API.WeatherAPI.connection("Poznan", "2021-12-09");
        System.out.println(s.getWeather());
        System.out.println(s.getTemperature());

*/
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class WeatherAPI{
    double temperature;
    String weather;

    public WeatherAPI(double temperature, String weather) {
        this.temperature = temperature;
        this.weather = weather;
    }
    public double getTemperature(){
        return temperature;
    }
    public String getWeather(){
        return weather;
    }

    public static WeatherAPI connection(String city, String date) {

        // OPTIONS
        // String city = "Poznan";
        // String date = "2021-12-09";
        // String hour = "0";

        String key = "992e7cab06164165980213921210812";
        String url = "https://api.weatherapi.com/v1/history.json?key=" + key +
                "&q=" + city + "&dt=" + date;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(WeatherAPI::parse)
                .join();

    }

    private static WeatherAPI parse(String responseBody) {
        JSONObject info = new JSONObject(responseBody);
        JSONObject day = info.getJSONObject("forecast")
                .getJSONArray("forecastday")
                .getJSONObject(0)
                .getJSONObject("day");
        double avgTemp = day.getDouble("avgtemp_c");
        JSONObject condition = day.getJSONObject("condition");
        String weather = condition.getString("text");
        return new WeatherAPI(avgTemp, weather);
        //System.out.println(avgTemp);
        //System.out.println(weather);
    }
}

