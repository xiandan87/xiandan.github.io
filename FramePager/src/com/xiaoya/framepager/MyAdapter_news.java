package com.xiaoya.framepager;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter_news extends BaseAdapter {
	List<String> mylist;
	Context context;
	public MyAdapter_news(Context cxt,List<String> list) {
		this.mylist = list;
		this.context=cxt;
	}
	private class ViewHolder {
		ImageView iv_icon;
		TextView tv_title;
		TextView tv_date;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mylist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mylist.size();
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.lv_item_news, null);
			viewHolder.iv_icon = (ImageView) convertView
					.findViewById(R.id.news_icon);
			viewHolder.tv_title = (TextView) convertView
					.findViewById(R.id.news_title);
			viewHolder.tv_date = (TextView) convertView
					.findViewById(R.id.news_date);
			convertView.setTag(viewHolder);

		} else {

			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tv_title.setText(mylist.get(position));
		return convertView;
	}

}
