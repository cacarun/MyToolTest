package com.mytooltest.guide;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;

import com.mytooltest.R;
import com.mytooltest.guide.mgr.GuideViewMgr;
import com.mytooltest.guide.view.GuideContainer;
import com.mytooltest.guide.view.GuideView;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private GuideView guideView;
    private GuideContainer guideContainer;
    private GuideViewMgr guideViewMgr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        findViewById(R.id.btn_show).setOnClickListener(this);

        initGuideView();

    }

    private void initGuideView() {
        ViewStub guideContainerStub = (ViewStub) findViewById(R.id.guide_container_stub);
        View view = guideContainerStub.inflate();
        guideContainer = (GuideContainer) view.findViewById(R.id.guideContainer);
        guideView = (GuideView) view.findViewById(R.id.guideView);
        guideContainer.setGuideView(guideView);
        guideViewMgr = new GuideViewMgr(this, guideView, guideContainer);
    }



    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.btn_show) {

            Log.i("GuideActivity", "click");
            guideViewMgr.showGuide(GuideActivity.this, GuideViewMgr.GUIDE_TYPE.TYPE_CHECK_IN);
        }

    }

}
