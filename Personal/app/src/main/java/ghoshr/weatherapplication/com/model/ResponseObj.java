package ghoshr.weatherapplication.com.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseObj {

    @SerializedName("coord")
    @Expose
    private Coordinate mCoord;
    @SerializedName("weather")
    @Expose
    private List<WeatherObject> mWeatherList;
    @SerializedName("main")
    @Expose
    private MainWeather mMainWeather;
    @SerializedName("wind")
    @Expose
    private Wind mWind;
    @SerializedName("cod")
    @Expose
    private String mErrorCode;
    @SerializedName("message")
    @Expose
    private String mErrorMessage;

    public String getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(String mErrorCode) {
        this.mErrorCode = mErrorCode;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setErrorMessage(String mErrorMessage) {
        this.mErrorMessage = mErrorMessage;
    }



    public Wind getWind() {
        return mWind;
    }

    public void setWind(Wind wind) {
        this.mWind = wind;
    }

    public Coordinate getCoord() {
        return mCoord;
    }

    public void setCoord(Coordinate coord) {
        this.mCoord = coord;
    }

    public List<WeatherObject> getWeather() {
        return mWeatherList;
    }

    public void setWeather(List<WeatherObject> weather) {
        mWeatherList = weather;
    }

    public MainWeather getMain() {
        return mMainWeather;
    }

    public void setMain(MainWeather mainWeather) {
        this.mMainWeather = mainWeather;
    }

}
