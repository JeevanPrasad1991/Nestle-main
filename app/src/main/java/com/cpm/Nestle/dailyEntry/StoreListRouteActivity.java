package com.cpm.Nestle.dailyEntry;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.cpm.Nestle.R;
import com.cpm.Nestle.delegates.CoverageBean;
import com.cpm.Nestle.getterSetter.MappingJourneyPlan;
import com.cpm.Nestle.utilities.CommonString;
import com.cpm.Nestle.utilities.DataParser;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoreListRouteActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ArrayList<CoverageBean> storelist = new ArrayList<CoverageBean>();
    ArrayList<MappingJourneyPlan> storelist1 = new ArrayList<MappingJourneyPlan>();
    int global_index = 0;
    LatLng origin, destination;
    double lat, lon;

    ArrayList<String> color_list = new ArrayList<>();

    //for optimize path
    ArrayList<CoverageBean> sortedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_list_route);
        if (getIntent().getSerializableExtra(CommonString.TAG_OBJECT) != null) {
            storelist1 = (ArrayList<MappingJourneyPlan>)getIntent().getSerializableExtra(CommonString.TAG_OBJECT);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        color_list.add("#f44336");
        color_list.add("#1e88e5");
        color_list.add("#2e7d32");
        color_list.add("#ab47bc");
        color_list.add("#ffcc80");
        color_list.add("#fdd835");

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        showRoute(global_index);
    }

    public void showRoute(int index){
        LinearLayout tv = (LinearLayout) this.getLayoutInflater().inflate(R.layout.marker_view, null, false);

        TextView desc = (TextView) tv.findViewById(R.id.tv_desc);
        desc.setText(storelist.get(global_index).getSTORE_NAME());

        tv.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        tv.layout(0, 0, tv.getMeasuredWidth(), tv.getMeasuredHeight());

        tv.setDrawingCacheEnabled(true);
        tv.buildDrawingCache();
        Bitmap bm = tv.getDrawingCache();
        lat = Double.parseDouble(storelist.get(index).getLATITUDE());
        lon = Double.parseDouble(storelist.get(index).getLONGITUDE());
        LatLng loc = new LatLng(lat, lon);

        mMap.addMarker(new MarkerOptions().position(loc).anchor(0.5f, 0.5f).icon(BitmapDescriptorFactory.fromBitmap(bm)));

        if(index==0){
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        }

        if(index+1<storelist.size()){
            origin = new LatLng(lat, lon);
            destination = new LatLng(Double.parseDouble(storelist.get(index+1).getLATITUDE()), Double.parseDouble(storelist.get(index+1).getLONGITUDE()));

          /*  String url = getUrl(origin, destination);
            Log.d("onMapClick", url.toString());
            FetchUrl FetchUrl = new FetchUrl();

            // Start downloading json data from Google Directions API
            FetchUrl.execute(url);*/

            showRoute(index+1);
        }
    }

    private String getUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;


        // Sensor enabled
        String sensor = "sensor=false";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();
            Log.d("downloadUrl", data.toString());
            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    // Fetches data from url passed
    private class FetchUrl extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try {
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("Background Task data", data.toString());
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);

        }
    }

    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                Log.d("ParserTask",jsonData[0].toString());
                DataParser parser = new DataParser();
                Log.d("ParserTask", parser.toString());

                // Starts parsing data
                routes = parser.parse(jObject);
                Log.d("ParserTask","Executing routes");
                Log.d("ParserTask",routes.toString());

            } catch (Exception e) {
                Log.d("ParserTask",e.toString());
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for (int i = 0; i < result.size(); i++) {
                points = new ArrayList<>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(10);

               /* Random rnd = new Random();
                int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));*/
                int color;
                if(global_index<color_list.size()){
                    color  = Color.parseColor(color_list.get(global_index));
                }
                else {
                    color = Color.parseColor(color_list.get(colorIdex(global_index)));
                }
                lineOptions.color(color);

                Log.d("onPostExecute","onPostExecute lineoptions decoded");

            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                mMap.addPolyline(lineOptions);
                global_index++;
                if(global_index<storelist.size()){
                    showRoute(global_index);
                }
            }
            else {
                Log.d("onPostExecute","without Polylines drawn");
            }
        }
    }

    public int colorIdex(int current_index){
        int index = current_index;
        while(index >= color_list.size()){
            index = index - color_list.size();
        }

        return index;
    }

    void getShortestPath(CoverageBean store){

        sortedList.add(store);

        if(sortedList.size()<storelist.size()){
            CoverageBean current_loc=new CoverageBean();
            float shortest_distance=-1;
            for(int i=1; i<storelist.size();i++){
                float [] dist = new float[1];
                double lat, lon, lat1, lon1;
                lat = Double.parseDouble(store.getLATITUDE());
                lon = Double.parseDouble(store.getLONGITUDE());
                lat1 = Double.parseDouble(storelist.get(i).getLATITUDE());
                lon1 = Double.parseDouble(storelist.get(i).getLONGITUDE());
                Location.distanceBetween(lat, lon, lat1,lon1, dist);

                if(shortest_distance==-1 || dist[0]<shortest_distance){
                    shortest_distance = dist[0];
                    current_loc = storelist.get(i);
                }
            }

            getShortestPath(current_loc);
        }
        else {

        }

    }
}
