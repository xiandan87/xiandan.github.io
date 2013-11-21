/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xiaoya.framepager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SuperAwesomeCardFragment extends Fragment {

	private static final String ARG_POSITION = "position";

	private int position;
	public static int index;
	private ArrayList<String> list = new ArrayList<String>();
	public List<String> urls;
	public static 	HashMap<String, Bitmap> imagesCache = new HashMap<String, Bitmap>(); // 图片缓存
	static LinearLayout linear_point;
	AdImageGallery images_ga;
	/**
	 * 获取自动 播放的信息，调handler，通知gallery自动播放
	 */
	public ViewpagerTask timeTask = null;
	private ScheduledExecutorService scheduledExecutorService;
	
	
	public static SuperAwesomeCardFragment newInstance(int position) {
	    Log.i("pop", position+"");
		SuperAwesomeCardFragment f = new SuperAwesomeCardFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		position = getArguments().getInt(ARG_POSITION);
		System.out.println("onCreate=====>>>>"+position);
		getData();
		

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println(position + "======>>>>>");
		View v=inflater.inflate(R.layout.fragment_setting, container, false);
		
		FrameLayout fram_gallery=(FrameLayout)v.findViewById(R.id.frame_gallery);
		fram_gallery.setVisibility(View.GONE);
		ListView lv_news=(ListView)v.findViewById(R.id.lv_news);
		
		System.out.println("position  ====>>>>>>"+position);
		if(position==0){
			fram_gallery.setVisibility(View.VISIBLE);
			 images_ga=(AdImageGallery)v.findViewById(R.id.image_wall_gallery);
			 images_ga.setImageActivity(this);
			Bitmap image = BitmapFactory.decodeResource(getResources(),
					R.drawable.gallary_moren);
			imagesCache.put("background_non_load", image); // 设置缓存中默认的图片
			linear_point=( LinearLayout)v.findViewById(R.id.gallery_point_linear);
			
			urls = new ArrayList<String>(); // 图片地址List
			urls.add("http://f11.topit.me/l/201009/16/12845672546160.jpg");
			urls.add("http://f12.topit.me/l166/101665601956b260eb.jpg");
			urls.add("http://i6.topit.me/6/15/50/1147943393d6950156l.jpg");
			urls.add("http://f6.topit.me/6/08/22/113361531058822086l.jpg");
			urls.add("http://i9.topit.me/9/23/10/1147966176c1910239l.jpg");
			
			List<String> imageList = new ArrayList<String>();
			imageList.add("青玉案");
			imageList.add("众里寻他千百度");
			imageList.add("蓦然回首");
			imageList.add("那人却在");
			imageList.add("灯火阑珊处");
			
			ImageAdapter imageAdapter = new ImageAdapter(getActivity(), urls, imageList	);
			images_ga.setAdapter(imageAdapter);
			images_ga.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int positioin, long arg3) {
					// TODO Auto-generated method stub
					Toast.makeText(getActivity(),
							"您当中选中的索引:" + positioin + ":页面正在加载中....",
							Toast.LENGTH_SHORT).show();
				}
			});
			
//			for (int i = 0; i < urls.size(); i++) {
//				TextView textView=new TextView(getActivity());
//				textView.setText(imageList.get(i%urls.size()));
//				linear_point.addView(textView);
//			}
			for (int i = 0; i < urls.size(); i++) {
				ImageView pointView = new ImageView(getActivity());
				if (i == 0) {
					pointView.setBackgroundResource(R.drawable.feature_point_cur);
				} else
					pointView.setBackgroundResource(R.drawable.feature_point);
				
				linear_point.addView(pointView);
				
			}
			
		}
		lv_news.setAdapter(new MyAdapter_news(getActivity(), list));
		return v;
	}

	/**
	 * 选中图片中的点
	 * 
	 * @param cur
	 */
	public static void changePointView(int cur) {
		//LinearLayout pointLinear = (LinearLayout) findViewById(R.id.gallery_point_linear);
		View view = linear_point.getChildAt(index);
		View curView = linear_point.getChildAt(cur);
		if (view != null && curView != null) {
			ImageView pointView = (ImageView) view;
			ImageView curPointView = (ImageView) curView;
			pointView.setBackgroundResource(R.drawable.feature_point);
			curPointView.setBackgroundResource(R.drawable.feature_point_cur);
			index = cur;
		}
	}
	// 创建自动滚动广告线程
		public class ViewpagerTask implements Runnable {
			public volatile boolean timeCondition = true;

			@Override
			public void run() {
				if (!timeCondition) {
					return;
				}
				try {

					int gallerypisition = images_ga.getSelectedItemPosition() + 1;
					Message msg = new Message();
					Bundle date = new Bundle();
					date.putInt("pos", gallerypisition);
					msg.setData(date);
					msg.what = 1;
					autoGalleryHandler.sendMessage(msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		final Handler autoGalleryHandler = new Handler() {
			public void handleMessage(Message message) {
				super.handleMessage(message);
				switch (message.what) {
				case 1:
					images_ga.setSelection(message.getData().getInt("pos"));
					break;
				}
			}
		};

		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			Set<String> keyset = imagesCache.keySet();
			for (String value : keyset) {
				Bitmap temp = imagesCache.get(value);
				if (null != temp && !temp.isRecycled()) {
					temp.recycle();
				}
			}
			Iterator<String> it = keyset.iterator();
			while (it.hasNext()) {
				String s = it.next();
				it.remove();
				imagesCache.remove(s);
			}
			imagesCache.clear();
			if (!scheduledExecutorService.isShutdown()) {
				scheduledExecutorService.shutdown();
			}
		}

		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			super.onStart();
			if(position==0){
			    timeTask = new ViewpagerTask();
	            scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
	            // 首次启动时10秒后开始执行，接下来5秒执行一次
	            scheduledExecutorService.scheduleAtFixedRate(timeTask, 5, 3,
	                    TimeUnit.SECONDS);
			}
		}

		@Override
		public void onStop() {
			super.onStop();
			if(position==0){
			    scheduledExecutorService.shutdown();// 停止线程任务
			}
		}
	/**
	 * 填充列表数据；
	 */
	public void getData() {
		for (int i = 0; i < 20; i++) {
			list.add("  xinwenwenwen   xinwenwenwen xinwenwenwen" + i);
		}
	}
}