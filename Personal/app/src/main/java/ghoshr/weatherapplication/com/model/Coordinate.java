package ghoshr.weatherapplication.com.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class Coordinate {
    @SerializedName("lon")
    @Expose
    private String mLon;
    @SerializedName("lat")
    @Expose
    private String mLat;
}
