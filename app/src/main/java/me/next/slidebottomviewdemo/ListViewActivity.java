package me.next.slidebottomviewdemo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import me.next.slidebottompanel.SlideBottomPanel;

public class ListViewActivity extends AppCompatActivity {

    private String webUrl = "http://www.zhihu.com/question/29416073/answer/44340933";
    private WebView webView;
    private ListView listView;
    private ArrayList<String> list = new ArrayList<>();
    private SlideBottomPanel sbv;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        sbv = (SlideBottomPanel) findViewById(R.id.sbv);
        webView = (WebView) findViewById(R.id.wv_main);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(webUrl);
        webView.setWebChromeClient(new WebChromeClient());

        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, getData()));
    }

    private ArrayList<String> getData()
    {
        for (int i = 0; i < 20; i++) {
            list.add("Item " + i);
        }
        return list;
    }

    @Override
    public void onBackPressed() {
        if (sbv.isPanelShowing()) {
            sbv.hide();
            return;
        }
        super.onBackPressed();
    }
}
