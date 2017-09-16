package com.example.android.newnews;

import android.content.AsyncTaskLoader;
import android.content.Context;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {

    String URL;
    ArrayList<News> newses;

    public NewsLoader(Context context, String url) {
        super(context);
        URL = url;

    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public ArrayList<News> loadInBackground() {

        if (this.URL == null) return null;
        newses = new ArrayList<News>();
        URL url = createUrl(this.URL);
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);

            if (baseJsonResponse.has("response")) {
                JSONObject response = baseJsonResponse.getJSONObject("response");

                if (response.has("results")) {
                    JSONArray featureArray = response.getJSONArray("results");

                    for (int i = 0; i < featureArray.length(); i++) {
                        JSONObject object = featureArray.getJSONObject(i);
                        String section;
                        String title;
                        String weburl;
                        String date = "";
                        String authors = "";

                        if (object.has("sectionName") && object.has("webTitle") && object.has("webUrl")) {

                            if (!object.getString("sectionName").equalsIgnoreCase("crosswords")) {
                                title = object.getString("webTitle");
                                section = object.getString("sectionName");
                                weburl = object.getString("webUrl");
                                if (object.has("webPublicationDate")) {
                                    date = object.getString("webPublicationDate");
                                }

                                if (object.has("tags")) {
                                    JSONArray tags = object.getJSONArray("tags");

                                    for (int h = 0; h < tags.length(); h++) {
                                        JSONObject a = tags.getJSONObject(h);
                                        authors += a.get("webTitle") + " ";

                                    }
                                }
                                newses.add(new News(title, weburl, section, date, authors));
                            }
                        }
                    }
                }
            }

        } catch (JSONException | IOException j) {
            j.printStackTrace();
        }
        return newses;
    }

    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
            return null;
        }
        return url;
    }

    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
