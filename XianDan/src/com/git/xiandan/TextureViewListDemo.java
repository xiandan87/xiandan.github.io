package com.git.xiandan;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TextureViewListDemo extends Activity implements OnPreparedListener {
    private final String TAG="pop";
    private boolean Debug=true;
    private ListView videoList;
    
    MediaPlayer mediaPlayer;
    int position;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setContentView(R.layout.textureview_list_layout);
        videoList=(ListView) this.findViewById(R.id.textureview_list);
        videoList.setAdapter(new VideoAdapter());
        
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC); 
        mediaPlayer.setOnPreparedListener(this);
    }
    
    String[] titles={"tilte_1","tilte_2","tilte_3","tilte_4","tilte_5",
            "tilte_6","tilte_7","tilte_8","tilte_9","tilte_10"};
    
    
    class VideoAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder=null;
            if(convertView==null){
                LayoutInflater inflater=LayoutInflater.from(TextureViewListDemo.this);
                convertView=inflater.inflate(R.layout.textureview_list_item, null);
                holder=new Holder();
                holder.video=(TextureView) convertView.findViewById(R.id.video_content);
                holder.title=(TextView) convertView.findViewById(R.id.video_title);
                convertView.setTag(holder);
            }else{
                holder=(Holder) convertView.getTag();
            }
            
            holder.title.setText(titles[position]);
            holder.video.setTag(position);
            holder.video.setSurfaceTextureListener(new TextureListener(holder.video));
            
            return convertView;
        }
        
        
        class Holder{
            TextureView video;
            TextView title;
        }
        
    }
    
    class TextureListener implements SurfaceTextureListener{
        private TextureView view;
        
        public TextureListener(TextureView view) {
            this.view=view;
        }

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            if(view!=null){
                log("available:"+view.getTag());
            }
            int index=(Integer) view.getTag();
            if(index==0){
                //开始播放
                play(surface);
                
                if (position>0) {
                    try {
                        //并直接从指定位置开始播放
                        mediaPlayer.seekTo(position-1000);
                        position=0;                     
                    } catch (Exception e) {
                    }
                }
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            
        }
        
    }
    
    private void play(SurfaceTexture surfaceTexture) {
        try {
            mediaPlayer.reset();
            mediaPlayer
            .setAudioStreamType(AudioManager.STREAM_MUSIC);
            //设置需要播放的视频
            mediaPlayer.setDataSource("/mnt/sdcard/test_baofeng.mp4");
//          mediaPlayer.setDataSource("http://daily3gp.com/vids/family_guy_penis_car.3gp");
            //把视频画面输出到SurfaceView
            Surface surface=new Surface(surfaceTexture);
            mediaPlayer.setSurface(surface);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepare();
            //播放
//          mediaPlayer.start();        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    private void log(String value){
        if(this.Debug){
            android.util.Log.i("pop", value);
        }
    }


    @Override
    public void onPrepared(MediaPlayer mp) {
        int videoWidth = mediaPlayer.getVideoWidth();  
        int videoHeight = mediaPlayer.getVideoHeight();  
        if (videoHeight != 0 && videoWidth != 0) {  
            mp.start();  
        }  
    }
    
}
