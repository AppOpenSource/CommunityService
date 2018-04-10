package com.askviky.communityservice.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.askviky.communityservice.R;
import com.askviky.communityservice.bean.ChatMsgEntity;

public class ListViewXAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private List<ChatMsgEntity> mData;
	
	private final int TYPE_0 = 0;
	private final int TYPE_1 = 1;
	private final int VIEW_TYPE = 2;

	private static final int COME_FROM_LOCAL = 0;
	private static final int COME_FROM_OUTSIDE = 1;
	
	public ListViewXAdapter(Context context, List<ChatMsgEntity> data) {
		this.mInflater = LayoutInflater.from(context);
		this.mData = data;
	}

	@Override
	public int getCount() {
		return mData == null ? 0 : mData.size();
	}
	
	@Override
	public int getItemViewType(int position) {
		ChatMsgEntity entity = (ChatMsgEntity) mData.get(position);
		int isComMsg = entity.getType();
		if (isComMsg == COME_FROM_OUTSIDE) {
			return TYPE_0;
		} else if (isComMsg == COME_FROM_LOCAL) {
			return TYPE_1;
		}
		return -1;
	}
	
	@Override
	public int getViewTypeCount() {
		return VIEW_TYPE;
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder0 holder0 = null;
		ViewHolder1 holder1 = null;
		int type = getItemViewType(position);

		if (convertView == null) {
			switch (type) {
			case TYPE_0:
				convertView = mInflater.inflate(R.layout.listview_msg_detail, null);
				holder0 = new ViewHolder0();
				holder0.img = (ImageView) convertView.findViewById(R.id.img);
				holder0.title = (TextView) convertView.findViewById(R.id.title);
				holder0.msg = (TextView) convertView.findViewById(R.id.msg);
				holder0.time = (TextView) convertView.findViewById(R.id.time);
				convertView.setTag(holder0);
				break;
			case TYPE_1:
				convertView = mInflater.inflate(R.layout.listview_msg_reply, null);
				holder1 = new ViewHolder1();
				holder1.img = (ImageView) convertView.findViewById(R.id.img);
				holder1.title = (TextView) convertView.findViewById(R.id.title);
				holder1.msg = (TextView) convertView.findViewById(R.id.msg);
				holder1.time = (TextView) convertView.findViewById(R.id.time);
				convertView.setTag(holder1);
				break;
			}
		} else {
			switch (type) {
			case TYPE_0:
				holder0 = (ViewHolder0) convertView.getTag();
				break;
			case TYPE_1:
				holder1 = (ViewHolder1) convertView.getTag();
				break;
			}
		}
		
		ChatMsgEntity entity = (ChatMsgEntity) mData.get(position);
		switch (type) {
		case TYPE_0:
			holder0.img.setBackgroundResource((Integer) R.drawable.server_img);
			holder0.title.setText((String) entity.getTitle());
			holder0.msg.setText((String) entity.getMsg());
			holder0.time.setText((String) entity.getTime());
			break;
		case TYPE_1:
			holder1.img.setBackgroundResource((Integer) R.drawable.server_img);
			holder1.title.setText((String) entity.getTitle());
			holder1.msg.setText((String) entity.getMsg());
			holder1.time.setText((String) entity.getTime());
			break;
		}
		return convertView;
	}
	
	public final class ViewHolder0 {
		public ImageView img;
		public TextView title;
		public TextView msg;
		public TextView time;
	}

	public final class ViewHolder1 {
		public ImageView img;
		public TextView title;
		public TextView msg;
		public TextView time;
	}
}
