package com.nice.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import com.nice.R;
import com.nice.model.NiceQuestion;

import java.util.List;

/**
 * Created by jiao on 2016/1/22.
 */
public class IncompleteQuestionAdapter extends AbsAdapter<NiceQuestion> {


    public IncompleteQuestionAdapter(@NonNull List datas, Context context, int... layoutId) {
        super(datas, context, layoutId);
    }


    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(AbsViewHolder holder, int position) {
        holder.setText(R.id.item_incomplete_question_name, datas.get(position).name);
        holder.setText(R.id.item_incomplete_question_time, datas.get(position).name);
    }
}
