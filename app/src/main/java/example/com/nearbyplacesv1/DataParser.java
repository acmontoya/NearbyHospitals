package example.com.nearbyplacesv1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Angel on 12/18/2016.
 */
public class DataParser {
    public List<HashMap<String, String>> parse(String jsonData) {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            Log.d("Places", "parse");
            jsonObject = new JSONObject((String) jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            Log.d("Places", "parse error");
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }

    public HashMap<String,String> parse(JSONObject jObject){

        JSONObject jPlaceDetails = null;
        try {
            /** Retrieves all the elements in the 'places' array */
            jPlaceDetails = jObject.getJSONObject("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /** Invoking getPlaces with the array of json object
         * where each json object represent a place
         */
        return getPlaceDetails(jPlaceDetails);
    }

    private HashMap<String, String> getPlaceDetails(JSONObject jPlaceDetails){

        HashMap<String, String> hPlaceDetails = new HashMap<String, String>();

        String name = "-NA-";
        String reference = "-NA-";
        String vicinity="-NA-";
        String latitude="";
        String longitude="";
        String formatted_address="-NA-";
        String formatted_phone="-NA-";
        try {
            // Extracting Place name, if available
            if(!jPlaceDetails.isNull("name")){
                name = jPlaceDetails.getString("name");
            }

            if(!jPlaceDetails.isNull("reference")){
                reference = jPlaceDetails.getString("reference");
            }

            // Extracting Place Vicinity, if available
            if(!jPlaceDetails.isNull("vicinity")){
                vicinity = jPlaceDetails.getString("vicinity");
            }

            // Extracting Place formatted_address, if available
            if(!jPlaceDetails.isNull("formatted_address")){
                formatted_address = jPlaceDetails.getString("formatted_address");
            }

            // Extracting Place formatted_phone, if available
            if(!jPlaceDetails.isNull("formatted_phone_number")){
                formatted_phone = jPlaceDetails.getString("formatted_phone_number");
            }


            latitude = jPlaceDetails.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = jPlaceDetails.getJSONObject("geometry").getJSONObject("location").getString("lng");

            hPlaceDetails.put("reference", reference);
            hPlaceDetails.put("name", name);
            hPlaceDetails.put("vicinity", vicinity);
            hPlaceDetails.put("lat", latitude);
            hPlaceDetails.put("lng", longitude);
            hPlaceDetails.put("formatted_address", formatted_address);
            hPlaceDetails.put("formatted_phone_number", formatted_phone);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hPlaceDetails;
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray) {
        int placesCount = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<>();
        HashMap<String, String> placeMap = null;
        Log.d("Places", "getPlaces");

        for (int i = 0; i < placesCount; i++) {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);
                Log.d("Places", "Adding places");

            } catch (JSONException e) {
                Log.d("Places", "Error in Adding places");
                e.printStackTrace();
            }
        }
        return placesList;
    }

    private HashMap<String, String> getPlace(JSONObject googlePlaceJson) {
        HashMap<String, String> googlePlaceMap = new HashMap<String, String>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String formatted_address = "-NA-";
        String formatted_phone_number = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        Log.d("getPlace", "Entered");

        try {
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity");
            }
            if(!googlePlaceJson.isNull("formatted_address")){
                formatted_address = googlePlaceJson.getString("formatted_address");
            }
            if(!googlePlaceJson.isNull("formatted_phone_number")){
                formatted_address = googlePlaceJson.getString("formatted_phone_number");
            }

            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = googlePlaceJson.getString("reference");
            googlePlaceMap.put("place_name", placeName);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("formatted_phone_number", formatted_phone_number);
            googlePlaceMap.put("formated_address", formatted_address);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("reference", reference);
            Log.d("getPlace", "Putting Places");
        } catch (JSONException e) {
            Log.d("getPlace", "Error");
            e.printStackTrace();
        }
        return googlePlaceMap;
    }
}
