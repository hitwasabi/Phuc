package com.bkacad.tu.phuc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    protected ListView lvSong;
    protected List<Song> songList;
    protected SongAdapter songAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvSong = findViewById(R.id.lvSong);
        songList = new ArrayList<>();
        songList.add(new Song("Quốc Ca",0,"Văn Cao"));
        songList.add(new Song("Bạc Phận",1,"Jack 5 củ"));
        songList.add(new Song("Một bước yêu vạn dặm đau",2,"MR.Siro"));
        songList.add(new Song("Happy Birth Day",3,"Bé Louise"));
        songList.add(new Song("Em yêu trường em",4,"Bé Trang Thư"));
        songList.add(new Song("Bạn Cấp 3",5,"Lou Hoàng"));

        //Adapter
        songAdapter = new SongAdapter(this,songList);
        //Set Adapter cho listview
        lvSong.setAdapter(songAdapter);


        lvSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent j = new Intent(MainActivity.this,SongActivity.class);
                j.putExtra("position", i);

                //Start Activity
                startActivity(j);


            }
        });
    }
}