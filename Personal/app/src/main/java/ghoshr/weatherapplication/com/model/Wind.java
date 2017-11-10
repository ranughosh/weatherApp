package ghoshr.weatherapplication.com.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Wind {
    @SerializedName("speed")
    @Expose
    private String mSpeed;
    @SerializedName("deg")
    @Expose
    private String mDeg;
    @SerializedName("gust")
    @Expose
    private String mGust;

    public String getSpeed() {
        return mSpeed;
    }

    public void setSpeed(String speed) {
        this.mSpeed = speed;
    }

    public String getDeg() {
        return mDeg;
    }

    public void setDeg(String deg) {
        this.mDeg = deg;
    }

    public String getGust() {
        return mGust;
    }

    public void setGust(String gust) {
        this.mGust = gust;
    }
}
