package ghoshr.weatherapplication.com;

import org.junit.Before;
import org.junit.Test;

import ghoshr.weatherapplication.com.model.MainWeather;
import ghoshr.weatherapplication.com.presenter.MainPresenter;
import ghoshr.weatherapplication.com.view.MainView;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private MainView view;

    private MainPresenter presenter;

    private MainWeather weather;

    @Before
    public void setup() {
        view = mock(MainView.class);
        presenter = spy(new MainPresenter(view));
        weather=new MainWeather();
        weather.setTemp("75.06");
        weather.setTempMax("77");
        weather.setTempMin("73.4");
    }

    @Test
    public void testUpdateViewAvgTemp() {
        presenter.setMainWeather(weather);
        verify(view).setTempAvg(eq("75.06"));
    }

    @Test
    public void testUpdateViewMaxTemp() {
        presenter.setMainWeather(weather);
        verify(view).setTempMax(eq("77"));
    }

    @Test
    public void testUpdateViewMinTemp() {
        presenter.setMainWeather(weather);
        verify(view).setTempMin(eq("73.4"));
    }
}