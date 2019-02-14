package com.lib.grouplist;

import android.view.View;

/**
 * Group 标题被点击的回调事件
 *
 */
public interface OnGroupClickListener {
    /**
     * Callback when the group item was clicked.
     *
     * @param itemView      the itemView of the group item.
     * @param groupPosition the position of the group.
     */
    void onGroupItemClick(View itemView, int groupPosition);
}
