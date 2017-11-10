package ghoshr.weatherapplication.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ghoshr.weatherapplication.com.R;
import ghoshr.weatherapplication.com.model.WeatherObject;
import ghoshr.weatherapplication.com.presenter.MainPresenter;
import ghoshr.weatherapplication.com.view.RecyclerListInterface;
import ghoshr.weatherapplication.com.view.RecyclerRowView;

public class ForcastAdapter extends RecyclerView.Adapter<ForcastAdapter.ForecastAdapterViewHolder> implements RecyclerListInterface{



    private Context mContext;
    private MainPresenter mPresenter;
    private ArrayList<WeatherObject> mWeatherArray;

    @Override
    public void setdata(ArrayList<WeatherObject> weatherArray) {
        setWeatherArray(weatherArray);
    }

    public void setWeatherArray(ArrayList<WeatherObject> weatherArray) {
        mWeatherArray=weatherArray;
        notifyDataSetChanged();
    }



    public ForcastAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setPresenter(MainPresenter presenter){
        this.mPresenter =presenter;
    }
    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(mContext)
                .inflate(R.layout.forecast_list_row, parent, false);

        return new ForecastAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder holder, int position) {
        mPresenter.bindViewHolder(holder,position);
    }

    @Override
    public int getItemCount() {
       if (mWeatherArray == null)
            return 0;
        else
            return mWeatherArray.size();
    }

    public class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements RecyclerRowView {


        TextView mWeatherSummary;
        ImageView mWeatherImage;

        ForecastAdapterViewHolder(View view) {
            super(view);
            mWeatherSummary = (TextView) view.findViewById(R.id.weather_description);
            mWeatherImage = (ImageView) view.findViewById(R.id.quick_image);


        }

        @Override
        public void setDescription(String descr) {
          mWeatherSummary.setText(descr);
        }

        @Override
        public void setImage(String imageUrl) {
            Picasso.with(mContext).load(imageUrl).into(mWeatherImage);
        }

    }

}
