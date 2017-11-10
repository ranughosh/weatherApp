package ghoshr.weatherapplication.com.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class WeatherObject {
    @SerializedName("id")
    @Expose
    private String mId;
    @SerializedName("main")
    @Expose
    private String mMain;
    @SerializedName("description")
    @Expose
    private String mDescription;
    @SerializedName("icon")
    @Expose
    private String mIcon;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        this.mId = id;
    }

    public String getMain() {
        return mMain;
    }

    public void setMain(String main) {
        this.mMain = main;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        this.mIcon = icon;
    }
}
