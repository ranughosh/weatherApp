package ghoshr.weatherapplication.com;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import ghoshr.weatherapplication.com.model.MainWeather;
import ghoshr.weatherapplication.com.presenter.MainPresenter;
import ghoshr.weatherapplication.com.ui.MainActivity;
import ghoshr.weatherapplication.com.view.MainView;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Mockito.mock;

/**
 * Created by ghoshr on 10/10/2017.
 */
@RunWith(AndroidJUnit4.class)
public class EspressoTest {


    private MainView view;

    private MainPresenter presenter;

    private MainWeather weather;

    private Context context;

    private MainActivity activity;

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class,false,true);

    @Before
    public void init(){

        activity= mActivityRule.getActivity();
        context = mActivityRule.getActivity().getBaseContext();
        //view =activity;
        presenter =new MainPresenter(mActivityRule.getActivity());
        weather=new MainWeather();
        weather.setTemp("75.06");
        weather.setTempMax("77");
        weather.setTempMin("73.4");
    }

    @Test
    public void testAvgTempDisplayed(){
        /*System.out.println(String.valueOf(activity==null));*/
        activity.setTempAvg(weather.getTemp());
        String tempText=context.getString(R.string.temp_avg,weather.getTemp());
        //presenter.setMainWeather(weather);
        //onView(withId(R.id.temp_avg)).check(matches(withText(tempText)));
    }

    @Test
    public void buttonShouldDisplayProgressBar() {
        //onView(withId(R.id.submit_btn)).perform(click());
        //onView(withId(R.id.recyclerview_forecast)).check(matches(isDisplayed()));
    }

}
