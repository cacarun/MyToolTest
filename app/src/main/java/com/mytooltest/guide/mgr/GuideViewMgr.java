package com.mytooltest.guide.mgr;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import com.mytooltest.R;
import com.mytooltest.guide.GuideActivity;
import com.mytooltest.guide.view.GuideContainer;
import com.mytooltest.guide.view.GuideView;


public class GuideViewMgr {
    private static final String tag="GuideViewMgr";
    private final Activity activity;
    private final int dimension;
    private GuideView guideView;
    private GuideContainer guideContainer;
    private ScrollView scrollView;
    private View videoBtn;
    private View offerBtn;
    private View checkinLayout;

    public GuideViewMgr(Activity activity, GuideView guideView, GuideContainer guideContainer) {
        this.activity=activity;
        this.guideView = guideView;
        this.guideContainer = guideContainer;
        checkinLayout = activity.findViewById(R.id.activity_earn_free_credits_daily_checkin);
        videoBtn =  activity.findViewById(R.id.get_credits_video);
        offerBtn = activity.findViewById(R.id.get_credits_offer);
        scrollView = (ScrollView) activity.findViewById(R.id.scrollView);
        dimension = (int) activity.getResources().getDimension(R.dimen.guide_rect_boder);
    }

    private GUIDE_DETAIL currentGuide = GUIDE_DETAIL.TYPE_CHECK_IN;
    /**
     * 出现banner广告时刷新guide 位置
     */
    public void refreshGuide() {
        Log.i(tag, "refreshGuide");
//        scrollAdInSmallDevice();
        if (guideContainer == null || guideView == null) return;
        guideContainer.clearAllView();
        guideView.clearRect();
//        if (guideContainer.isShow()) {
//            switch (currentGuide) {
//                case TYPE_CHECK_IN:
//                    showGuideCheckIn();
//                    break;
//                case TYPE_FEELINGLUCKY:
//                    showGuideFeelingLucky();
//                    break;
//                case TYPE_NO_VEDIO_HIGH_VALUE:
//                    showGuideNoVideoHighValueCountry();
//                    break;
//                case TYPE_NO_VEDIO_LOW_VALUE:
//                    showGuideNoVideoLowValueCountry();
//                    break;
//                case TYPE_VEDIO_AGAIN_HIGH_VALUE:
//                    showGuideVideoAgainHighValueCountry();
//                    break;
//                case TYPE_VEDIO_AGAIN_LOW_VALUE:
//                    showGuideVideoAgainLowValueCountry();
//                case TYPE_VEDIO_NO_AGAIN_HIGH_VALUE:
//                    showGuideVideoNoAgainHightValue();
//                case TYPE_VEDIO_NO_AGAIN_LOW_VALUE:
//                    showGuideVideoNoAgainLowValueCountry();
//                    break;
//                default:
//                    return;
//            }
//            guideContainer.noAnimRefresh();
//        }

        showGuideCheckIn();
        guideContainer.noAnimRefresh();
    }

    public static boolean canShowGuide(GUIDE_TYPE type) {
        Log.i(tag, "canShowGuide" + type);
        return true;
    }

    public boolean showGuide(GuideActivity activity, GUIDE_TYPE type) {
        Log.i(tag, "showGuide...");
        guideContainer.clearAllView();
        guideView.clearRect();

//        scrollAdInSmallDevice();
        showGuideCheckIn();
        guideContainer.animIn(400);

        return true;
    }


//    private void scrollAdInSmallDevice() {
//        Log.i(tag, "scrollAdInSmallDevice");
//        if (scrollView == null) {
//            Log.e(tag, "scrollView is null!");
//            return;
//        }
//        DisplayMetrics metric = new DisplayMetrics();
//        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
//        int height = metric.heightPixels;   // 屏幕高度（像素）
//        Log.i(tag, "scrollAdInSmallDevice height" + height);
//        if (height > 1200) {
//            scrollView.scrollTo(0, 0);
//        } else {
//            View view = activity.findViewById(R.id.my_credit_lay);
//            if (view != null) {
//                scrollView.scrollTo(0, view.getHeight()+dimension);
//            }
//        }
//    }

