package com.nice.adapter;

import android.content.Context;

import com.nice.R;
import com.nice.model.NicetSheet;

import org.byteam.superadapter.list.BaseViewHolder;
import org.byteam.superadapter.list.SuperAdapter;

import java.util.List;

/**
 * Created by chen on 2016/1/27.
 */
public class FinishQuestAdapter extends SuperAdapter<NicetSheet> {

    public FinishQuestAdapter(Context context, List<NicetSheet> data, int layoutResId) {
        super(context, data, layoutResId);
    }

    @Override
    protected void onBind(int viewType, BaseViewHolder holder, int position, NicetSheet item) {
        holder.setText(R.id.item_finish_question_name, item.shName);
        holder.setText(R.id.item_finish_question_time, item.rbTime);
    }
}
