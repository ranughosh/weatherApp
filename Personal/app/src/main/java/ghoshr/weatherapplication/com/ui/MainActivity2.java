package ghoshr.weatherapplication.com.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import ghoshr.weatherapplication.com.R;
import ghoshr.weatherapplication.com.databinding.ActivityMainVmBinding;
import ghoshr.weatherapplication.com.viewModel.UpdateLocationIntf;
import ghoshr.weatherapplication.com.viewModel.WeatherViewModel;

/**
 * Created by ghoshr on 1/17/2018.
 */
//this one will use view model pattern
public class MainActivity2 extends AppCompatActivity implements UpdateLocationIntf{

    private static String TAG=MainActivity2.class.getSimpleName();
    private String SAVED_OBJ_KEY="SAVEDOBJ";

    @Override
    public void updateLocation(String loc) {
      weatherViewModel.setZipCode(loc);
    }

    private ActivityMainVmBinding mainActivity2Binding;
    private WeatherViewModel weatherViewModel;
    private EditText zipCode;
    private String responseObj;
    //private Composite

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* if (savedInstanceState != null) {
           responseObj = savedInstanceState.getString(SAVED_OBJ_KEY);
        }*/
        mainActivity2Binding = DataBindingUtil.setContentView(this,R.layout.activity_main_vm);
        weatherViewModel=new WeatherViewModel();
        mainActivity2Binding.setWvm(weatherViewModel);
        //weatherViewModel= ViewModelProviders.of(this).get(MainViewModel.class);
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
        /*zipCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                  hideKeyBoard(zipCode);
                }
            }
        });*/

    }

   /* @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(SAVED_OBJ_KEY, weatherViewModel.getWeatherObj().toString());
        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }*/

}
