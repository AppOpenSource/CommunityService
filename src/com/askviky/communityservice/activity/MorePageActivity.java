package com.askviky.communityservice.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.askviky.common.activity.AppBaseActivity;
import com.askviky.common.util.CallUtil;
import com.askviky.communityservice.R;
import com.askviky.communityservice.adapter.MoreListAdapter;
import com.askviky.communityservice.bean.MoreListItem;
import com.askviky.communityservice.service.LoginService;

public class MorePageActivity extends AppBaseActivity implements TagAliasCallback {

    private static final String TAG = MorePageActivity.class.getSimpleName();
	private final int LIST_TYPE_1 = 1; //list1布局标示
	private final int LIST_TYPE_2 = 2; //list2布局标示
	private List<MoreListItem> mData1, mData2;
	private LoginService mLoginManager;

	public void onItemClick(AdapterView<?> parent, View view,int listtype, int position,
			long id) {
		switch (listtype) {
		case LIST_TYPE_1:
			// 拨打电话
			MoreListItem item = mData1.get(position);
			CallUtil.dial(this, item.getPhoneNumber());
			break;
		case LIST_TYPE_2:
			switch (position) {
			case 0:
				startActivity(new Intent(this, AboutActivity.class));
				break;
			case 1:
				Toast.makeText(this, "无更新", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				startActivity(new Intent(this, AboutActivity.class));
				break;
			case 3:
				loginOutDialog();
				break;
			default:
				break;
			}
		break;
		}
	}

	/**
	 * 退出登录对话框(注销登录)
	 */
	private void loginOutDialog() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("提醒");
		dialog.setMessage("是否退出?");
		dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				exit();
				MorePageActivity.this.finish();//关闭activity 
			}
		});
		dialog.setNegativeButton("取消", null);
		dialog.create();
		dialog.show();
	}

	public void exit() {
		if (mLoginManager.getUserId()!=-1) {
			mLoginManager.delete();
		}
		JPushInterface.stopPush(getApplicationContext());
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回键
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			ConfirmExit();//按了返回键，但已经不能返回，则执行退出确认
			return true; 
		}   
		return super.onKeyDown(keyCode, event);   
	}
	
	public void ConfirmExit() { //退出确认
		AlertDialog.Builder ad=new AlertDialog.Builder(MorePageActivity.this);
		ad.setTitle("退出");
		ad.setMessage("是否退出软件?");
		ad.setPositiveButton("是", new DialogInterface.OnClickListener() {//退出按钮
			@Override
			public void onClick(DialogInterface dialog, int i) {
				MorePageActivity.this.finish();//关闭activity
			}
		});
		ad.setNegativeButton("否",new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int i) {
				//不退出不用执行任何操作
			}

		});
		ad.show();//显示对话框
	}

	@Override
	public void gotResult(int code, String alias, Set<String> tags) {
		String logs = "";
		switch (code) {
		case 0:
			logs = "Set tag and alias success, alias = " + alias + "; tags = " + tags;
			break;
		default:
			logs = "Failed with errorCode = " + code + " " + alias + "; tags = " + tags;
		}
		Log.d(TAG, "gotResult: "+logs);
	}

	@Override
	protected void initVariables() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		setContentView(R.layout.activity_more_page);

		mLoginManager = new LoginService(this);
		ListView mListView1 = (ListView)findViewById(R.id.list_view_1);
		ListView mListView2 = (ListView)findViewById(R.id.list_view_2);

		mData1 = new ArrayList<MoreListItem>();
		mData1.add(new MoreListItem(-1, "居委会电话:010-555555", "010-555555", R.drawable.call_bg));
		mData1.add(new MoreListItem(-1, "快递电话:010-555555", "010-555555", R.drawable.call_bg));
		mData1.add(new MoreListItem(-1, "派出所电话:010-555555", "010-555555", R.drawable.call_bg));
		MoreListAdapter adapter1 = new MoreListAdapter(this, mData1);

		mListView1.setAdapter(adapter1);
		mListView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MorePageActivity.this.onItemClick(parent, view, LIST_TYPE_1, position, id);
			}
		});

		mData2 = new ArrayList<MoreListItem>();
		mData2.add(new MoreListItem(R.drawable.feedback, "意见反馈", R.drawable.more_arrow));
		mData2.add(new MoreListItem(R.drawable.update, "版本更新", R.drawable.more_arrow));
		mData2.add(new MoreListItem(R.drawable.about, "关于CommunityService", R.drawable.more_arrow));
		mData2.add(new MoreListItem(R.drawable.logout, "退出", R.drawable.more_arrow));

		MoreListAdapter adapter2 = new MoreListAdapter(this, mData2);
		mListView2.setAdapter(adapter2);
		mListView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MorePageActivity.this.onItemClick(parent, view, LIST_TYPE_2, position, id);
			}
		});
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		
	}
}
