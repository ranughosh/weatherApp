package ghoshr.weatherapplication.com.ui;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ghoshr.weatherapplication.com.presenter.MainPresenter;
import ghoshr.weatherapplication.com.view.MainView;
import ghoshr.weatherapplication.com.adapter.ForcastAdapter;
import ghoshr.weatherapplication.com.R;
import ghoshr.weatherapplication.com.model.ResponseObj;

public class MainActivity extends AppCompatActivity implements
       View.OnClickListener,MainView {

    @Override
    public void setError(String error) {
        mErrorText.setText(error);
        mErrorText.setVisibility(View.VISIBLE);
        discardData();
    }

    private String TAG = "MainActivity";
    private RecyclerView mForcastRecycler;
    private ForcastAdapter mForcastAdapter;
    private ResponseObj responseObj;

    private TextView mHighTemp;
    private TextView mLowTemp;
    private TextView mAvgTemp;
    private TextView mPressure;
    private TextView mHumidity;
    private TextView mWind;
    private Button mSubmit;
    private EditText mLocation;//would have done auto mLocation detection with mLocation permission if had more time
    private String LAST_LOC_STR = "last_location";
    private MainPresenter mMainPresenter;
    private TextView mErrorText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initUI() {
        mForcastRecycler = (RecyclerView) findViewById(R.id.recyclerview_forecast);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mForcastRecycler.setLayoutManager(layoutManager);
        mForcastRecycler.setHasFixedSize(true);


        mHighTemp = (TextView) findViewById(R.id.temp_high);
        mLowTemp = (TextView) findViewById(R.id.temp_low);
        mAvgTemp = (TextView) findViewById(R.id.temp_avg);
        mPressure = (TextView) findViewById(R.id.pressure);
        mHumidity = (TextView) findViewById(R.id.humidity);
        mWind = (TextView) findViewById(R.id.wind);
        mSubmit = (Button) findViewById(R.id.submit_btn);
        mLocation = (EditText) findViewById(R.id.location);
        mErrorText =(TextView) findViewById(R.id.error_text);
        mSubmit.setOnClickListener(this);
        mMainPresenter =new MainPresenter(this);
        mForcastAdapter = new ForcastAdapter(this);
        mForcastAdapter.setPresenter(mMainPresenter);
        mMainPresenter.setListInterface(mForcastAdapter);
        mForcastRecycler.setAdapter(mForcastAdapter);
        mMainPresenter.initialSetUp();
    }


    @Override
    public void onClick(View v) {
        Log.d(TAG, "searchText:" + mLocation.getText().toString());
        onBtnClickListener(mLocation.getText().toString());

    }

    public void showProgress() {
        hideKeyboard();
        findViewById(R.id.progress_indicator).setVisibility(View.VISIBLE);
        findViewById(R.id.weather_layout).setVisibility(View.GONE);
    }

    public void hideProgress() {
        findViewById(R.id.progress_indicator).setVisibility(View.GONE);
        findViewById(R.id.weather_layout).setVisibility(View.VISIBLE);
    }

    private void discardData() {
        mForcastAdapter.setWeatherArray(null);
    }


    public  void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)this.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onPause() {

        super.onPause();
        mMainPresenter.storeLastLocation();
        //store the last mLocation

    }

    @Override
    public void setTempMax(String tempMax) {
        mHighTemp.setText(getString(R.string.temp_max,tempMax));
    }

    @Override
    public void setTempMin(String tempMin) {
        mLowTemp.setText(getString(R.string.temp_min,tempMin));
    }

    @Override
    public void setTempAvg(String tempAvg) {
        mAvgTemp.setText(getString(R.string.temp_avg,tempAvg));
    }

    @Override
    public void setHumidity(String humidityText) {
        mHumidity.setText(getString(R.string.humidity,humidityText));
    }

    @Override
    public void setWind(String windText) {
        mWind.setText(getString(R.string.wind,windText));
    }

    @Override
    public void setPressure(String pressureText) {

        mPressure.setText("Presssure:" + pressureText + " hPa");
    }

    @Override
    public String getLastLocation() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String restoredText = preferences.getString(LAST_LOC_STR, null);
        return restoredText;
    }

    @Override
    public void setLastLocation(String lastLocation) {
        mLocation.setText(lastLocation);
    }


    @Override
    public void onBtnClickListener(String location) {
       mMainPresenter.loadWeather(location);
    }

    @Override
    public void storeLastLocation() {
        if (!TextUtils.isEmpty(mLocation.getText().toString())) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(LAST_LOC_STR, mLocation.getText().toString());
            editor.apply();
        }
    }

    @Override
    public void setError() {
         findViewById(R.id.weather_layout).setVisibility(View.GONE);
         mErrorText.setText(getString(R.string.error_text));
         mErrorText.setVisibility(View.VISIBLE);
         discardData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMainPresenter.unsubsribe();//for orientation changes
    }


}
