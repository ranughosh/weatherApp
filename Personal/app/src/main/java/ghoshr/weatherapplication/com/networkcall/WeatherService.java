package ghoshr.weatherapplication.com.networkcall;


import ghoshr.weatherapplication.com.model.ResponseObj;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;


/**
 * Created by ghoshr on 10/13/2017.
 */

public interface WeatherService {
    @GET
    Observable<ResponseObj> getWeatherWUrl(@Url String url);
}
