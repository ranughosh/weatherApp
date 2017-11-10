package ghoshr.weatherapplication.com.networkcall;

import ghoshr.weatherapplication.com.AppConstants.Constants;
import ghoshr.weatherapplication.com.model.ResponseObj;

/**
 * Created by ghoshr on 10/16/2017.
 */

public class ApiUtils {
    public static WeatherService getWeatherService() {
        return RetrofitClient.getClient(Constants.endPointUrl).create(WeatherService.class);
    }
}