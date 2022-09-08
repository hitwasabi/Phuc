package com.bkacad.tu.phuc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
public class SongActivity extends AppCompatActivity {
    TextView Title, numLeft, numRight;
    SeekBar seekBar;
    ImageButton btnBack, btnPlay, btnNext;
    //    khai báo arrList như này mới ko bị báo lỗi rỗng khi thực hiện các thao tác ở dưới
    private ArrayList<Song> arraySong = new ArrayList<>();
    int position, uri ;
    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        InitUI();
//        lỗi hàm addsong đã sửa cmt ở dưới
        AddSong();


        Intent i = getIntent();
        Bundle bundle=i.getExtras();
        position= (int)bundle.getInt("position",0);
//        đổi lại uri tương ứng với kiểu dữ liệu int của thuộc tính File
        uri= arraySong.get(position).getFile();
        if(mediaPlayer!=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }

//        chọn lại đúng hàm create với kiểu tham số int không dùng tham số uri
        mediaPlayer = MediaPlayer.create(SongActivity.this,uri);
        Title.setText(arraySong.get(position).getTitle());

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                if(position > arraySong.size() - 1){
                    position = 0;
                }
//                giống nút back
                if(mediaPlayer.isPlaying()){
                    btnPlay.setImageResource(R.drawable.play);
                    mediaPlayer.pause();

                }
                Title.setText(arraySong.get(position).getTitle());
                uri= arraySong.get(position).getFile();
                mediaPlayer = MediaPlayer.create(SongActivity.this,uri);
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
//                kiểm tra xem có đang tồn tại mediaPlayer không
                if(mediaPlayer.isPlaying()){
                    btnPlay.setImageResource(R.drawable.play);
                    mediaPlayer.pause();

                }
//                đặt lại text
                Title.setText(arraySong.get(position).getTitle());
//                gán lại uri và mediaPlayer như ở phần cmt đầu tiên
                uri= arraySong.get(position).getFile();
                mediaPlayer = MediaPlayer.create(SongActivity.this,uri);

                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.stop);
                SetTime();
                UpdateTime();
            }
        });


        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    btnPlay.setImageResource(R.drawable.play);
                    mediaPlayer.pause();

                } else{
                    btnPlay.setImageResource(R.drawable.stop);
                    mediaPlayer.start();

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
//        không khởi tạo mới arraySong nữa vì trên có rồi
        arraySong.add(new Song("Quốc Ca", R.raw.quocca));
        arraySong.add(new Song("Bạc Phận", R.raw.bacphan));
        arraySong.add(new Song("Một bước yêu vạn dặm đau", R.raw.mbyvdd));
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

