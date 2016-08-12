package citycircle.com.MyAppService;

import android.content.Context;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;

import org.greenrobot.eventbus.EventBus;

import citycircle.com.Utils.MyEventBus;

/**
 * Created by admins on 2016/8/9.
 */
public class Message extends MessageReceiver {
    @Override
    protected void onMessage(Context context, CPushMessage cPushMessage) {
        super.onMessage(context, cPushMessage);
        System.out.println("cPushMessage:"+cPushMessage.getTitle());
        EventBus.getDefault().post(
                new MyEventBus("show"));
    }
}
