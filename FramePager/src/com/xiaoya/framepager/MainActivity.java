package com.xiaoya.framepager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
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
