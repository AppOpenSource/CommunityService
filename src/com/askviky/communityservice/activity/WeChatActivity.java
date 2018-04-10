package com.askviky.communityservice.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.askviky.common.activity.AppBaseActivity;
import com.askviky.communityservice.R;
import com.askviky.communityservice.adapter.WeChatListAdapter;
import com.askviky.communityservice.bean.WeChatMsgEntity;

public class WeChatActivity extends AppBaseActivity implements OnClickListener {

	private Button mBtnSend;
	private Button mBtnBack;
	private EditText mEditTextContent;
	private ListView mListView;
	private WeChatListAdapter mAdapter;
	private List<WeChatMsgEntity> mDataArrays = new ArrayList<WeChatMsgEntity>();
	
	private final static int COUNT = 8;
	
	private String[] mMsgArray = new String[] { "有大吗", "有！你呢？", "我也有", "那上吧", 
			"打啊！你放大啊", "你tm咋不放大呢？留大抢人头那！Cao的。你个菜b", "2B不解释", "尼滚...." };
	
	private String[] mDataArray = new String[] { "2012-09-01 18:00", "2012-09-01 18:10", 
			"2012-09-01 18:11", "2012-09-01 18:20", "2012-09-01 18:30", "2012-09-01 18:35",
			"2012-09-01 18:40", "2012-09-01 18:50" };

	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.btn_send:
			send();
			break;
		case R.id.btn_back:
			finish();
			break;
		}
	}

	private void send() {
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0) {
			WeChatMsgEntity entity = new WeChatMsgEntity();
			entity.setDate(getDate());
			entity.setName("人马");
			entity.setMsgType(false);
			entity.setText(contString);

			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			mEditTextContent.setText("");
			mListView.setSelection(mListView.getCount() - 1);
		}
	}

	private String getDate() {
		Calendar c = Calendar.getInstance();

		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH));
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));

		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":" + mins); 

		return sbBuffer.toString();
	}

	@Override
	protected void initVariables() {
		for (int i = 0; i < COUNT; i++) {
			WeChatMsgEntity entity = new WeChatMsgEntity();
			entity.setDate(mDataArray[i]);
			if (i % 2 == 0) {
				entity.setName("小黑");
				entity.setMsgType(true);
			} else {
				entity.setName("人马");
				entity.setMsgType(false);
			}
			entity.setText(mMsgArray[i]);
			mDataArrays.add(entity);
		}
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		setContentView(R.layout.activity_wechat);

		mListView = (ListView) findViewById(R.id.listview);
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		mBtnBack = (Button) findViewById(R.id.btn_back);
		mBtnBack.setOnClickListener(this);
		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
		
		mAdapter = new WeChatListAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
	}

	@Override
	protected void loadData() {

	}
}
