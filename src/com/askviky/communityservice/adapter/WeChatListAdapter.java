package com.askviky.communityservice.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.askviky.communityservice.R;
import com.askviky.communityservice.bean.WeChatMsgEntity;

public class WeChatListAdapter extends BaseAdapter {

	private static final String TAG = WeChatListAdapter.class.getSimpleName();
	private List<WeChatMsgEntity> mColl;
	private Context mCtx;
	private LayoutInflater mInflater;

	public static interface IMsgViewType {
		int IMVT_COM_MSG = 0;
		int IMVT_TO_MSG = 1;
	}

	public WeChatListAdapter(Context context, List<WeChatMsgEntity> coll) {
		mCtx = context;
		mColl = coll;
		mInflater = LayoutInflater.from(context);
	}

	public int getCount() {
		return mColl.size();
	}

	public Object getItem(int position) {
		return mColl.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		WeChatMsgEntity entity = mColl.get(position);

		if (entity.getMsgType()) {
			return IMsgViewType.IMVT_COM_MSG;
		} else {
			return IMsgViewType.IMVT_TO_MSG;
		}
	}

	public int getViewTypeCount() {
		return 2;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		WeChatMsgEntity entity = mColl.get(position);
		boolean isComMsg = entity.getMsgType();

		ViewHolder viewHolder = null;	
		if (convertView == null) {
			if (isComMsg) {
				convertView = mInflater.inflate(R.layout.listitem_chatting_msgtext_left, null);
			} else {
				convertView = mInflater.inflate(R.layout.listitem_chatting_msgtext_right, null);
			}
			viewHolder = new ViewHolder();
			viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
			viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
			viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
			viewHolder.isComMsg = isComMsg;
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tvSendTime.setText(entity.getDate());
		viewHolder.tvUserName.setText(entity.getName());
		viewHolder.tvContent.setText(entity.getText());
		return convertView;
	}

	static class ViewHolder { 
		public TextView tvSendTime;
		public TextView tvUserName;
		public TextView tvContent;
		public boolean isComMsg = true;
	}

}
