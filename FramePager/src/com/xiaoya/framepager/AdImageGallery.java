package com.xiaoya.framepager;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class AdImageGallery extends Gallery {

	//private ImageActivity m_iact;
	private SuperAwesomeCardFragment superAwesomeCardFragment;
	public AdImageGallery(Context context) {
		super(context);
		
		// TODO Auto-generated constructor stub
	}
	
	public AdImageGallery(Context context,AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public AdImageGallery(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

//	public void setImageActivity(ImageActivity iact){
//		m_iact = iact;
//	}
	public void setImageActivity( SuperAwesomeCardFragment fragment){
		superAwesomeCardFragment = fragment;
	}
	
	private boolean isFling = false;
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if(isFling){
			countDown.cancel();
			isFling = false;
		}
		countDown = new LooperCountDown(5000, 1000);
		countDown.start();
		isFling = true;
		int kEvent;
        if(isScrollingLeft(e1, e2)){ //左滑
          kEvent = KeyEvent.KEYCODE_DPAD_LEFT;
        }
        else{ //右滑
          kEvent = KeyEvent.KEYCODE_DPAD_RIGHT;
        }
        onKeyDown(kEvent, null); 
        if(this.getSelectedItemPosition()==0)
        	this.setSelection(superAwesomeCardFragment.urls.size());
        return true;  
	}
	private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2){
        return e2.getX() > e1.getX();
    }
	
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		superAwesomeCardFragment.timeTask.timeCondition = false;
		return super.onScroll(e1, e2, distanceX, distanceY);
	}
	
	private CountDownTimer countDown;
	class LooperCountDown extends CountDownTimer{

		public LooperCountDown(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			superAwesomeCardFragment.timeTask.timeCondition = true;
			isFling = false;
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
		}
	}

}
