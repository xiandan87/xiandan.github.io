package com.git.xiandan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MenuActivity extends Activity implements OnClickListener{
    private Button demo1;
    private Button demo2;
    private Button demo3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.menu);
        
        demo1=(Button) this.findViewById(R.id.demo_1);
        demo1.setOnClickListener(this);
        demo2=(Button) this.findViewById(R.id.demo_2);
        demo2.setOnClickListener(this);
        demo3=(Button) this.findViewById(R.id.demo_3);
        demo3.setOnClickListener(this);
        
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
        case R.id.demo_1:
            gotoDemo(MainActivity.class);
            break;
        case R.id.demo_2:
            gotoDemo(VideoViewDemo.class);
            break;
        case R.id.demo_3:
            gotoDemo(VideoViewActivity.class);
        }
        
    }
    
    private void gotoDemo(Class clazz){
        Intent intent=new Intent(this,clazz);
        this.startActivity(intent);
    }
}
