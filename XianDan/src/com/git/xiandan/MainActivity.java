package com.git.xiandan;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends Activity  implements OnClickListener,OnPreparedListener{

	ImageButton btnplay, btnstop, btnpause;
	SurfaceView surfaceView;
	MediaPlayer mediaPlayer;
	int position;
	
	 private int videoWidth;  
	 private int videoHeight;  

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);	
		btnplay=(ImageButton)this.findViewById(R.id.btnplay);
		btnstop=(ImageButton)this.findViewById(R.id.btnstop);
		btnpause=(ImageButton)this.findViewById(R.id.btnpause);
		
		btnstop.setOnClickListener(this);
		btnplay.setOnClickListener(this);
		btnpause.setOnClickListener(this);
		
		mediaPlayer=new MediaPlayer();
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC); 
		mediaPlayer.setOnPreparedListener(this);
		surfaceView=(SurfaceView) this.findViewById(R.id.surfaceView);
	
		//设置SurfaceView自己不管理的缓冲区
		surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);		
		surfaceView.getHolder().addCallback(new Callback() {		
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
		
			}
		
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
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
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {

			}
		});		
	}
	@Override
	public void onClick(View v) {	
		switch (v.getId()) {
		case R.id.btnplay:
			play();
			break;
			
		case R.id.btnpause:
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.pause();
			}else{
				mediaPlayer.start();
			}
			break;

		case R.id.btnstop:
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
			}
			
			break;
		default:
			break;
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
	protected void onPause() {	
		//先判断是否正在播放
		if (mediaPlayer.isPlaying()) {
			//如果正在播放我们就先保存这个播放位置
			position=mediaPlayer.getCurrentPosition()
					;
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
//			mediaPlayer.setDataSource("http://daily3gp.com/vids/family_guy_penis_car.3gp");
			//把视频画面输出到SurfaceView
			mediaPlayer.setDisplay(surfaceView.getHolder());
			mediaPlayer.setOnPreparedListener(this);
			mediaPlayer.prepare();
			//播放
//			mediaPlayer.start();		
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
}
