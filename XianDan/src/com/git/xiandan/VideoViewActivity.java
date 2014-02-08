package com.git.xiandan;

import java.io.File;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class VideoViewActivity extends Activity {

    private VideoView videoView;
    private Button btnHide, btnShow;
    MediaController mediaController;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.videoview2);

        videoView = (VideoView) this.findViewById(R.id.video);
        btnShow = (Button) this.findViewById(R.id.btnShow);
        btnHide = (Button) this.findViewById(R.id.btnHide);

        // 使用这种方式创建MediaController将不会显示“快进”和“快退”两个按钮
        // mediaController = new MediaController(this,false);

        mediaController = new MediaController(this);

        File videoFile = new File("/mnt/sdcard/Wonders_of_Nature.mp4");

        // 先判断这个文件是否存在
//        if (videoFile.exists()) {
//            System.out.println("文件存在");
//            videoView.setVideoPath(videoFile.getAbsolutePath());
            videoView.setVideoPath("http://daily3gp.com/vids/family_guy_penis_car.3gp");
            System.out.println(videoFile.getAbsolutePath());
            // 设置VideView与MediaController建立关联
            videoView.setMediaController(mediaController);
            // 设置MediaController与VideView建立关联
            mediaController.setMediaPlayer(videoView);

            // 让VideoView获取焦点
            videoView.requestFocus();
            // 开始播放
            videoView.start();
//        } else {
//            Toast.makeText(this, "文件不存在", Toast.LENGTH_LONG).show();
//        }

        btnShow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mediaController != null) {
                    // 使用0的话就一直显示直到调用hide()
                    mediaController.show(0);
                }

            }
        });

        btnHide.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mediaController != null) {
                    
                    mediaController.hide();
                }

            }
        });
    }
}
