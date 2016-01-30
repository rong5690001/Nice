package com.nice.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nice.R;
import com.nice.model.NIcetSheetQuestion;
import com.nice.model.NiceSheetQuestionOption;
import com.nice.util.Denisty;
import com.nice.widget.NiceEditText;
import com.nice.widget.NiceImageView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by chen on 2016/1/24.
 */
public class QuestionContextAdapter extends AbsAdapter<NIcetSheetQuestion> {

    private Map<Long, String> selectedValues = new HashMap<>();
    private Map<Integer, List<NiceImageView>> selectImageViewMap = new HashMap<>();
    private DatePickerDialog datePickerDialog;

    public QuestionContextAdapter(@NonNull List<NIcetSheetQuestion> datas, Context context, int... layoutId) {
        super(datas, context, layoutId);
    }

    @Override
    public int getItemViewType(int position) {
        if (datas.get(position).sqType == 400600000000007L) {//单项选择题
            return 0;
        }
        if (datas.get(position).sqType == 400600000000009L) {//单选说明题
            return 1;
        }
        if (datas.get(position).sqType == 4006000000000010L) {//签名
            return 3;
        }
        if (datas.get(position).sqType == 400600000000004L) {//上传图片
            return 4;
        }
        if (datas.get(position).sqType == 400600000000008L) {//多选
            return 5;
        }
        return 2;
    }

    @Override
    public void onBindViewHolder(AbsViewHolder holder, final int position) {
        if (datas.get(position).sqType == 400600000000007L) {//单项选择题
            onBindViewHolder_singleSelected(holder, position);
            return;
        }
        if (datas.get(position).sqType == 400600000000009L) {//单选说明题
            onBindViewHolder_selectinStruction(holder, position);
            return;
        }
        if (datas.get(position).sqType == 4006000000000010L) {//签名
            return;
        }
        if (datas.get(position).sqType == 400600000000004L) {//上传图片
            return;
        }
        if (datas.get(position).sqType == 400600000000008L) {//多选
            onBindViewHolder_multipeSelect(holder, position);
            return;
        }
        onBindViewHolder_completion(holder, position);

    }