    //    private void showGuideVideoAgainHighValueCountry() {
//        currentGuide = GUIDE_DETAIL.TYPE_VEDIO_AGAIN_HIGH_VALUE;
//        guideContainer.clearAllView();
//        Log.i(tag, "showGuideVideoHighValueCountry");
//        View inflate = activity.getLayoutInflater().inflate(R.layout.guide_item_watch_more_videos, null);
//        View inflate1 = activity.getLayoutInflater().inflate(R.layout.guide_item_complete_an_offer, null);
//        guideContainer.addBlackRect(videoBtn, inflate, null,dimension,new GuideView.OnClickListener() {
//            @Override
//            public boolean onClick() {
//                return false;
//            }
//        });
//        guideContainer.addBlackRect(offerBtn, null, inflate1, dimension, new GuideView.OnClickListener() {
//            @Override
//            public boolean onClick() {
//                return false;
//            }
//        });
//    }
//
//    private void showGuideVideoAgainLowValueCountry() {
//        currentGuide = GUIDE_DETAIL.TYPE_VEDIO_AGAIN_LOW_VALUE;
//        guideContainer.clearAllView();
//        Log.i(tag, "showGuideVideoLowValueCountry");
//        View view = activity.getLayoutInflater().inflate(R.layout.guide_item_watch_more_videos, null);
//        guideContainer.addBlackRect(videoBtn, view, null, dimension, new GuideView.OnClickListener() {
//            @Override
//            public boolean onClick() {
//                return false;
//            }
//        });
//        List<View> viewList = new ArrayList<>();
//        viewList.add(checkinLayout);
//        viewList.add(mFeelingLuckyText);
//        View inflate = activity.getLayoutInflater().inflate(R.layout.guide_item_check_in_for_novideo_highvalue, null);
//        View inflate1 = activity.getLayoutInflater().inflate(R.layout.guide_item_test_lucky_1_credits, null);
//        resetTipForGetInsta(inflate1);
//        guideContainer.addBlackRect(checkinLayout, inflate, null, -dimension, new GuideView.OnClickListener() {
//            @Override
//            public boolean onClick() {
//                return false;
//            }
//        });
//        guideContainer.addBlackRect(mFeelingLuckyText, null, inflate1, -dimension, new GuideView.OnClickListener() {
//            @Override
//            public boolean onClick() {
//                return false;
//            }
//        });
//    }
//    private void showGuideVideoNoAgainHightValue() {
//        currentGuide = GUIDE_DETAIL.TYPE_VEDIO_NO_AGAIN_HIGH_VALUE;
//        guideContainer.clearAllView();
//        DTLog.i(tag, "showGuideAfterVideoNoVideoHightValue");
//        View view = activity.getLayoutInflater().inflate(R.layout.guide_item_fast_get_free_credits, null);
//        guideContainer.addBlackRect(offerBtn, view, null, dimension, new GuideView.OnClickListener() {
//            @Override
//            public boolean onClick() {
//                DTLog.i(tag, "onclick "+ActionType.GET_CREDITS_GUIDE_HAVE_VIDEO_NO_AGAIN_HIGH_VALUE_COUNTRY_SHOW_CLICK_OFFER);
//                DTTracker.getInstance().sendEventV2(CategoryType.GET_CREDITS, ActionType.GET_CREDITS_GUIDE_HAVE_VIDEO_NO_AGAIN_HIGH_VALUE_COUNTRY_SHOW_CLICK_OFFER, "", 0);
//                SharedPreferencesUtil.saveIsNeedShowVideoGuide(false);
//                return false;
//            }
//        });
//    }
//    private void showGuideVideoNoAgainLowValueCountry() {
//        DTTracker.getInstance().sendEventV2(CategoryType.GET_CREDITS, ActionType.GET_CREDITS_GUIDE_HAVE_VIDEO_NO_AGAIN_LOW_VALUE_COUNTRY_SHOW, "", 0);
//        currentGuide = GUIDE_DETAIL.TYPE_VEDIO_NO_AGAIN_LOW_VALUE;
//        DTLog.i(tag, "showGuideAfterVideoNoVideoLowValueCountry");
//        guideContainer.clearAllView();
//        View inflate = activity.getLayoutInflater().inflate(R.layout.guide_item_check_in_for_novideo_highvalue, null);
//        View inflate1 = activity.getLayoutInflater().inflate(R.layout.guide_item_test_lucky_1_credits, null);
//        resetTipForGetInsta(inflate1);
//        guideContainer.addBlackRect(checkinLayout, inflate, null, -dimension, new GuideView.OnClickListener() {
//            @Override
//            public boolean onClick() {
//                DTLog.i(tag, "onclick "+ActionType.GET_CREDITS_GUIDE_HAVE_VIDEO_NO_AGAIN_LOW_VALUE_COUNTRY_SHOW_CLICK_CHECKIN);
//                DTTracker.getInstance().sendEventV2(CategoryType.GET_CREDITS, ActionType.GET_CREDITS_GUIDE_HAVE_VIDEO_NO_AGAIN_LOW_VALUE_COUNTRY_SHOW_CLICK_CHECKIN, "", 0);
//                SharedPreferencesUtil.saveIsNeedShowVideoGuide(false);
//                return false;
//            }
//        });
//        guideContainer.addBlackRect(mFeelingLuckyText, null, inflate1, -dimension, new GuideView.OnClickListener() {
//            @Override
//            public boolean onClick() {
//                DTLog.i(tag, "onclick "+ActionType.GET_CREDITS_GUIDE_HAVE_VIDEO_NO_AGAIN_LOW_VALUE_COUNTRY_SHOW_CLICK_FEELIINGLUCKY);
//                DTTracker.getInstance().sendEventV2(CategoryType.GET_CREDITS, ActionType.GET_CREDITS_GUIDE_HAVE_VIDEO_NO_AGAIN_LOW_VALUE_COUNTRY_SHOW_CLICK_FEELIINGLUCKY, "", 0);
//                SharedPreferencesUtil.saveIsNeedShowVideoGuide(false);
//                return false;
//            }
//        });
//    }
//
//    private void showGuideNoVideoHighValueCountry() {
//        DTTracker.getInstance().sendEventV2(CategoryType.GET_CREDITS, ActionType.GET_CREDITS_GUIDE_NO_VIDEO_HIGH_VALUE_COUNTRY_SHOW, "", 0);
//        currentGuide = GUIDE_DETAIL.TYPE_NO_VEDIO_HIGH_VALUE;
//        guideContainer.clearAllView();
//        DTLog.i(tag, "showGuideNoVideoHighValueCountry");
//        View view = activity.getLayoutInflater().inflate(R.layout.guide_item_fast_get_free_credits, null);
//        guideContainer.addBlackRect(offerBtn, view, null, dimension, new GuideView.OnClickListener() {
//            @Override
//            public boolean onClick() {
//                DTLog.i(tag, "onclick "+ActionType.GET_CREDITS_GUIDE_NO_VIDEO_HIGH_VALUE_COUNTRY_SHOW_CLICK_OFFER);
//                DTTracker.getInstance().sendEventV2(CategoryType.GET_CREDITS, ActionType.GET_CREDITS_GUIDE_NO_VIDEO_HIGH_VALUE_COUNTRY_SHOW_CLICK_OFFER, "", 0);
//                SharedPreferencesUtil.saveIsNeedShowVideoGuide(false);
//                return false;
//            }
//        });
//
//        View inflate = activity.getLayoutInflater().inflate(R.layout.guide_item_check_in_for_novideo_highvalue, null);
//        View inflate1 = activity.getLayoutInflater().inflate(R.layout.guide_item_test_lucky_1_credits, null);
//        resetTipForGetInsta(inflate1);
//        guideContainer.addBlackRect(checkinLayout, inflate, null, -dimension, new GuideView.OnClickListener() {
//            @Override
//            public boolean onClick() {
//                DTLog.i(tag, "onclick "+ActionType.GET_CREDITS_GUIDE_NO_VIDEO_HIGH_VALUE_COUNTRY_SHOW_CLICK_CHECKIN);
//                DTTracker.getInstance().sendEventV2(CategoryType.GET_CREDITS, ActionType.GET_CREDITS_GUIDE_NO_VIDEO_HIGH_VALUE_COUNTRY_SHOW_CLICK_CHECKIN, "", 0);
//                SharedPreferencesUtil.saveIsNeedShowVideoGuide(false);
//                return false;
//            }
//        });
//        guideContainer.addBlackRect(mFeelingLuckyText, null, inflate1, -dimension, new GuideView.OnClickListener() {
//            @Override
//            public boolean onClick() {
//                DTLog.i(tag, "onclick "+ActionType.GET_CREDITS_GUIDE_NO_VIDEO_HIGH_VALUE_COUNTRY_SHOW_CLICK_FEELINGLUCKY);
//                DTTracker.getInstance().sendEventV2(CategoryType.GET_CREDITS, ActionType.GET_CREDITS_GUIDE_NO_VIDEO_HIGH_VALUE_COUNTRY_SHOW_CLICK_FEELINGLUCKY, "", 0);
//                SharedPreferencesUtil.saveIsNeedShowVideoGuide(false);
//                return false;
//            }
//        });
//    }
//    private void showGuideNoVideoLowValueCountry() {
//        DTTracker.getInstance().sendEventV2(CategoryType.GET_CREDITS, ActionType.GET_CREDITS_GUIDE_NO_VIDEO_LOW_VALUE_COUNTRY_SHOW, "", 0);
//        currentGuide = GUIDE_DETAIL.TYPE_NO_VEDIO_LOW_VALUE;
//        guideContainer.clearAllView();
//        DTLog.i(tag, "showGuideNoVideoLowValueCountry");
//        View inflate = activity.getLayoutInflater().inflate(R.layout.guide_item_check_in_for_free_credits, null);
//        View inflate1 = activity.getLayoutInflater().inflate(R.layout.guide_item_test_lucky_1_credits, null);
//        resetTipForGetInsta(inflate1);
//        guideContainer.addBlackRect(checkinLayout, inflate, null, -dimension, new GuideView.OnClickListener() {
//            @Override
//            public boolean onClick() {
//                DTLog.i(tag, "onclick "+ActionType.GET_CREDITS_GUIDE_NO_VIDEO_LOW_VALUE_COUNTRY_SHOW_CLICK_CHECKIN);
//                DTTracker.getInstance().sendEventV2(CategoryType.GET_CREDITS, ActionType.GET_CREDITS_GUIDE_NO_VIDEO_LOW_VALUE_COUNTRY_SHOW_CLICK_CHECKIN, "", 0);
//                SharedPreferencesUtil.saveIsNeedShowVideoGuide(false);
//                return false;
//            }
//        });
//        guideContainer.addBlackRect(mFeelingLuckyText, null, inflate1, -dimension, new GuideView.OnClickListener() {
//            @Override
//            public boolean onClick() {
//                DTLog.i(tag, "onclick "+ActionType.GET_CREDITS_GUIDE_NO_VIDEO_LOW_VALUE_COUNTRY_SHOW_CLICK_FEELINGLUCKY);
//                DTTracker.getInstance().sendEventV2(CategoryType.GET_CREDITS, ActionType.GET_CREDITS_GUIDE_NO_VIDEO_LOW_VALUE_COUNTRY_SHOW_CLICK_FEELINGLUCKY, "", 0);
//                SharedPreferencesUtil.saveIsNeedShowVideoGuide(false);
//                return false;
//            }
//        });
//    }
//
//
//    private void showGuideFeelingLucky() {
//        DTTracker.getInstance().sendEventV2(CategoryType.GET_CREDITS, ActionType.GET_CREDITS_GUIDE_FEELINGLUCK_SHOW, "", 0);
//        currentGuide = GUIDE_DETAIL.TYPE_FEELINGLUCKY;
//        guideContainer.clearAllView();
//        DTLog.i(tag, "showGuideFeelingLucky");
//        View inflate =activity. getLayoutInflater().inflate(R.layout.guide_item_feeling_lucky, null);
//        guideContainer.addBlackRect(mFeelingLuckyText, inflate, null, -10, 0,new GuideView.OnClickListener() {
//            @Override
//            public boolean onClick() {
//                DTLog.i(tag, "onclick "+ActionType.GET_CREDITS_GUIDE_FEELINGLUCK_SHOW_CLICK);
//                DTTracker.getInstance().sendEventV2(CategoryType.GET_CREDITS, ActionType.GET_CREDITS_GUIDE_FEELINGLUCK_SHOW_CLICK, "", 0);
//                SharedPreferencesUtil.saveIsNeedShowFeelingLuckyGuide(false);
//                return true;
//            }
//        });
//    }
//
//
    public enum GUIDE_TYPE {
        TYPE_VEDIO_AGAIN,
        TYPE_VEDIO_NO_AGAIN,
        TYPE_NO_VEDIO,
        TYPE_CHECK_IN,
        TYPE_FEELINGLUCKY
    }

