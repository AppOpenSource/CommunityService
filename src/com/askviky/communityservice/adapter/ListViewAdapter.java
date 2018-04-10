package com.askviky.communityservice.adapter;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.askviky.communityservice.R;

public class ListViewAdapter extends BaseAdapter {

	private Context mActivity;
	private List<Map<String, Object>> mListData;
	private LayoutInflater mInflater;

	public ListViewAdapter (Activity activity, List<Map<String, Object>> listData) {
		this.mActivity = activity;
		this.mListData = listData;
		this.mInflater = LayoutInflater.from(activity);
	}
	public int getCount() {
		return mListData == null ? 0 : mListData.size();
	}

	@Override
	public Object getItem(int position) {
		return mListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Map<String,Object> map = mListData.get(position);

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.listitem_server, null);
			holder.img = (ImageView) convertView.findViewById(R.id.serve_img);
			holder.nameText = (TextView) convertView.findViewById(R.id.name_text);
			holder.addressText = (TextView) convertView.findViewById(R.id.address_text);
			holder.telephoneText = (TextView) convertView.findViewById(R.id.telephone_text);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.img.setBackgroundResource((Integer) map.get("imgId"));
		holder.nameText.setText((String)map.get("name"));
		holder.addressText.setText((String)map.get("address"));
		holder.telephoneText.setText((String)map.get("telephone"));

		return convertView;
	}

	public final class ViewHolder {
		public ImageView img;
		public TextView nameText;
		public TextView addressText;
		public TextView telephoneText;
	}
}
