package com.wj.simplemupdf;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.artifex.mupdfdemo.ScreenSwitchUtils;
import com.artifex.mupdfdemo.WJPdfView;

public class MainActivity extends AppCompatActivity {
    private WJPdfView mPdfView;
    private ScreenSwitchUtils instance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = ScreenSwitchUtils.init(this.getApplicationContext());
        mPdfView= (WJPdfView) findViewById(R.id.mupdf);
        mPdfView.openPDF("http://obor.ecache.cn:80/Uplode/Books/Data/bd71945e00e54ad1a891974bac6b61dd.pdf");
        mPdfView.setOnPdfListener(new WJPdfView.OnPdfListener() {
            @Override
            public void finish() {
                MainActivity.this.finish();
            }

            @Override
            public void onCompletion() {
                //观看完成
            }

            @Override
            public void toggleScreen() {
                //横竖屏切换
                instance.toggleScreen();
            }

            @Override
            public void onstart() {
                //启动换横屏监听
                instance.start(MainActivity.this);
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (instance.isPortrait()) {
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if(mPdfView.getVisibility()==View.VISIBLE) {
                mPdfView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));//设置显示的高度
                mPdfView.requestLayout();
                mPdfView.updateAdapter();
            }
        } else  {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            if(mPdfView.getVisibility()==View.VISIBLE) {
                mPdfView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                mPdfView.requestLayout();
                mPdfView.updateAdapter();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPdfView.onDestroy();
    }
}
