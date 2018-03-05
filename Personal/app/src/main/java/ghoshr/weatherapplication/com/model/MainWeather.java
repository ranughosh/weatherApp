package ghoshr.weatherapplication.com.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class MainWeather {

    @SerializedName("temp")
    @Expose
    private String mTemp;
    @SerializedName("humidity")
    @Expose
    private String mHumidity;
    @SerializedName("pressure")
    @Expose
    private String mPressure;
    @SerializedName("temp_min")
    @Expose
    private String mTempMin;
    @SerializedName("temp_max")
    @Expose
    private String mTempMax;

    public String getTemp() {
        return mTemp;
    }

    public void setTemp(String temp) {
        this.mTemp = temp;
    }

    public String getHumidity() {
        return mHumidity;
    }

    public void setHumidity(String humidity) {
        this.mHumidity = humidity;
    }

    public String getPressure() {
        return mPressure;
    }

    public void setPressure(String pressure) {
        this.mPressure = pressure;
    }

    public String getTempMin() {
        return mTempMin;
    }

    public void setTempMin(String tempMin) {
        this.mTempMin = tempMin;

    }

    public String getTempMax() {
        return mTempMax;
    }

    public void setTempMax(String tempMax) {
        this.mTempMax = tempMax;
    }
}
