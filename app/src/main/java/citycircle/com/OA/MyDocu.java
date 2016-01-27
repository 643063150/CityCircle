package citycircle.com.OA;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import citycircle.com.R;

/**
 * Created by admins on 2016/1/26.
 */
public class MyDocu extends Fragment {
    View view;
    ListView doculist;
    ArrayList<HashMap<String, String>> arraylist = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> hashmap;
    String path;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.document_lay, container, false);
        path= Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/citycircle/OADowns";
        getDocument(path);
        intview();
        return view;
    }
    private void intview() {
        doculist = (ListView) view.findViewById(R.id.doculist);
    }
    private void getDocument(String path) {
        File file = new File(path);
        File[] subFile = file.listFiles();
        for (int iFileLength = 0; iFileLength < subFile.length; iFileLength++) {
            // 判断是否为文件夹
            if (!subFile[iFileLength].isDirectory()) {
                String filename = subFile[iFileLength].getName();
                // 判断是否为MP4结尾
                hashmap=new HashMap<>();
                hashmap.put("name",filename);
                hashmap.put("path",path+"/"+filename);
                arraylist.add(hashmap);
            }
        }
    }
}
