package com.askviky.communityservice.common;

public class GlobalConstant {
	
	public static final String SERVER_IP = "192.168.31.197";
	public static final String DB_NAME = "cookbook";
	public static final String USER = "askviky";
	public static final String PASSWD = "123456";
	public static final String DB_URL = "jdbc:mysql://"+SERVER_IP+"/"+DB_NAME;
	
	public static final String FIGURE_FLAG_FIGURE = "figure"; // 配图falg
	public static final String FIGURE_FLAG_MATERIAL = "material"; // 食材配图falg
	public static final String FIGURE_FLAG_MOVE = "move"; // 做法配图falg
	
	public static final int TAB_COUNT = 3; // 选项卡总数
}
