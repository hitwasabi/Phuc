package com.bkacad.tu.phuc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
public class SongActivity extends AppCompatActivity {
    TextView Title, numLeft, numRight;
    SeekBar seekBar;
    ImageButton btnBack, btnPlay, btnNext;
    ArrayList<Song> arraySong;
    int position = 1;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        InitUI();
        AddSong();
        KhoiTaoMediaPlayer();

        Intent j = getIntent();



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                if(position > arraySong.size() - 1){
                    position = 0;
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.stop);
                SetTime();
                UpdateTime();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                if(position < 0){
                    position = arraySong.size() - 1;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.stop);
                SetTime();
                UpdateTime();
            }
        });


        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                } else{
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.stop);
                }
                SetTime();
                UpdateTime();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    private void KhoiTaoMediaPlayer(){
        mediaPlayer = MediaPlayer.create(SongActivity.this, arraySong.get(position).getFile());
        Title.setText(arraySong.get(position).getTitle());
    }

    private void UpdateTime(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhDang = new SimpleDateFormat("mm:ss");
                numLeft.setText(dinhDang.format(mediaPlayer.getCurrentPosition()));
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        position++;
                        if(position > arraySong.size() - 1){
                            position = 0;
                        }
                        KhoiTaoMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.stop);
                        SetTime();
                        UpdateTime();
                    }
                });
                handler.postDelayed(this, 500);
            }
        },100);
    }

    private void SetTime(){
        SimpleDateFormat dinhDang = new SimpleDateFormat("mm:ss");
        numRight.setText(dinhDang.format(mediaPlayer.getDuration()));
        seekBar.setMax(mediaPlayer.getDuration());
    }

    private void AddSong () {
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Quốc Ca", R.raw.quocca));
        arraySong.add(new Song("Bạc Phận", R.raw.bacphan));
    }


    private void InitUI() {
        numLeft = (TextView) findViewById(R.id.numleft);
        numRight = (TextView) findViewById(R.id.numRight);
        Title = (TextView) findViewById(R.id.Title);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
    }
}

