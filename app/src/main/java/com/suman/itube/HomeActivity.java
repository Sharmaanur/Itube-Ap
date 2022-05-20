package com.suman.itube;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.suman.itube.DB.DBHandler;
import com.suman.itube.DB.VideoModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeActivity extends AppCompatActivity {
    DBHandler db;
    EditText link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        db = new DBHandler(this);
        link = findViewById(R.id.ed_video_link);
        findViewById(R.id.btn_play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!link.getText().toString().trim().isEmpty()) {
                    Intent intent = new Intent(HomeActivity.this, PlayerActivity.class);
                    intent.putExtra("link", getYouTubeId(link.getText().toString()));
                    startActivity(intent);
                }else {
                    Toast.makeText(HomeActivity.this, "Paste video link", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.btn_add_playlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveVideo();
            }
        });
        findViewById(R.id.btn_myplaylist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, PlayListActivity.class));
            }
        });
    }
    void saveVideo(){
        if (!link.getText().toString().trim().isEmpty()){
        VideoModel clientIDModel = new VideoModel();
        clientIDModel.setVideoLink(link.getText().toString().trim());
        db.addVideos(clientIDModel);
            Toast.makeText(this, "Link Added", Toast.LENGTH_SHORT).show();
        }
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