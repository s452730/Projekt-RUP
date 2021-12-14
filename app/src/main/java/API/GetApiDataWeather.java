package API;

public class GetApiDataWeather {
    double avg_Temp;
    String weather;

    public GetApiDataWeather(double avg_Temp, String weather) {
        this.avg_Temp = avg_Temp;
        this.weather = weather;
    }

    public double getAvg_Temp() {
        return avg_Temp;
    }

    public String getWeather() {
        return weather;
    }

}