    public enum GUIDE_DETAIL {
        TYPE_VEDIO_AGAIN_HIGH_VALUE,
        TYPE_VEDIO_AGAIN_LOW_VALUE,
        TYPE_VEDIO_NO_AGAIN_HIGH_VALUE,
        TYPE_VEDIO_NO_AGAIN_LOW_VALUE,
        TYPE_NO_VEDIO_HIGH_VALUE,
        TYPE_NO_VEDIO_LOW_VALUE,
        TYPE_CHECK_IN,
        TYPE_FEELINGLUCKY
    }
//
//    private void resetTipForGetInsta(View view){
//        if (GetInstaManager.getInstance().checkNeedShowEntryForGide() == GetInstaManager.REPLACE_LUCKY){
//            TextView tip = (TextView) view.findViewById(R.id.tv_feeling_lucky_tip);
//            tip.setText(DTApplication.getInstance().getString(R.string.getinsta_entry_tip,GetInstaManager.getInstance().getTaskCreditLimit(),DTApplication.getInstance().getResources().getString(R.string.credit)));
//        }
//    }

    private void showGuideCheckIn() {
        currentGuide = GUIDE_DETAIL.TYPE_CHECK_IN;
        guideContainer.clearAllView();
        Log.i(tag, "showGuideCheckIn");
        View inflate = activity.getLayoutInflater().inflate(R.layout.guide_item_check_in, null);
        guideContainer.addBlackRect(checkinLayout, inflate, null, -10, 0,new GuideView.OnClickListener() {
            @Override
            public boolean onClick() {
                return true;
            }
        });
    }

}
