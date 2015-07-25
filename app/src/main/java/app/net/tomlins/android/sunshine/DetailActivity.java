package app.net.tomlins.android.sunshine;

/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.ShareActionProvider;
import android.widget.TextView;

public class DetailActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_detail);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.weather_detail, new WeatherDetailFragment(), "WeatherDetailFragment'")
                    .commit();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class WeatherDetailFragment extends Fragment {

        public final String LOG_TAG = WeatherDetailFragment.class.getSimpleName();
        private static final String SHARE_FORECAST_DATA = "#sunshine";
        private String mForecastData;

        public WeatherDetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_weather_detail, container, false);

            Intent intent = getActivity().getIntent();
            if (intent!=null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                mForecastData = intent.getStringExtra(Intent.EXTRA_TEXT);
                ((TextView)rootView.findViewById(R.id.detail_text)).setText(mForecastData);
            }

            return rootView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // Inflate the menu; this adds items to the action bar if it is present.
            inflater.inflate(R.menu.menu_weather_detail_fragment, menu);

            // Find the MenuItem that we know has the ShareActionProvider
            MenuItem item = menu.findItem(R.id.menu_item_share);

            // Get its ShareActionProvider
            ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

            // Connect the dots: give the ShareActionProvider its Share Intent
            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(createShareIntent());
            } else {
                Log.i(LOG_TAG, "Unable to create share intent");
            }
            //return true;
        }

        private Intent createShareIntent() {
            Intent mShareIntent = new Intent(Intent.ACTION_SEND);
            mShareIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            mShareIntent.setType("text/plain");
            mShareIntent.putExtra(Intent.EXTRA_TEXT, mForecastData + " " + SHARE_FORECAST_DATA);
            return mShareIntent;

        }
    }
}