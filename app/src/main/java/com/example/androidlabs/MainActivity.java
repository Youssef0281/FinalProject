package com.example.androidlabs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.androidlabs.item.Item;
import com.example.androidlabs.item.parser.XMLUtils;

import com.example.androidlabs.item.Item;
import com.example.androidlabs.R;
import com.example.androidlabs.item.parser.XMLUtils;
import com.example.androidlabs.item.parser.XMLUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private Button latestNewsButton;
    private Button archiveNewsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CoordinatorLayout coordinatorLayout = findViewById(R.id.coordinator);
        Snackbar.make(coordinatorLayout, R.string.Snackbar, Snackbar.LENGTH_SHORT).show();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                GetHttpConnectionTask task = new GetHttpConnectionTask();
                                task.execute();
                                return true;
                            case R.id.navigation_dashboard:
                                startActivity(getArchiveNewsStartingIntent());
                                return true;
                            case R.id.navigation_notifications:
                                startActivity(getHelpStartingIntent());
                                return true;
                        }
                        return false;
                    }
                });
    }








    private void showToastUnableConnection() {
        Toast toast = Toast.makeText(this, R.string.unableToConnectToast, Toast.LENGTH_SHORT);
        toast.show();
    }


    private class GetHttpConnectionTask extends AsyncTask<Void, Void, ArrayList<Item>> {

        @Override
        protected ArrayList<Item> doInBackground(Void... voids) {
            try {
                URL rssFeedUrl = new URL("https://feeds.bbci.co.uk/news/technology/rss.xml");

                try (InputStream in = rssFeedUrl.openStream();
                     Reader reader = new BufferedReader(new InputStreamReader(in))) {
                    return XMLUtils.parseItems(reader);
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    return null;
                }


            } catch (IOException e) { // exception must not be thrown, URL is valid
                return null;
            }
        }


        @Override
        protected void onPostExecute(@Nullable ArrayList<Item> items) {
            if (items == null) {
                showToastUnableConnection();
            } else {
                Intent latestNewsActivityIntent = getLatestNewsStartingIntent()
                        .putParcelableArrayListExtra(LatestNewsActivity.extrasItems, items);
                startActivity(latestNewsActivityIntent);
            }

        }
    }


    private Intent getLatestNewsStartingIntent() {
        return new Intent(this, LatestNewsActivity.class);
    }
    private Intent getHelpStartingIntent() {
        return new Intent(this, HelpActivity.class);
    }

    private Intent getArchiveNewsStartingIntent() {
        return new Intent(this, ArchiveNewsActivity.class);
    }
}