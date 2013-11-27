package com.xiaoya.framepager;

import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends FragmentActivity  {
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;
	// private Button btn_back;
	private TextView tv_bigTxt;
	//private int currentSelectedFragmentPosition = 0; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tv_bigTxt = (TextView) findViewById(R.id.txt);
		tv_bigTxt.setText("测试的新闻");

		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		pager = (ViewPager) findViewById(R.id.pager);
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		
		
		final int pageMargin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
						.getDisplayMetrics());
		pager.setPageMargin(pageMargin);

		tabs.setViewPager(pager);
		
		/*try {
            Runtime.getRuntime().exec(new String[] { "su", "-c", "reboot now" });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
		
		Intent i = new Intent(Intent.ACTION_REBOOT);
		i.putExtra("nowait", 1);
		i.putExtra("interval", 1);
		i.putExtra("window", 0);
		sendBroadcast(i);
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {
		private final String[] TITLES = { "全     部", "邦惠资讯", "领导动态", "安邦资讯",
				"公司动态" };

		// , "安邦资讯","公司动态"
		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return TITLES[position];
		}

		@Override
		public int getCount() {
			return TITLES.length;
		}

		@Override
		public Fragment getItem(int position) {
			System.out.println("LLLLLLLLLLLLL=====>>>>" + position);
			return SuperAwesomeCardFragment.newInstance(position);
		}

	}


	
}
