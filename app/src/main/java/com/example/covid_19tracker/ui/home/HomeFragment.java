package com.example.covid_19tracker.ui.home;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.covid_19tracker.R;
import com.example.covid_19tracker.model.CountryData;
import com.example.covid_19tracker.model.CountryLocation;
import com.example.covid_19tracker.model.CountryLocationRender;
import com.example.covid_19tracker.retrofit.ICovidDataAPI;
import com.example.covid_19tracker.retrofit.RetrofitClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap gMap;
    private MapView liveTrackerMapView;
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private ClusterManager<CountryLocation> clusterManager;
    private ICovidDataAPI dataAPI;
    private List<CountryData> covidAllList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("  World View");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setIcon(R.drawable.ic_map_black_24dp);
        liveTrackerMapView = (MapView)getActivity().findViewById(R.id.Home_mapView);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        liveTrackerMapView.onCreate(mapViewBundle);
        liveTrackerMapView.getMapAsync(this);


        try{
            dataAPI= RetrofitClient.getInstance().create(ICovidDataAPI.class);

            Call<List<CountryData>> call = dataAPI.getCovidData();

            call.enqueue(new Callback<List<CountryData>>() {
                @Override
                public void onResponse(Call<List<CountryData>> call, Response<List<CountryData>> response) {
                    covidAllList=response.body();
                    setMarkersOnMap(covidAllList);

                }

                private void setMarkersOnMap(List<CountryData> covidAllList) {
                    for(CountryData cd: covidAllList){
                        CountryLocation cL = new CountryLocation(cd.getCountryInfo().getCountryLat(),cd.getCountryInfo().getCountryLong(),cd.getCases(),cd.getCountryName());
                        //clustering markers
                        clusterManager.addItem(cL);
                        clusterManager.cluster();
                    }
                }

                @Override
                public void onFailure(Call<List<CountryData>> call, Throwable throwable) {
                    Log.e("debug", throwable.toString());
                }
            });
        }catch(Exception e){
            Log.e("error",""+e.getMessage());
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap=googleMap;
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = gMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getContext(), R.raw.map_style_json));

            if (!success) {
                Log.d("debug", "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e("debug", "Can't find style. Error: ", e);
        }

      //  gMap.setMyLocationEnabled(true);
        setUpClusterManager();
    }

    private void setUpClusterManager() {
        clusterManager = new ClusterManager<CountryLocation>(getActivity(), gMap);
        CountryLocationRender renderer = new CountryLocationRender(getActivity(), gMap, clusterManager);
        clusterManager.setRenderer(renderer);
        gMap.setOnCameraIdleListener(clusterManager);
        gMap.setOnMarkerClickListener(clusterManager);
    }

    @Override
    public void onResume() {
        super.onResume();
        liveTrackerMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();


        liveTrackerMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        liveTrackerMapView.onStop();
    }

    @Override
    public void onPause() {
        liveTrackerMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        liveTrackerMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        liveTrackerMapView.onLowMemory();
    }
}
