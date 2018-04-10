package com.askviky.communityservice.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.askviky.common.view.CSListView;
import com.askviky.communityservice.R;
import com.askviky.communityservice.bean.ChatMsgContent;
import com.askviky.communityservice.bean.ChatMsgEntity;
import com.askviky.communityservice.db.sqlite.DBOpenHelper;

public class ListPageActivity extends ListActivity {

	private List<ChatMsgEntity> mData;
	private List<ChatMsgContent> mListData;
	private DBOpenHelper mDBHelper;
	private Context mContext;
	private Dialog mProgressDialog;
	private CSListView mServeList;
	private MsgListAdapter mServeListAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_page);

		mDBHelper = new DBOpenHelper(this);
		mData = getMessageListData();
		setListAdapter(new MsgListAdapter(this));
	}

	private List<ChatMsgEntity> getMessageListData() {
		List<ChatMsgEntity> list = new ArrayList<ChatMsgEntity>();

		try {
			// String sql =
			// "SELECT id, title, msg, time, type FROM msglist WHERE title = ?";
			// Cursor cursor = db.rawQuery(sql, new String[]{ title });

			SQLiteDatabase db = mDBHelper.getWritableDatabase();
			// String sqlnow =
			// "SELECT id, title, msg, time, type FROM(SELECT *, row_number() over(partition by title order by id desc) as RowNo from msglist) as t where RowNo=1";
			Cursor cursor = db.query("msglist", new String[] {}, null, null,
					null, null, null);

			while (cursor.moveToNext()) {
				ChatMsgEntity entity = new ChatMsgEntity();
				entity.setTitle(cursor.getString(cursor.getColumnIndex("title")));
				entity.setMsg(cursor.getString(cursor.getColumnIndex("msg")));
				entity.setTime(cursor.getString(cursor.getColumnIndex("time")));
				entity.setImg(R.drawable.server_img);
				list.add(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Intent intent = new Intent(this, ListViewActivity.class);
		ChatMsgEntity entity = (ChatMsgEntity) mData.get(position);
		intent.putExtra("title", entity.getTitle());
		startActivity(intent);
	}

	public final class ViewHolder {
		public ImageView img;
		public TextView title;
		public TextView msg;
		public TextView time;
	}

	public class MsgListAdapter extends BaseAdapter {
		private LayoutInflater mInflater;
		private ViewHolder holder = null;

		public MsgListAdapter(Context context) {
			this.mInflater = LayoutInflater.from(context);
		}

		public MsgListAdapter(Activity activity, List<ChatMsgContent> listData) {
			mContext = activity;
			mListData = listData;
			this.mInflater = LayoutInflater.from(activity);
		}

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int arg0) {
			return mData.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.listview_msg, null);
				holder = new ViewHolder();
				holder.img = (ImageView) convertView.findViewById(R.id.img);
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.msg = (TextView) convertView.findViewById(R.id.msg);
				holder.time = (TextView) convertView.findViewById(R.id.time);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.img.setBackgroundResource((Integer) mData.get(position).getImg());
			holder.title.setText((String) mData.get(position).getTitle());
			holder.msg.setText((String) mData.get(position).getMsg());
			holder.time.setText((String) mData.get(position).getTime());

			return convertView;
		}
	}

	public void initListView() {
		mServeList = (CSListView) findViewById(R.id.serve_list);
		mServeListAdapter = new MsgListAdapter(ListPageActivity.this, mListData);
		mServeList.setAdapter(mServeListAdapter);
		mServeList.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(ListPageActivity.this, ListViewActivity.class);
				startActivity(intent);
			}
		});
	}
}
