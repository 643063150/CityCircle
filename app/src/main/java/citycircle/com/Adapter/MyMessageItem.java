package citycircle.com.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import citycircle.com.JsonMordel.MessageList;
import citycircle.com.R;
import citycircle.com.Utils.DateUtils;

/**
 * Created by admins on 2016/6/27.
 */
public class MyMessageItem extends BaseAdapter {
    List<MessageList.DataBean.InfoBean> list;
    Context context;
    public MyMessageItem( List<MessageList.DataBean.InfoBean> list,Context context){
        this.list=list;
        this.context=context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        getItem getItem=new getItem();
        convertView= LayoutInflater.from(context).inflate(R.layout.message_item,null);
        getItem.content=(TextView)convertView.findViewById(R.id.content);
        getItem.time=(TextView)convertView.findViewById(R.id.time);
        getItem.title=(TextView)convertView.findViewById(R.id.title);
        getItem.content.setText(list.get(position).getContent());
        getItem.title.setText(list.get(position).getTitle());
        getItem.time.setText(DateUtils.getDateToStrings(Long.parseLong(list.get(position).getCreate_time())));
        return convertView;
    }
    public class getItem{
        TextView title,content,time;
    }
}
