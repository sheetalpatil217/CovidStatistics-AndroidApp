package com.example.covid_19tracker.ui.dashboard;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.covid_19tracker.R;
import com.example.covid_19tracker.model.CountryData;
import com.example.covid_19tracker.retrofit.ICovidDataAPI;
import com.example.covid_19tracker.retrofit.RetrofitClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.snackbar.Snackbar;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {
    private MaterialSpinner spinner;
    private AnyChartView pieChart;
    private Pie pie;
    private ICovidDataAPI dataAPI;
    private List<CountryData> covidAllList = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("  Statistics");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setIcon(R.drawable.ic_pie_chart_black_24dp);
        spinner = (MaterialSpinner) getActivity().findViewById(R.id.dashboard_country_spinner);
        pieChart = (AnyChartView)getActivity().findViewById(R.id.countryDashboard_pieChart);
        pie = AnyChart.pie();
        pie.labels().position("outside");
        pie.legend().title().enabled(false);
        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);
        pieChart.setChart(pie);
        try{
            dataAPI= RetrofitClient.getInstance().create(ICovidDataAPI.class);

            Call<List<CountryData>> call = dataAPI.getCovidData();

            call.enqueue(new Callback<List<CountryData>>() {
                @Override
                public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                    covidAllList=response.body();
                    setSpinnerData(covidAllList);

                }

                private void setSpinnerData(List<CountryData> covidAllList) {
                    List<String> countries = covidAllList.stream().map((CountryData) -> CountryData.getCountryName()).collect(Collectors.toList());
                    spinner.setItems(countries);
                }

                @Override
                public void onFailure(Call<List<CountryData>> call, Throwable throwable) {
                    Log.e("debug", throwable.toString());
                }
            });
        }catch(Exception e){
            Log.e("error",""+e.getMessage());
        }

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                setPieChartData(item);
            }

            private void setPieChartData(String item) {
                pie.legend().title().enabled(true);
                pie.legend().title()
                        .text("Cases in "+item)
                        .padding(0d, 0d, 10d, 0d);
                pie.data(getCountrySpecificData(item));
            }
        });
    }

    private List<DataEntry> getCountrySpecificData(String item) {
        ArrayList<DataEntry> entries = new ArrayList<>();
        CountryData countryData = covidAllList.stream().filter(a -> a.getCountryName() == item).collect(Collectors.toList()).get(0);
        entries.add(new ValueDataEntry("Active", Integer.valueOf(countryData.getActive().toString())));
        entries.add(new ValueDataEntry("Recovered", Integer.valueOf(countryData.getRecovered())));
        entries.add(new ValueDataEntry("Deaths",Integer.valueOf(countryData.getDeaths())));
        return entries;
    }


}
