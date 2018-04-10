package com.askviky.communityservice.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.askviky.common.activity.AppBaseActivity;
import com.askviky.communityservice.R;
import com.askviky.communityservice.adapter.ListViewXAdapter;
import com.askviky.communityservice.bean.ChatMsgEntity;
import com.askviky.communityservice.db.sqlite.DBOpenHelper;
import com.askviky.communityservice.db.sqlite.DBService;

public class ChatActivity extends AppBaseActivity {

	private List<ChatMsgEntity> mData;
	private DBOpenHelper mDBHelper;
	
	private ListView mListView;
	private EditText mEditText;
	private Button mButton;
	private ImageButton mImgButton;
	
	private ListViewXAdapter mAdapter;
	private DBService mDBService;
	private String mConversationTitle;
	
	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		setContentView(R.layout.activity_chat);

		mButton = (Button) findViewById(R.id.send_btn);
		mListView = (ListView) findViewById(R.id.list_view);
		mImgButton = (ImageButton) findViewById(R.id.back_btn);
		mImgButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		mEditText = (EditText) findViewById(R.id.body_edit);
		mEditText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		});
		
		mButton.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendReply();
			}
		});
	}

	@Override
	protected void loadData() {
		mDBService = new DBService(this);
		mDBHelper = new DBOpenHelper(this);
		
		mConversationTitle = getIntent().getStringExtra("title");
		mData = getDetailListData(mConversationTitle);
		mAdapter = new ListViewXAdapter(this, mData);
		mListView.setAdapter(mAdapter);
	}
	
	private void sendReply() {
		String replyMsg = mEditText.getText().toString();
		if (replyMsg.length() > 0 && mData.size() > 0) {
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setTime(getDate());
			entity.setTitle(mData.get(0).getTitle());
			entity.setType(0);
			entity.setMsg(replyMsg);
			mData.add(entity);
			mDBService.insertEntity(entity);

			mData = getDetailListData(mConversationTitle);
			mAdapter.notifyDataSetChanged();
			mEditText.setText("");
			mListView.setSelection(mListView.getCount() - 1);
		}
	}
	
    private String getDate() {
        Calendar calendar = Calendar.getInstance();

        String year = String.valueOf(calendar.get(Calendar.YEAR));
        String month = String.valueOf(calendar.get(Calendar.MONTH));
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) + 1);
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(calendar.get(Calendar.MINUTE));
       
        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":" + mins); 
        return sbBuffer.toString();
    }

	private List<ChatMsgEntity> getDetailListData(String title) {
		List<ChatMsgEntity> list = new ArrayList<ChatMsgEntity>();
		try {
			SQLiteDatabase db =	mDBHelper.getWritableDatabase();
			String sql = "SELECT id, title, msg, time, type FROM msglist WHERE title = ?";
			Cursor cursor = db.rawQuery(sql, new String[]{ title });
			while (cursor.moveToNext()) {
				ChatMsgEntity entity = new ChatMsgEntity();
				entity.setTitle(cursor.getString(cursor.getColumnIndex("title")));
				entity.setMsg(cursor.getString(cursor.getColumnIndex("msg")));
				entity.setTime(cursor.getString(cursor.getColumnIndex("time")));
				entity.setType(cursor.getInt(cursor.getColumnIndex("type")));
				list.add(entity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ChatMsgEntity entity = new ChatMsgEntity();
		entity.setTitle("Title");
		entity.setMsg("您好，很高兴为您服务");
		entity.setTime("2016.01.17-20:16");
		entity.setType(1);
		list.add(entity);		
		return list;
	}

}
