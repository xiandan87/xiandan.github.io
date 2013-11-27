package com.xiaoya.framepager;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

public class ImageAdapter extends BaseAdapter {
	private List<String> imageUrls; // 图片地址list
	private List<String> imageInfo;
	private Context context;
	private int screenWidth;
	private int screenHeigth;

	public ImageAdapter(Context context, List<String> imageUrls
			,List<String> imageInfo
			) {
		this.imageUrls = imageUrls;
		this.imageInfo = imageInfo;
		this.context = context;
		DisplayMetrics dm = new DisplayMetrics();
		dm = context.getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeigth = screenWidth / 2;
	}

	public int getCount() {
		return Integer.MAX_VALUE;
	}

	public Object getItem(int position) {
		return imageUrls.get(position % imageUrls.size());
	}

	public long getItemId(int position) {
		return position;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				switch (msg.what) {
				case 0: {
					ImageAdapter.this.notifyDataSetChanged();
				}
					break;
				}

				super.handleMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	public View getView(int position, View convertView, ViewGroup parent) {

		Bitmap image;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.imagegallery_item,
					null); // 实例化convertView
			Gallery.LayoutParams params = new Gallery.LayoutParams(
					Gallery.LayoutParams.FILL_PARENT,
					Gallery.LayoutParams.FILL_PARENT);
			convertView.setLayoutParams(params);
			image = SuperAwesomeCardFragment.imagesCache.get(imageUrls
					.get(position % imageUrls.size())); // 从缓存中读取图片
			if (image == null) {
				// 当缓存中没有要使用的图片时，先显示默认的图片
				image = SuperAwesomeCardFragment .imagesCache
						.get("background_non_load");
				// 异步加载图片
				LoadImageTask task = new LoadImageTask(convertView);
				task.execute(imageUrls.get(position % imageUrls.size()));
			}
			convertView.setTag(image);

		} else {
			image = (Bitmap) convertView.getTag();
		}
		TextView textView = (TextView) convertView
				.findViewById(R.id.gallery_text);
		//textView.setBackgroundColor(Color.WHITE);
		if (null == imageInfo || imageInfo.size() == 0) {
			textView.setText("");
			
		} else {
			if (imageInfo.size() < imageUrls.size()) {
				if (imageInfo.get(position % imageInfo.size()) != null) {
					textView.setText(imageInfo.get(position % imageInfo.size()));
				}
			} else {
				textView.setText(imageInfo.get(position % imageInfo.size()));
			}
		}
		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.gallery_image);
		imageView.setImageBitmap(image);
		LayoutParams para = imageView.getLayoutParams();
		para.width = screenWidth;
		para.height = screenHeigth;
		imageView.setLayoutParams(para);

		// 设置缩放比例：保持原样
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		SuperAwesomeCardFragment.changePointView(position % imageUrls.size());
		return convertView;
	}

	// 加载图片的异步任务
	class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
		private View resultView;

		LoadImageTask(View resultView) {
			this.resultView = resultView;
		}

		// doInBackground完成后才会被调用
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			// 调用setTag保存图片以便于自动更新图片
			resultView.setTag(bitmap);
		}

		// 从网上下载图片
		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap image = null;
			try {
				// new URL对象 把网址传入
				URL url = new URL(params[0]);
				// 取得链接
				URLConnection conn = url.openConnection();
				conn.connect();
				// 取得返回的InputStream
				InputStream is = conn.getInputStream();
				// 将InputStream变为Bitmap
				image = BitmapFactory.decodeStream(is);
				is.close();
				SuperAwesomeCardFragment.imagesCache.put(params[0], image); // 把下载好的图片保存到缓存中
				Message m = new Message();
				m.what = 0;
				mHandler.sendMessage(m);
			} catch (Exception e) {
				e.printStackTrace();
			}

			return image;
		}
	}
}
