package com.nice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.nice.R;
import com.nice.util.Denisty;
import com.nice.widget.NiceImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chen on 2016/1/24.
 */
public class QuestionContextAdapter extends AbsAdapter<String> {

    private Map<Integer, Integer> selectedValues = new HashMap<>();
    private Map<Integer, List<NiceImageView>> imageViewMap = new HashMap<>();

    public QuestionContextAdapter(@NonNull List<String> datas, Context context, int... layoutId) {
        super(datas, context, layoutId);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position % 3){
            case 0:return 0;
            case 1:return 1;
            default:return 2;
        }
    }

    @Override
    public void onBindViewHolder(AbsViewHolder holder, final int position) {
        switch (position % 3){
            case 0:onBindViewHolder_singleSelected(holder, position);break;
            case 1:onBindViewHolder_MutiSelected(holder, position);break;
        }
    }

    private void onBindViewHolder_MutiSelected(final AbsViewHolder holder, final int position){

        if(0 == (selectedValues.containsKey(position) ? selectedValues.get(position) : -1)){
            holder.getView(R.id.muti_selected_not_image).setSelected(false);
            holder.getView(R.id.muti_selected_layout_not_editText).setVisibility(View.GONE);
            holder.getView(R.id.muti_selected_layout_ok).setSelected(true);
            holder.getView(R.id.muti_selected_layout_ok_editText).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.muti_selected_not_image).setSelected(true);
            holder.getView(R.id.muti_selected_layout_not_editText).setVisibility(View.VISIBLE);
            holder.getView(R.id.muti_selected_layout_ok).setSelected(false);
            holder.getView(R.id.muti_selected_layout_ok_editText).setVisibility(View.GONE);
        }
        holder.getView(R.id.muti_selected_layout_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.getView(R.id.muti_selected_not_image).setSelected(false);
                holder.getView(R.id.muti_selected_layout_not_editText).setVisibility(View.GONE);
                holder.getView(R.id.muti_selected_layout_ok).setSelected(true);
                holder.getView(R.id.muti_selected_layout_ok_editText).setVisibility(View.VISIBLE);
                selectedValues.put(position, 0);
            }
        });

        holder.getView(R.id.muti_selected_layout_not).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.getView(R.id.muti_selected_not_image).setSelected(true);
                holder.getView(R.id.muti_selected_layout_not_editText).setVisibility(View.VISIBLE);
                holder.getView(R.id.muti_selected_layout_ok).setSelected(false);
                holder.getView(R.id.muti_selected_layout_ok_editText).setVisibility(View.GONE);
                selectedValues.put(position, 1);
            }
        });

    }

    private void onBindViewHolder_singleSelected(AbsViewHolder holder, final int position){
        final LinearLayout linearLayout = holder.getView(R.id.signleselect_radio);
        linearLayout.removeAllViews();
        List<NiceImageView> imageViewsTemp = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            final View view = View.inflate(context, R.layout.selected_single, null);
            final NiceImageView imageView = (NiceImageView) view.findViewById(R.id.item_new_question_choose_btn);
            imageView.setSelected(i == (selectedValues.containsKey(position) ? selectedValues.get(position) : -1));
            final int tag = i;
            ((TextView) view.findViewById(R.id.item_new_question_name)).setText("选项" + i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(position + ":" + tag);
                    selectedValues.put(position, tag);
                    for (NiceImageView imageView1 : imageViewMap.get(position)){
                        imageView1.setSelected(false);
                    }
                    imageView.setSelected(true);
                }
            });
            imageViewsTemp.add(imageView);
            linearLayout.addView(view);
        }
        imageViewMap.put(position, imageViewsTemp);
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
