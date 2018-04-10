package com.askviky.communityservice.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

import com.amap.mapapi.map.MapActivity;
import com.amap.mapapi.map.MapController;
import com.amap.mapapi.map.MapView;
import com.askviky.common.view.MapItemizedOverlay;
import com.askviky.communityservice.R;

public class MapPageActivity extends MapActivity implements OnClickListener {

	private MapView mMapView;
	private MapController mController;
	private ImageButton mBack;//返回按钮

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_map_page);
		
		mBack = (ImageButton) findViewById(R.id.back_btn);
		mBack.setOnClickListener(this);
		
		mMapView = (MapView) findViewById(R.id.map_view);
		mMapView.setBuiltInZoomControls(true);
		mController = mMapView.getController();
		
		Drawable marker = getResources().getDrawable(R.drawable.da_marker_red);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());
		mMapView.getOverlays().add(new MapItemizedOverlay(marker, this));
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back_btn:
			this.finish();
			break;
		}
	}
}
