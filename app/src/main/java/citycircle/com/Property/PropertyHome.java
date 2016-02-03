package citycircle.com.Property;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import citycircle.com.OA.ContactsActivity;
import citycircle.com.OA.OaUserinfo;
import citycircle.com.R;

/**
 * Created by admins on 2016/1/30.
 */
public class PropertyHome extends Activity implements View.OnClickListener{
    ImageView back;
    TextView messages, DOC, meeting, phonenumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pro_home);
        intview();
    }
    private void intview(){
        back = (ImageView) findViewById(R.id.loginback);
        back.setOnClickListener(this);
        messages = (TextView) findViewById(R.id.messages);
        DOC = (TextView) findViewById(R.id.DOC);
        phonenumber = (TextView) findViewById(R.id.phonenumber);
        meeting = (TextView) findViewById(R.id.meeting);
        messages.setOnClickListener(this);
        DOC.setOnClickListener(this);
        meeting.setOnClickListener(this);
        phonenumber.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.loginback:
                finish();
                break;
            case R.id.messages:
                intent.setClass(PropertyHome.this, Information.class);
                PropertyHome.this.startActivity(intent);
                break;
            case R.id.phonenumber:
                intent.setClass(PropertyHome.this, ContactsActivity.class);
                PropertyHome.this.startActivity(intent);
                break;
            case R.id.meeting:
                intent.setClass(PropertyHome.this, OaUserinfo.class);
                PropertyHome.this.startActivity(intent);
                break;
            case R.id.DOC:
                intent.setClass(PropertyHome.this, AddHome.class);
                PropertyHome.this.startActivity(intent);
                break;
        }
    }
}
