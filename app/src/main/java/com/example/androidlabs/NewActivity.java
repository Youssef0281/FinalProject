package com.example.androidlabs;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidlabs.adapters.NewsAdapter;
import com.example.androidlabs.item.Item;

import com.example.androidlabs.adapters.NewsAdapter;
import com.example.androidlabs.item.Item;

import java.util.ArrayList;

/**
 * Base Activity for displaying the list of items via RecyclerView
 */
public class NewsActivity extends AppCompatActivity {
    protected RecyclerView itemsView;
    protected ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_view);
    }

    protected void setRecyclerView(NewsAdapter adapter) {
        itemsView = (RecyclerView) findViewById(R.id.itemsView);
        itemsView.setLayoutManager(new LinearLayoutManager(this));
        itemsView.setAdapter(adapter);
    }

    public class SeeArticleClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemIndex = itemsView.getChildLayoutPosition((View) v.getParent().getParent().getParent());

            Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(items.get(itemIndex).getLink()));
            startActivity(intent);
        }
    }
}