    /**
     * 选择说明题
     *
     * @param holder
     * @param position
     */
    private void onBindViewHolder_selectinStruction(final AbsViewHolder holder, final int position) {
        final long id = datas.get(position).sqId;
        holder.setText(R.id.title, datas.get(position).sqTitle + ":");
        final String ok = datas.get(position).SheetQuestionOption.get(0).qoValue;
        final String not = datas.get(position).SheetQuestionOption.get(1).qoValue;
        if (ok.equals(selectedValues.containsKey(position) ? selectedValues.get(position) : null)) {
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
                selectedValues.put(id, ok);
            }
        });

        holder.getView(R.id.muti_selected_layout_not).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.getView(R.id.muti_selected_not_image).setSelected(true);
                holder.getView(R.id.muti_selected_layout_not_editText).setVisibility(View.VISIBLE);
                holder.getView(R.id.muti_selected_layout_ok).setSelected(false);
                holder.getView(R.id.muti_selected_layout_ok_editText).setVisibility(View.GONE);
                selectedValues.put(id, not);
            }
        });

    }

    /**
     * 单选
     *
     * @param holder
     * @param position
     */
    private void onBindViewHolder_singleSelected(AbsViewHolder holder, final int position) {
        final long id = datas.get(position).sqId;
        holder.setText(R.id.title, datas.get(position).sqTitle + ":");
        final LinearLayout linearLayout = holder.getView(R.id.signleselect_radio);
        linearLayout.removeAllViews();
        List<NiceImageView> imageViewsTemp = new ArrayList<>();
        for (int i = 0; i < datas.get(position).SheetQuestionOption.size(); i++) {
            final NiceSheetQuestionOption option = datas.get(position).SheetQuestionOption.get(i);
            final View view = View.inflate(context, R.layout.selected_single, null);
            final NiceImageView imageView = (NiceImageView) view.findViewById(R.id.item_new_question_choose_btn);
            imageView.setSelected(option.qoValue.equals(selectedValues.containsKey(position) ? selectedValues.get(position) : null));
            ((TextView) view.findViewById(R.id.item_new_question_name)).setText(option.qoText);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(id + ":" + option.qoValue);
                    selectedValues.put(id, option.qoValue);
                    for (NiceImageView imageView1 : selectImageViewMap.get(position)) {
                        imageView1.setSelected(false);
                    }
                    imageView.setSelected(true);
                }
            });
            imageViewsTemp.add(imageView);
            linearLayout.addView(view);
        }
        selectImageViewMap.put(position, imageViewsTemp);
    }

    /**
     * 多选
     *
     * @param holder
     * @param position
     */
    private void onBindViewHolder_multipeSelect(AbsViewHolder holder, final int position) {
        final long id = datas.get(position).sqId;
        holder.setText(R.id.title, datas.get(position).sqTitle + ":");
        final LinearLayout linearLayout = holder.getView(R.id.multiple_select_radio);
        linearLayout.removeAllViews();
        List<NiceImageView> imageViewsTemp = new ArrayList<>();
        for (int i = 0; i < datas.get(position).SheetQuestionOption.size(); i++) {
            final NiceSheetQuestionOption option = datas.get(position).SheetQuestionOption.get(i);
            final View view = View.inflate(context, R.layout.selected_multiple, null);
            NiceImageView imageView = (NiceImageView) view.findViewById(R.id.item_new_question_choose_btn);
            if (selectImageViewMap.containsKey(position)) {
                for (NiceImageView imageView1 : selectImageViewMap.get(position)) {
                    if (imageView1.isSelected()) {
                        imageView.setSelected(true);
                    }
                }
            }
//            imageView.setSelected(option.qoValue.equals(selectedValues.containsKey(position)
//                    ? selectedValues.get(position) : null));
            final int tag = i;
            ((TextView) view.findViewById(R.id.item_new_question_name)).setText(option.qoText);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(id + ":" + tag);
                    selectedValues.put(id, option.qoValue);
                    v.setSelected(!v.isSelected());
                }
            });
            imageViewsTemp.add(imageView);
            linearLayout.addView(view);
        }
        selectImageViewMap.put(position, imageViewsTemp);
    }

    /**
     * 填空题
     *
     * @param holder
     * @param position
     */
    private void onBindViewHolder_completion(AbsViewHolder holder, final int position) {
        final long id = datas.get(position).sqId;
        holder.setText(R.id.title, datas.get(position).sqTitle + ":");
        NiceEditText editText = holder.getView(R.id.value);
        System.out.println("id:" + id +
                "/n" + (selectedValues.containsKey(id)
                ? selectedValues.get(id) : "") +
                "/n" + datas.get(position).sqType);
        editText.setText(selectedValues.containsKey(id)
                ? selectedValues.get(id) : "");
        editText.setMinHeight(Denisty.dip2px(context, 30));
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setOnTouchListener(null);
        if (datas.get(position).sqType == 400600000000002L) {//填空题(时间)
            editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    v.requestFocus();
                    showTimePicker(id);
                    return true;
                }
            });
        }
        if (datas.get(position).sqType == 400600000000003L) {//填空题(数字)
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }

        if (datas.get(position).sqType == 400600000000005L) {//简答题
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            editText.setMinHeight(Denisty.dip2px(context, 150));
        }

    }

    @Override
    public int getItemCount() {
        return null == datas ? 0 : datas.size();
    }

    /**
     * 显示时间控件
     */
    private void showTimePicker(final long id){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        if(null == datePickerDialog) {
            datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    selectedValues.put(id, year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    notifyDataSetChanged();
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        if(datePickerDialog.isShowing()) return;
        datePickerDialog.show();
    }

}
