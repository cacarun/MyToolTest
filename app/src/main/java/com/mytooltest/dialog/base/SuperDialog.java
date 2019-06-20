
package com.mytooltest.dialog.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

import java.lang.ref.WeakReference;

public class SuperDialog extends Dialog {
    private static final String TAG = "SuperDialog";

    private WeakReference<Context> contextWeakReference = null;

    /**
     * @param context
     */
    public SuperDialog(Context context) {
        super(context);
        contextWeakReference = new WeakReference<Context>(context);
    }

    /**
     * @param context
     */
    public SuperDialog(Context context, int theme) {
        super(context, theme);
        contextWeakReference = new WeakReference<Context>(context);
    }

    @Override
    public void show() {
        if(contextWeakReference != null) {
            Context context = contextWeakReference.get();
            if (context instanceof Activity && !((Activity)context).isFinishing()){
                try {
                    super.show();
                } catch(WindowManager.BadTokenException e) {
                }
            }
        }
    }

    public void show(OnDismissListener onDismissListener) {
        if(null!=onDismissListener){
            this.setOnDismissListener(onDismissListener);
        }
        show();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
