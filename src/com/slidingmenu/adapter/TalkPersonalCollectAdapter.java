package com.slidingmenu.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.teachersystem.R;

public class TalkPersonalCollectAdapter extends BaseAdapter {

	private LayoutInflater mInflater = null;
	private List<Map<String, Object>> data;

	public TalkPersonalCollectAdapter(Context context,List data) {
		// 根据context上下文加载布局，这里的是Demo17Activity本身，即this
		this.data=data;
		this.mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// How many items are in the data set represented by this Adapter.
		// 在此适配器中所代表的数据集中的条目数
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// Get the data item associated with the specified position in the data
		// set.
		// 获取数据集中与指定索引对应的数据项
		return position;
	}

	@Override
	public long getItemId(int position) {
		// Get the row id associated with the specified position in the list.
		// 获取在列表中与指定索引对应的行id
		return position;
	}

	// Get a View that displays the data at the specified position in the data
	// set.
	// 获取一个在数据集中指定索引的视图来显示数据
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		// 如果缓存convertView为空，则需要创建View
		if (convertView == null) {
			holder = new ViewHolder();
			// 根据自定义的Item布局加载布局
			convertView = mInflater.inflate(R.layout.listview_item, null);
			holder.title = (TextView) convertView.findViewById(R.id.lv_tv_item_name);
			holder.info = (TextView) convertView.findViewById(R.id.lv_tv_item);
			// 将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText((String) data.get(position).get("title"));
		holder.info.setText((String) data.get(position).get("info"));

		return convertView;
	}

	// ViewHolder静态类
	static class ViewHolder {
		public TextView title;
		public TextView info;
	}
}