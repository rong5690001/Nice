package com.nice.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.nice.R;
import com.nice.model.NiceQuestion;
import com.nice.model.NicetSheet;

import org.byteam.superadapter.list.BaseViewHolder;
import org.byteam.superadapter.list.SuperAdapter;

import java.util.List;

/**
 * Created by jiao on 2016/1/22.
 */
public class IncompleteQuestionAdapter extends SuperAdapter<NicetSheet> {


    public IncompleteQuestionAdapter(Context context, List<NicetSheet> data, int layoutResId) {
        super(context, data, layoutResId);
    }


    @Override
    protected void onBind(int viewType, BaseViewHolder holder, int position, NicetSheet item) {
        holder.setText(R.id.item_incomplete_question_name, (item.shName).length()<12?item.shName:((item.shName).substring(0,12)+"..."));
        holder.setText(R.id.item_incomplete_question_time, item.rbTime);
    }

}
