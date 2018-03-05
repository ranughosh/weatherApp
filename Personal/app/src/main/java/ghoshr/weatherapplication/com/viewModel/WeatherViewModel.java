package ghoshr.weatherapplication.com.viewModel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;

import java.util.ArrayList;

import ghoshr.weatherapplication.com.AppConstants.Constants;
import ghoshr.weatherapplication.com.databinding.ActivityMainVmBinding;
import ghoshr.weatherapplication.com.model.MainWeather;
import ghoshr.weatherapplication.com.model.ResponseObj;
import ghoshr.weatherapplication.com.networkcall.ApiUtils;
import ghoshr.weatherapplication.com.networkcall.WeatherService;
import ghoshr.weatherapplication.com.util.Utility;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by ghoshr on 1/18/2018.
 */

public class WeatherViewModel extends BaseObservable {

    public WeatherViewModel(){
        mWeatherService = ApiUtils.getWeatherService();
    }

    public WeatherViewModel(ResponseObj responseObj){
        weatherObj=responseObj;
        mWeatherService = ApiUtils.getWeatherService();
    }


    public ResponseObj getWeatherObj() {
        return weatherObj;
    }

    public void setWeatherObj(ResponseObj weatherObj) {
        this.weatherObj = weatherObj;
    }

    private ResponseObj weatherObj;
    Observable<ResponseObj> mObservable;
    private Disposable mDisposable;
    private static String TAG = WeatherViewModel.class.getSimpleName();
    private WeatherService mWeatherService;

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Nullable
    private String zipCode;
    private Observer<ResponseObj> mObserver = new Observer<ResponseObj>() {

        @Override
        public void onSubscribe(@NonNull Disposable d) {
            mDisposable = d;
        }

        @Override
        public void onComplete() {

            Log.d(TAG, "on completed");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "error:" + e.toString());

        }

        @Override
        public void onNext(ResponseObj weatherResponse) {

            if (weatherResponse != null) {
                if (weatherResponse.getErrorMessage() != null) {

                    Log.d(TAG, "error message:" + weatherResponse.getErrorMessage());
                } else {
                    if (weatherResponse.getMain() != null && weatherResponse.getMain().getTemp() != null) {
                        Log.d(TAG, "weatherResponse:" + weatherResponse.getMain().getTemp());
                        weatherObj = weatherResponse;
                        updateWeatherValues();

                    }
                }

            }
        }
    };

    @Bindable
    public String getTempAvg() {
        if (weatherObj != null && weatherObj.getMain() != null) {
            return weatherObj.getMain().getTemp();
        }
        return null;
    }

    public void updateWeatherValues(){
        setTempAvg();
        setTempMax();
        setTempMin();
        setPressure();
        setHumidity();
        setWind();
    }

    public void setTempAvg(){
        notifyPropertyChanged(BR.tempAvg);
    }

    public void setTempMax(){
        notifyPropertyChanged(BR.tempMax);
    }

    public void setTempMin(){
        notifyPropertyChanged(BR.tempMin);
    }

    public void setPressure(){
        notifyPropertyChanged(BR.pressure);
    }

    public void setHumidity(){
        notifyPropertyChanged(BR.humidity);
    }

    public void setWind(){
        notifyPropertyChanged(BR.windSpeed);
    }

    @Bindable
    public String getTempMin() {
        if (weatherObj != null && weatherObj.getMain() != null) {
            return weatherObj.getMain().getTempMin();
        }
        return null;
    }

    @Bindable
    public String getTempMax() {
        if (weatherObj != null && weatherObj.getMain() != null) {
            return weatherObj.getMain().getTempMax();
        }
        return null;
    }

    @Bindable
    public String getPressure() {
        if (weatherObj != null && weatherObj.getMain() != null) {
            return weatherObj.getMain().getPressure();
        }
        return null;
    }

    @Bindable
    public String getHumidity() {
        if (weatherObj != null && weatherObj.getMain() != null) {
            return weatherObj.getMain().getHumidity();
        }
        return null;
    }

    @Bindable
    public String getWindSpeed() {
        if (weatherObj != null && weatherObj.getWind() != null)
            return weatherObj.getWind().getSpeed();
        return null;
    }

    public String getUrl() {
        StringBuilder strUrl = new StringBuilder(Constants.endPointUrl);
        strUrl.append("weather?");
        if (!TextUtils.isEmpty(zipCode)) {
            if (Utility.isZip(zipCode)) {
                strUrl.append("zip=" + zipCode);
            } else {
                strUrl.append("q=" + zipCode);
            }
            strUrl.append("&appid=" + Constants.appId);
            strUrl.append("&units=" + Constants.TEMP_UNIT);
        }
        Log.d(TAG, "str url:" + strUrl.toString());
        return strUrl.toString();

    }

    public void loadWeather() {
        if (!TextUtils.isEmpty(zipCode)) {

            mObservable = mWeatherService.getWeatherWUrl(getUrl());
            mObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(mObserver);
        }
    }

    public View.OnClickListener onSubmitClicked() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Submit Clicked", Toast.LENGTH_SHORT).show();
                loadWeather();
                hideKeyBoard(view,view.getContext());
            }
        };
    }

    private void hideKeyBoard(View view,Context context){
        AppCompatActivity parentActivity=(AppCompatActivity)context;
        InputMethodManager imm = (InputMethodManager)context.getSystemService(parentActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
