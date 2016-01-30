package com.nice.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.RelativeLayout;

import com.nice.R;
import com.nice.model.NiceQuestion;
import com.nice.model.NicetSheet;
import com.nice.widget.NiceImageView;

import org.byteam.superadapter.list.BaseViewHolder;
import org.byteam.superadapter.list.SuperAdapter;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by jiao on 2016/1/21.
 */
public class NewQuestionAdapter extends SuperAdapter<NicetSheet> {

    private List<Integer> selected;

    public NewQuestionAdapter(Context context, List<NicetSheet> datas, int layoutId) {
        super(context, datas, layoutId);
        selected = new ArrayList<>();
    }

    @Override
    protected void onBind(int viewType, BaseViewHolder holder, final int position, final NicetSheet item) {
        holder.setText(R.id.item_new_question_name, item.shName);
        holder.setText(R.id.item_new_question_time, item.rbTime);

        RelativeLayout chooseLayout = holder.getView(R.id.item_new_question_choose_layout);
        final NiceImageView chooseButton = holder.getView(R.id.item_new_question_choose_btn);
        if(selected.contains(position)){
            chooseButton.setImageResource(R.mipmap.checkmark_black);
        }else{
            chooseButton.setImageResource(R.mipmap.checkmark_side_black);
        }
        chooseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("choose".equals(v.getTag())){
                    chooseButton.setImageResource(R.mipmap.checkmark_side_black);
                    v.setTag(null);
                    selected.remove(position);
                }else{
                    chooseButton.setImageResource(R.mipmap.checkmark_black);
                    v.setTag("choose");
                    selected.add(position);
                    if (selected.size() == mList.size()){
                        EventBus.getDefault().post("allSelected");
                    }
                }
            }

        });
    }

    public List<String> getSelected(){
        List<String> ids = new ArrayList<>();
        for(int position : this.selected){
            ids.add(String.valueOf(mList.get(position).shId));
        }
        return ids;
    }

    public void allSelected(){
        selected.clear();
        for (int i = 0; i < mList.size(); i++) {
            selected.add(i);
        }
        notifyDataSetChanged();
    }

    public void clearAllSelected(){
        selected.clear();
        notifyDataSetChanged();
    }

}
