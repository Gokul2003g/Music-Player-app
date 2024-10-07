package com.example.ssgmusicplayer_new;

import static com.example.ssgmusicplayer_new.MainActivity.musicFiles;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    TextView song_name, artist_name, duration_played, duration_total;
    ImageView cover_art, nextBtn, prevBtn, backBtn, shuffleBtn, repeatBtn;
    FloatingActionButton playPauseBtn;
    SeekBar seekBar;
    int position = -1;
    static Uri uri;
    static MediaPlayer mediaplayer;

    static ArrayList<MusicFile> listSongs = new ArrayList<>();
    private Thread playThread, prevThread, nextThread;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initViews();
        getIntentMethod();
        song_name.setText(listSongs.get(position).getTitle());
        artist_name.setText(listSongs.get(position).getArtist());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaplayer != null  && fromUser){
                    mediaplayer.seekTo(progress*1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaplayer != null){
                  int mCurrentPostion = mediaplayer.getCurrentPosition() / 1000;
                  seekBar.setProgress(mCurrentPostion);
                  duration_played.setText((formattedTime(mCurrentPostion)));
                }
                handler.postDelayed(this, 1000);
            }

        });
    }

    @Override
    protected void onResume() {
        playThreadBtn();
        prevThreadBtn();
        nextThreadBtn();
        super.onResume();
    }

    private void nextThreadBtn() {
        nextThread = new Thread(){
            @Override
            public void run() {
                super.run();
                nextBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nextBtnClicked();
                    }
                });
            }
        };
        nextThread.start();
    }

    private void nextBtnClicked() {
        if(mediaplayer.isPlaying()){
            mediaplayer.stop();
            mediaplayer.release();
            position = ((position+1)%listSongs.size());
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaplayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist_name.setText(listSongs.get(position).getArtist());
            seekBar.setMax(mediaplayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaplayer != null){
                        int mCurrentPostion = mediaplayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPostion);
                    }
                    handler.postDelayed(this, 1000);
                }

            });
            playPauseBtn.setImageResource(R.drawable.baseline_pause);
            mediaplayer.start();

        }
        else{
            mediaplayer.stop();
            mediaplayer.release();
            position = ((position+1)%listSongs.size());
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaplayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist_name.setText(listSongs.get(position).getArtist());
            seekBar.setMax(mediaplayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaplayer != null){
                        int mCurrentPostion = mediaplayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPostion);
                    }
                    handler.postDelayed(this, 1000);
                }

            });
            playPauseBtn.setImageResource(R.drawable.baseline_play_arrow);
        }
    }

    private void prevThreadBtn() {
        prevThread = new Thread(){
            @Override
            public void run() {
                super.run();
                prevBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prevBtnClicked();
                    }
                });
            }
        };
        prevThread.start();
    }

    private void prevBtnClicked() {
        if(mediaplayer.isPlaying()){
            mediaplayer.stop();
            mediaplayer.release();
            position = ((position-1) < 0 ? (listSongs.size()-1) : (position - 1));
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaplayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist_name.setText(listSongs.get(position).getArtist());
            seekBar.setMax(mediaplayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaplayer != null){
                        int mCurrentPostion = mediaplayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPostion);
                    }
                    handler.postDelayed(this, 1000);
                }

            });
            playPauseBtn.setImageResource(R.drawable.baseline_pause);
            mediaplayer.start();

        }
        else{
            mediaplayer.stop();
            mediaplayer.release();
            position = ((position-1) < 0 ? (listSongs.size()-1) : (position - 1));
            uri = Uri.parse(listSongs.get(position).getPath());
            mediaplayer = MediaPlayer.create(getApplicationContext(), uri);
            metaData(uri);
            song_name.setText(listSongs.get(position).getTitle());
            artist_name.setText(listSongs.get(position).getArtist());
            seekBar.setMax(mediaplayer.getDuration()/1000);
            PlayerActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaplayer != null){
                        int mCurrentPostion = mediaplayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPostion);
                    }
                    handler.postDelayed(this, 1000);
                }

            });
            playPauseBtn.setImageResource(R.drawable.baseline_play_arrow);
        }
    }

    @Override
    public boolean isActivityTransitionRunning() {
        return super.isActivityTransitionRunning();
    }

    private void playThreadBtn() {
        playThread = new Thread(){
            @Override
            public void run() {
                super.run();
                playPauseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        playPauseBtnClicked();
                    }
                });
            }
        };
        playThread.start();
    }

    private void playPauseBtnClicked() {
       if(mediaplayer.isPlaying()){
           playPauseBtn.setImageResource((R.drawable.baseline_play_arrow));
           mediaplayer.pause();
           seekBar.setMax(mediaplayer.getDuration()/1000);
           PlayerActivity.this.runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   if(mediaplayer != null){
                       int mCurrentPostion = mediaplayer.getCurrentPosition() / 1000;
                       seekBar.setProgress(mCurrentPostion);
                   }
                   handler.postDelayed(this, 1000);
               }

           });

       }
       else{
           playPauseBtn.setImageResource(R.drawable.baseline_pause);
           mediaplayer.start();
           seekBar.setMax(mediaplayer.getDuration()/1000);
           PlayerActivity.this.runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   if (mediaplayer != null){
                       int mCurrentPostion = mediaplayer.getCurrentPosition() / 1000;
                       seekBar.setProgress(mCurrentPostion);
                   }
                   handler.postDelayed(this, 1000);
               }

           });

       }
    }


    private String formattedTime(int mCurrentPostion) {
        String totalout = "";
        String totalnew = "";
        String seconds = String.valueOf(mCurrentPostion % 60);
        String minutes = String.valueOf(mCurrentPostion / 60);
        totalout = minutes+":"+seconds;
        totalnew = minutes+":"+"0"+seconds;

        if(seconds.length() == 1){
            return totalnew;
        }
        else{
            return totalout;
        }
    }

    private void getIntentMethod() {
        position = getIntent().getIntExtra("position",-1);
        listSongs = musicFiles;
        if(listSongs != null){
            playPauseBtn.setImageResource(R.drawable.baseline_pause);
            uri = Uri.parse(listSongs.get(position).getPath());

        }
        if(mediaplayer != null){
            mediaplayer.stop();
            mediaplayer.release();
            mediaplayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaplayer.start();
        }
        else{
            mediaplayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaplayer.start();

        }
        seekBar.setMax(mediaplayer.getDuration() /1000);
        metaData(uri);



    }

    private void initViews(){
        song_name = findViewById(R.id.song_name);
        artist_name = findViewById(R.id.song_artist);
        duration_played = findViewById(R.id.durationplayed);
        duration_total = findViewById(R.id.durationtotal);
        cover_art = findViewById(R.id.cover_art);
        nextBtn = findViewById(R.id.next);
        prevBtn = findViewById(R.id.skip_prev);
        backBtn = findViewById(R.id.back_btn);
        shuffleBtn = findViewById(R.id.shuffle);
        repeatBtn = findViewById(R.id.repeat);
        playPauseBtn = findViewById(R.id.play_pause);
        seekBar = findViewById(R.id.seekbar);


    }

    private void metaData(Uri uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int durationTotal = Integer.parseInt(listSongs.get(position).getDuration())/1000;
        duration_total.setText(formattedTime(durationTotal));
        byte[] art = retriever.getEmbeddedPicture();
        if(art != null){
            Glide.with(this)
                    .asBitmap()
                    .load(art)
                    .into(cover_art);
        }
        else{
            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.download)
                    .into(cover_art);
        }



    }
}