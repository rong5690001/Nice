package com.nice.adapter;

import android.content.Context;
import android.view.View;

import com.nice.R;
import com.nice.model.Event.SwitchGroupEvent;

import org.byteam.superadapter.list.BaseViewHolder;
import org.byteam.superadapter.list.IMultiItemViewType;
import org.byteam.superadapter.list.SuperAdapter;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by jiao on 2016/1/28.
 */
public class GroupAdapter extends SuperAdapter<String> {
    public GroupAdapter(Context context, List<String> data, IMultiItemViewType<String> multiItemViewType) {
        super(context, data, multiItemViewType);
    }

    public GroupAdapter(Context context, List<String> data, int layoutResId) {
        super(context, data, layoutResId);
    }


    @Override
    protected void onBind(int viewType, BaseViewHolder holder, final int position, String item) {
        holder.setText(R.id.group_name,item);
        holder.getView(R.id.new_contrainer).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SwitchGroupEvent event = new SwitchGroupEvent(position);
                EventBus.getDefault().post(event);
            }
        });
    }
}
