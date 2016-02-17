package com.nice.adapter;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.nice.R;
import com.nice.model.NicetSheet;
import com.nice.widget.NiceImageView;

import org.byteam.superadapter.list.BaseViewHolder;
import org.byteam.superadapter.list.SuperAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by jiao on 2016/1/21.
 */
public class UploadQuestionAdapter extends SuperAdapter<NicetSheet> {


    public UploadQuestionAdapter(Context context, List<NicetSheet> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    protected void onBind(int viewType, BaseViewHolder holder, final int position, final NicetSheet item) {
        holder.setText(R.id.item_upload_question_name, item.shName);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        try {
            holder.setText(R.id.item_upload_question_time, format.format(format.parse(item.rbTime)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
