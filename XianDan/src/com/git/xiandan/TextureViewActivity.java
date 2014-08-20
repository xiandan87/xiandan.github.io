package com.git.xiandan;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

@SuppressLint("NewApi")
public class TextureViewActivity extends Activity implements SurfaceTextureListener, OnPreparedListener, OnClickListener{
    private Button playBtn;
    private Button pauseBtn;
    private TextureView mPreview;
    MediaPlayer mediaPlayer;
    int position;
    
    private int videoWidth;  
    private int videoHeight;  
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.textureview_layout);
        playBtn=(Button) this.findViewById(R.id.play_video);
        playBtn.setOnClickListener(this);
        pauseBtn=(Button) this.findViewById(R.id.pause_video);
        pauseBtn.setOnClickListener(this);
        mPreview=(TextureView) this.findViewById(R.id.texture_view);
        mPreview.setSurfaceTextureListener(this);
        
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC); 
        mediaPlayer.setOnPreparedListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        if (position>0) {
            try {
                //开始播放
                play();
                //并直接从指定位置开始播放
                mediaPlayer.seekTo(position-1000);
                position=0;                     
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null&&mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    protected void onPause() {
      //先判断是否正在播放
        if (mediaPlayer.isPlaying()) {
            //如果正在播放我们就先保存这个播放位置
            position=mediaPlayer.getCurrentPosition();
            mediaPlayer.stop();
        }
        super.onPause();
    }
    
    
    private void play() {
        try {
            mediaPlayer.reset();
            mediaPlayer
            .setAudioStreamType(AudioManager.STREAM_MUSIC);
            //设置需要播放的视频
            mediaPlayer.setDataSource("/mnt/sdcard/test_baofeng.mp4");
//          mediaPlayer.setDataSource("http://daily3gp.com/vids/family_guy_penis_car.3gp");
            //把视频画面输出到SurfaceView
            Surface surface=new Surface(mPreview.getSurfaceTexture());
            mediaPlayer.setSurface(surface);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepare();
            //播放
//          mediaPlayer.start();        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onPrepared(MediaPlayer mp) {
        videoWidth = mediaPlayer.getVideoWidth();  
        videoHeight = mediaPlayer.getVideoHeight();  
        if (videoHeight != 0 && videoWidth != 0) {  
            mp.start();  
        }  
    }

    @Override
    public void onClick(View v) {
        if (v == playBtn) {
            play();
        } else if (v == pauseBtn) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
            }else{
                mediaPlayer.start();
            }
        }
    }
}
