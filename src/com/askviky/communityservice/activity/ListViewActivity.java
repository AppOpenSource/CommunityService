package com.askviky.communityservice.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.askviky.common.activity.AppBaseActivity;
import com.askviky.communityservice.R;
import com.askviky.communityservice.adapter.ListViewAdapter;

public class ListViewActivity extends AppBaseActivity {

	private ListView mServerList;
	private List<Map<String, Object>> mListData;

	public List<Map<String, Object>> getDataList() {
		mListData = new ArrayList<Map<String,Object>>();
		
		int img[] = { R.drawable.server_img, R.drawable.server_img, R.drawable.server_img };
		String name[] = { "XX小区物业", "XX小区物业", "XX小区物业" };
		String address[] = { "地址：北京市海淀区中关村", "地址：北京市海淀区中关村", "地址：北京市海淀区中关村" };
		String telephone[] = { "电话：010-12345", "电话：010-12345", "电话：010-12345" };
		
		for (int i=0; i<img.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("imgId", img[i]);
			map.put("name", name[i]);
			map.put("address", address[i]);
			map.put("telephone", telephone[i]);
			mListData.add(map);
		}
		return mListData;
	}

	@Override
	protected void initVariables() {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}

	@Override
	protected void initViews(Bundle savedInstanceState) {
		setContentView(R.layout.activity_listview);
		
		mServerList = (ListView) findViewById(R.id.serve_list);
		mListData = getDataList();
		ListViewAdapter adapter = new ListViewAdapter(this, mListData);
		mServerList.setAdapter(adapter);
	}

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		
	}
}
