package com.askviky.common;

public class CodingStandard {

	/*
	 * *****************
	 * 文件命名规范
	 * *****************
	 * 
	 * 	(一) Layout命名
	 * 	1．contentview命名：activity_功能模块.xml
	 * 	例如：activity_main.xml、activity_more.xml
	 * 	2．Dialog命名：dialog_描述.xml
	 * 	例如：dlg_hint.xml
	 * 	3．PopupWindow命名：ppw_描述.xml
	 * 	例如：ppw _info.xml
	 * 	4. 列表项命名listitem_描述.xml
	 * 	例如：listitem_city.xml
	 * 	5．包含项：include_模块.xml
	 * 	例如：include_head.xml、include_bottom.xml
	 * 
	 * 	(二) 图片命名
	 * 	1.静态图片前缀_模块、前缀_模块_描述
	 * 	例如：bg_main.png、ic_main_search.png
	 * 	2.动态图片前缀_模块_描述_状态、前缀_描述_状态
	 * 	例如：btn_film_buy_n.png、btn_film_buy_p.png、btn_back_n.png
	 * 	如果有多种形态如按钮等除外如btn_film_buy.xml（selector）
	 * 	
	 * 	(三) id命名
	 * 	Xml中id的命名，建议直接根据意义命名，不必使用以上复杂的定位，因为findViewById只在某指定layout中find或者value中建立id文件统一管理（兼容性不好）
	 * 	原文作者常用：android:id="@id/tv_name"android:id="@id/listv"、android:id="@id/tv_head_title"
	 * 	 个人习惯使用：android:id="@id/tvName"android:id="@id/listvName"、android:id="@id/tvHeadTitle",然后在class里的命名和id名字保持一致
	 * 	 
	 * 	(四) 字符串命名
	 * 	最好不要跟title、dialog、button等东西关系起来，直接用相同英文含义就可以了，如果下：
	 * 	<string name="ok">确定</string>
	 * 	<string name=" welcome_to_use">欢迎使用</string>
	 * 	再举一些不好的例子，如下<string name="menu_ok">确定</string>
	 * 	
	 * 	(五) 控件命名
	 * 	控件缩写_描述
	 * 	例如：TextView tv_name、Button btn_buy、LinearLayout llyt_body;
	 * 	
	 * 	(六) 类命名
	 * 	功能模块Activity.class功能模块Service.class
	 * 	如：MainActivity.class、HuaFuBaoActivity.class、AppUpgradeService.class
	 * 	
	 * 	(七) 资源缩写说明
	 * 	1.前缀|说明
	 * 	ic--icon 主要用于布局和子布局的图标
	 * 	bg--background 主要用于布局和子布局的背景
	 * 	di--divider 主要用于分隔线，不仅包括Listview中的还包括普通布局中的线
	 * 	sl--selector 主要用于某一view多种状态，listview 按钮等
	 * 	cl--color 主要用于颜色值
	 * 	bt--button 主要用于按钮的表示，有时我们会在ic和bt之间犹豫，简单的区分即是功能视图，如果一个view执行的时back或者confirm或者cancel的功能，则命名上则应该使用bt
	 * 	2.后缀|说明
	 * 	unit 在使用xml的tilemode来配图片时，element图片使用此后缀
	 * 	nor 图片的状态，代表普通状态
	 * 	hl 图片的状态，代表高亮状态
	 * 	press 图片的状态，代表按下状态
	 * 	select 图片的状态，代表其所占的view被选中
	 * 	unselect 图片的状态，代表其所占的view没有被选中
	 * 	3.组件名称|简写
	 * 	Button|Btn(btn)
	 * 	RadioButton|Rbtn(rbtn)
	 * 	ImageButton|Ibtn(ibtn)
	 * 	TextView|Tv(tv)
	 * 	ImageView|Iv(iv)
	 * 	ListView|Lv(lv)
	 * 	ProgressBar|Pbar(pbar)
	 * 	EditText|Edtv(et)
	 * 	ScrollView|Sclv(scly)
	 * 	CheckBox|Chk(chk)
	 * 	RelativeLayout|Rlyt(rlyt)
	 * 	LinearLayout|Llyt(llyt)
	 * 	TableLayout|Tlyt(tlyt)
	 * 	AbsoluteLayout|ALyt(alyt)
	 * 	FrameLayout|Flyt(flyt)
	 *
	 * *****************
	 * 文件命名规范
	 * *****************
	 */
	
	/*
	 * *****************
	 * 编码规范
	 * *****************
	 * 
	 * 
	 * *****************
	 * 编码规范
	 * *****************
	 * 
	 */
}
