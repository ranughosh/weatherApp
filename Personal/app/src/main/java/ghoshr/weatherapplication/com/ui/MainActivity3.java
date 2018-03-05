package ghoshr.weatherapplication.com.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ghoshr.weatherapplication.com.R;
import ghoshr.weatherapplication.com.model.ResponseObj;
import ghoshr.weatherapplication.com.viewModel.LoadWeatherInterface;
import ghoshr.weatherapplication.com.viewModel.MainViewModel;
import ghoshr.weatherapplication.com.viewModel.UpdateLocationIntf;

/**
 * Created by ghoshr on 1/26/2018.
 */

public class MainActivity3 extends AppCompatActivity implements UpdateLocationIntf,LoadWeatherInterface {
    private static String TAG=MainActivity3.class.getSimpleName();
    private MainViewModel mainViewModel;
    private EditText zipCode;

    @Override
    public void updateLocation(String loc) {
        mainViewModel.setZipCode(loc);
    }

    @Override
    public void loadWeather() {
        mainViewModel.loadWeather();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainViewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        zipCode=(EditText)findViewById(R.id.location);
        zipCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void afterTextChanged(Editable s) {
                Log.e(TAG, "afterTextChanged:\t" +s.toString());
                updateLocation(s.toString());
            }
        });
        subscribe();
        Button submitBtn=(Button) findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(onSubmitClicked());

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

    private void subscribe(){
        final Observer<ResponseObj> weatherObserver=new Observer<ResponseObj>() {
            @Override
            public void onChanged(@Nullable ResponseObj responseObj) {

                ((TextView) findViewById(R.id.temp_avg)).setText(responseObj.getMain().getTemp());
            }
        };

        mainViewModel.getObservableWeather().observe(this,weatherObserver);
    }

    private void hideKeyBoard(View view,Context context){
        AppCompatActivity parentActivity=(AppCompatActivity)context;
        InputMethodManager imm = (InputMethodManager)context.getSystemService(parentActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
