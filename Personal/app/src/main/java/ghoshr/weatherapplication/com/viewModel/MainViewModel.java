package ghoshr.weatherapplication.com.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.ArrayList;

import ghoshr.weatherapplication.com.AppConstants.Constants;
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
 * Created by ghoshr on 1/17/2018.
 */

public class MainViewModel extends ViewModel {

    @Nullable
    private String zipCode;
    Observable<ResponseObj> mObservable;
    private Disposable mDisposable;
    private static String TAG=MainViewModel.class.getSimpleName();
    private WeatherService mWeatherService;

    public void setTempAvg(MutableLiveData<String> tempAvg) {
        this.tempAvg = tempAvg;
    }

    public MutableLiveData<String> getTempAvg() {
        return tempAvg;
    }

    private MutableLiveData<String> tempAvg;

    public MutableLiveData<ResponseObj> getObservableWeather() {
        return observableWeather;
    }

    public void setObservableWeather(MutableLiveData<ResponseObj> observableWeather) {
        this.observableWeather = observableWeather;
    }

    private MutableLiveData<ResponseObj> observableWeather;

    public ResponseObj getWeatherObj() {
        return weatherObj;
    }

    public void setWeatherObj(ResponseObj weatherObj) {
        this.weatherObj = weatherObj;
    }

    private ResponseObj weatherObj;


    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    private Observer<ResponseObj> mObserver =new Observer<ResponseObj>() {

        @Override
        public void onSubscribe(@NonNull Disposable d) {
            mDisposable=d;
        }

        @Override
        public void onComplete() {
            //mMainView.hideProgress();
            Log.d(TAG, "on completed");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "error:" + e.toString());
            //mMainView.hideProgress();
            //mMainView.setError();
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
                        //updateWeatherValues();

                    }
                }

            }
        }
    };

    public String getUrl() {
        StringBuilder strUrl=new StringBuilder(Constants.endPointUrl);
        strUrl.append("weather?");
        if(!TextUtils.isEmpty(zipCode)) {
            if (Utility.isZip(zipCode)) {
                strUrl.append("zip=" + zipCode);
            } else {
                strUrl.append("q=" + zipCode);
            }
            strUrl.append("&appid=" + Constants.appId);
            strUrl.append("&units=" + Constants.TEMP_UNIT);
        }
        Log.d(TAG,"str url:"+strUrl.toString());
        return strUrl.toString();

    }

    public void loadWeather() {
        if(mWeatherService==null){
            mWeatherService= ApiUtils.getWeatherService();
        }
        if(!TextUtils.isEmpty(zipCode)) {

            mObservable = mWeatherService.getWeatherWUrl(getUrl());
            mObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(mObserver);

        }
    }




}
