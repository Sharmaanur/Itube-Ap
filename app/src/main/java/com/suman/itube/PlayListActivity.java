package com.suman.itube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.suman.itube.DB.DBHandler;
import com.suman.itube.DB.VideoModel;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlayListActivity extends AppCompatActivity {
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);
        dbHandler = new DBHandler(this);
        List<VideoModel> alllinks = dbHandler.getAllVideos();
        ArrayAdapter adapter = new ArrayAdapter<VideoModel>(this, android.R.layout.simple_dropdown_item_1line, alllinks);
        //adapter.setDropDownViewResource(android.R.layout.simple_list_item_2);
        ListView listView = (ListView) findViewById(R.id.mobile_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(PlayListActivity.this, PlayerActivity.class);
                intent.putExtra("link", getYouTubeId(alllinks.get(i).toString()));
                startActivity(intent);
            }
        });
    }
    private String getYouTubeId (String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if(matcher.find()){
            return matcher.group();
        } else {
            return "error";
        }
    }
}