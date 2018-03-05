package ghoshr.weatherapplication.com.presenter;

import android.text.TextUtils;
import android.util.Log;

import org.reactivestreams.Subscription;

import java.util.ArrayList;

import ghoshr.weatherapplication.com.AppConstants.Constants;
import ghoshr.weatherapplication.com.adapter.ForcastAdapter;
import ghoshr.weatherapplication.com.model.MainWeather;
import ghoshr.weatherapplication.com.model.ResponseObj;
import ghoshr.weatherapplication.com.model.WeatherObject;
import ghoshr.weatherapplication.com.networkcall.ApiUtils;
import ghoshr.weatherapplication.com.networkcall.WeatherService;
import ghoshr.weatherapplication.com.util.Utility;
import ghoshr.weatherapplication.com.view.MainView;
import ghoshr.weatherapplication.com.view.RecyclerListInterface;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by ghoshr on 10/13/2017.
 */

public class MainPresenter {

    private MainView mMainView;
    private WeatherService mWeatherService;

    private String TAG = "MainPresenter";
    private ArrayList<WeatherObject> mWeatherObjectList;
    private String mLocation;
    private RecyclerListInterface mListInterface;



    private MainWeather mMainWeather;
    Observable<ResponseObj> mObservable;
    private Disposable mDisposable;

    public void setListInterface(RecyclerListInterface mListInterface) {
        this.mListInterface = mListInterface;
    }


    public MainPresenter(MainView mainView) {
        this.mMainView = mainView;
        mWeatherService = ApiUtils.getWeatherService();

    }

    public MainWeather getMainWeather() {
        return mMainWeather;
    }

    public void setMainWeather(MainWeather mainWeather) {
        this.mMainWeather = mainWeather;
        updateWeatherHeader(mMainWeather);
    }

    private Observer<ResponseObj> mObserver =new Observer<ResponseObj>() {

        @Override
        public void onSubscribe(@NonNull Disposable d) {
            mDisposable=d;
        }

        @Override
        public void onComplete() {
            mMainView.hideProgress();
            Log.d(TAG, "on completed");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(TAG, "error:" + e.toString());
            mMainView.hideProgress();
            mMainView.setError();
        }

        @Override
        public void onNext(ResponseObj weatherResponse) {

            if (weatherResponse != null) {
                if (weatherResponse.getErrorMessage() != null) {


                    }
                    mMainView.setError(weatherResponse.getErrorMessage());

                } else {
                    if(weatherResponse.getMain()!=null && weatherResponse.getMain().getTemp()!=null) {
                        Log.d(TAG, "weatherResponse:" + weatherResponse.getMain().getTemp());
                        setMainWeather(weatherResponse.getMain());
                    if(weatherResponse.getWind()!=null && !TextUtils.isEmpty(weatherResponse.getWind().getSpeed()))
                        mMainView.setWind(weatherResponse.getWind().getSpeed());
                    if (weatherResponse.getWeather() != null && weatherResponse.getWeather().size() > 0) {
                        Log.d(TAG, "weatherResponse Array:" + weatherResponse.getWeather().size());
                        setWeatherObjectList(new ArrayList(weatherResponse.getWeather()));
                        mListInterface.setdata(mWeatherObjectList);
                    }
                }
            }
        }
    };

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        this.mLocation = location;
    }



    public String getUrl(String location) {
        StringBuilder strUrl=new StringBuilder(Constants.endPointUrl);
        strUrl.append("weather?");
        if(!TextUtils.isEmpty(location)) {
            if (Utility.isZip(location)) {
                strUrl.append("zip=" + location);
            } else {
                strUrl.append("q=" + location);
            }
            strUrl.append("&appid=" + Constants.appId);
            strUrl.append("&units=" + Constants.TEMP_UNIT);
        }
        Log.d(TAG,"str url:"+strUrl.toString());
        return strUrl.toString();

    }

    public void loadWeather(String location) {
        if(!TextUtils.isEmpty(location)) {
            mMainView.showProgress();
            setLocation(location);
            mObservable = mWeatherService.getWeatherWUrl(getUrl(location));
            mObservable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(mObserver);
        }
    }


    private void updateWeatherHeader(MainWeather currMainWeather) {
        mMainView.setTempMax(currMainWeather.getTempMax());
        mMainView.setTempMin(currMainWeather.getTempMin());
        mMainView.setTempAvg(currMainWeather.getTemp());
        mMainView.setPressure(currMainWeather.getPressure());
        mMainView.setHumidity(currMainWeather.getHumidity());
    }

    public void bindViewHolder(ForcastAdapter.ForecastAdapterViewHolder viewHolder, int position) {
        Log.d(TAG, "bind viewholder:" + mWeatherObjectList.size());
        WeatherObject mWeatherObject = mWeatherObjectList.get(position);
        viewHolder.setDescription(mWeatherObject.getDescription());
        viewHolder.setImage(Constants.imageUrl + mWeatherObject.getIcon() + ".png");
    }


    public ArrayList<WeatherObject> getData() {
        return mWeatherObjectList;
    }

    public void initialSetUp() {
        if (!TextUtils.isEmpty(mMainView.getLastLocation())) {
            mMainView.setLastLocation(mMainView.getLastLocation());
            loadWeather(mMainView.getLastLocation());
        }
    }

    public void storeLastLocation() {
        mMainView.storeLastLocation();
    }

    public void unsubsribe(){
        if(mDisposable !=null && !mDisposable.isDisposed()){
           mDisposable.dispose();
        }
    }

    public void setWeatherObjectList(ArrayList<WeatherObject>  weatherObjectList){
        mWeatherObjectList = weatherObjectList;
        mListInterface.setdata(mWeatherObjectList);
    }
}
