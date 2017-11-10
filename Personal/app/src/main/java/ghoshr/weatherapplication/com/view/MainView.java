package ghoshr.weatherapplication.com.view;


import android.support.v4.app.LoaderManager;

/**
 * Created by ghoshr on 10/13/2017.
 */

public interface MainView {

    public void showProgress();
    public void hideProgress();
    public void setTempMax(String tempMax);
    public void setTempMin(String tempMin);
    public void setTempAvg(String tempAvg);
    public void setHumidity(String humidity);
    public void setWind(String wind);
    public void setPressure(String pressure);

    public String getLastLocation();
    public void setLastLocation(String lastLocation);

    public void onBtnClickListener(String location);
    public void hideKeyboard();
    public void storeLastLocation();

    public void setError();

    public void setError(String error);
}
