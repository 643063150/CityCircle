package citycircle.com.Property;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import citycircle.com.R;
import citycircle.com.Utils.GlobalVariables;

/**
 * Created by admins on 2016/1/30.
 */
public class SelectMessage extends Activity {
    EditText keyed;
    ListView content;
    int type;
    TextView title;
    ImageView back;
    String url,urlstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectmessage);
        intview();
        type=getIntent().getIntExtra("type",1);
        if (type==2){
            keyed.setHint("根据门牌号查询 (如：xx号)");
            title.setText("选择门牌");
        }else if (type==3){
            keyed.setHint("根据房屋室号查询 (如：xx室)");
            title.setText("选择室号");
        }else {
            url= GlobalVariables.urlstr+"Common.getHouseList&pid=0&type=0";
        }
    }
    private void intview() {
        keyed=(EditText)findViewById(R.id.keyed);
        content=(ListView)findViewById(R.id.content);
        title=(TextView)findViewById(R.id.title);
        back=(ImageView)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
