package com.nice.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;

import com.nice.R;
import com.nice.model.NiceQuestion;
import com.nice.widget.NiceImageView;

import java.util.List;

/**
 * Created by jiao on 2016/1/21.
 */
public class NewQuestionAdapter extends AbsAdapter<NiceQuestion> {

    public NewQuestionAdapter(@NonNull List<NiceQuestion> datas, Context context, int... layoutId) {
        super(datas, context, layoutId);
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(AbsViewHolder holder, int position) {
        holder.setText(R.id.item_new_question_name, datas.get(position).name);
        System.out.println("111111111111111");
        holder.setText(R.id.item_new_question_time, datas.get(position).time);

        RelativeLayout chooseLayout = holder.getView(R.id.item_new_question_choose_layout);
        final NiceImageView chooseButton = holder.getView(R.id.item_new_question_choose_btn);
        chooseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("choose".equals(v.getTag())){
                    chooseButton.setImageResource(R.mipmap.checkmark_black);
                    v.setTag(null);
                }else{
                    chooseButton.setImageResource(R.mipmap.checkmark_side_black);
                    v.setTag("choose");
                }
            }

        });
    }

}
