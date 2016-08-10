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
    int type;
    public MyMessageItem( List<MessageList.DataBean.InfoBean> list,Context context,int type){
        this.list=list;
        this.context=context;
        this.type=type;
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
        switch (type){
            case 1:
                getItem.content.setText("系统消息:"+list.get(position).getContent());
                break;
            case 2:
                getItem.content.setText(list.get(position).getXqname()+":"+list.get(position).getContent());
                break;
            case 3:
                getItem.content.setText(list.get(position).getShopname()+":"+list.get(position).getContent());
                break;
        }

        getItem.title.setText(list.get(position).getTitle());
        getItem.time.setText(DateUtils.getDateToStringss(Long.parseLong(list.get(position).getCreate_time())));
        return convertView;
    }
    public class getItem{
        TextView title,content,time;
    }
}
