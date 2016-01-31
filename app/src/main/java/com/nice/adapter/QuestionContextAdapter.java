package com.nice.adapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.nice.NiceApplication;
import com.nice.R;
import com.nice.model.Event.SqIdEvent;
import com.nice.model.NIcetSheetQuestion;
import com.nice.model.NiceSheetQuestionOption;
import com.nice.model.NiceValue;
import com.nice.ui.QuestionContextActivity;
import com.nice.ui.QuestionSignActivity;
import com.nice.util.BitmapUtil;
import com.nice.util.Denisty;
import com.nice.util.QuestionUtil;
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

    public Map<Long, String> selectedValues = new HashMap<>();
    public Map<Long, String> selectedStrutionValues = new HashMap<>();
    private Map<Long, List<NiceImageView>> singleSelectImageViewMap = new HashMap<>();
    private Map<Long, List<NiceEditText>> selectEditTextMap = new HashMap<>();
    private Map<Long, NiceEditText> editTextMap;
    private DatePickerDialog datePickerDialog;
    private boolean isLastGroup = false;
    private String groupName;
    private long shId;
    private long qgId;

    public QuestionContextAdapter(long shId, long qgId, NiceValue niceValue, String groupName, boolean isLastGroup, @NonNull List<NIcetSheetQuestion> datas, Context context, int... layoutId) {
        super(datas, context, layoutId);
        this.isLastGroup = isLastGroup;
        this.groupName = groupName;
        this.shId = shId;
        this.qgId = qgId;
        editTextMap = new HashMap<>();
        if (null != niceValue) {
            selectedValues = niceValue.selectedValues;
            selectedStrutionValues = niceValue.selectedStrutionValues;
        }
    }

    @Override
    public int getItemViewType(int index) {
        if (null != datas) {
            if (datas.size() + 1 == index)
                return 6;
            if (0 == index) {
                return 7;
            }
        }
        int position = index - 1;
        if (datas.get(position).sqType == 400600000000007L) {//单项选择题
            return 0;
        }
        if (datas.get(position).sqType == 400600000000009L) {//单选说明题
            return 1;
        }
        if (datas.get(position).sqType == 400600000000010L) {//签名
            System.out.println("签名");
            return 3;
        }
        if (datas.get(position).sqType == 400600000000004L) {//上传图片
            return 4;
        }
        if (datas.get(position).sqType == 400600000000008L) {//多选
            return 5;
        }
        return 2;//填空
    }

    @Override
    public void onBindViewHolder(AbsViewHolder holder, final int index) {
        if (null != datas) {
            if (datas.size() + 1 == index) {
                onBindViewHolder_bottom_btn(holder, index);
                return;
            }
            if (0 == index) {
                onBindViewHolder_group_name(holder, index);
                return;
            }
        }
        int position = index - 1;
        Log.e("11", "sqType:" + datas.get(position).sqType);
        if (datas.get(position).sqType == 400600000000007L) {//单项选择题
            onBindViewHolder_singleSelected(holder, position);
            return;
        }
        if (datas.get(position).sqType == 400600000000009L) {//单选说明题
            onBindViewHolder_selectinStruction(holder, position);
            return;
        }
        if (datas.get(position).sqType == 400600000000010L) {//签名
            onBindViewHolder_sign(holder, position);
            return;
        }
        if (datas.get(position).sqType == 400600000000004L) {//上传图片
            onBindViewHolder_upload_image(holder, position);
            return;
        }
        if (datas.get(position).sqType == 400600000000008L) {//多选
            onBindViewHolder_multipeSelect(holder, position);
            return;
        }
        onBindViewHolder_completion(holder, position);//填空题

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
        final LinearLayout linearLayout = holder.getView(R.id.muti_selected_layout);
        linearLayout.removeAllViews();
        List<NiceImageView> imageViewsTemp = new ArrayList<>();
        List<NiceEditText> editTextViewsTemp = new ArrayList<>();
        for (int i = 0; i < datas.get(position).SheetQuestionOption.size(); i++) {
            final NiceSheetQuestionOption option = datas.get(position).SheetQuestionOption.get(i);
            final View view = View.inflate(context, R.layout.option_selectinstruction, null);
            final NiceImageView imageView = (NiceImageView) view.findViewById(R.id.muti_selected_image);
            final NiceEditText editText = (NiceEditText) view.findViewById(R.id.muti_selected_layout_editText);

            imageView.setSelected(option.qoValue.equals(selectedValues.containsKey(id) ? selectedValues.get(id) : null));
            ((TextView) view.findViewById(R.id.name)).setText(option.qoText);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(id + ":" + option.qoValue);
                    selectedValues.put(id, option.qoValue);
                    int viewIndex = 0;
                    for (NiceImageView imageView1 : singleSelectImageViewMap.get(id)) {
                        imageView1.setSelected(false);
                        selectEditTextMap.get(id).get(viewIndex).setVisibility(View.GONE);
                        ++viewIndex;
                    }
                    imageView.setSelected(true);
                    editText.setVisibility(View.VISIBLE);

                }
            });
            imageViewsTemp.add(imageView);
            editTextViewsTemp.add(editText);
            linearLayout.addView(view);
        }
        singleSelectImageViewMap.put(id, imageViewsTemp);
        selectEditTextMap.put(id, editTextViewsTemp);
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
            imageView.setSelected(option.qoValue.equals(selectedValues.containsKey(id) ? selectedValues.get(id) : null));
            ((TextView) view.findViewById(R.id.item_new_question_name)).setText(option.qoText);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println(id + ":" + option.qoValue);
                    selectedValues.put(id, option.qoValue);
                    for (NiceImageView imageView1 : singleSelectImageViewMap.get(id)) {
                        imageView1.setSelected(false);
                    }
                    imageView.setSelected(true);
                }
            });
            imageViewsTemp.add(imageView);
            linearLayout.addView(view);
        }
        singleSelectImageViewMap.put(id, imageViewsTemp);
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
            if (singleSelectImageViewMap.containsKey(id)) {
                for (NiceImageView imageView1 : singleSelectImageViewMap.get(id)) {
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
        singleSelectImageViewMap.put(id, imageViewsTemp);
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
        final NiceEditText editText = holder.getView(R.id.value);
        System.out.println("id:" + id +
                "/n" + (selectedValues.containsKey(id)
                ? selectedValues.get(id) : "") +
                "/n" + datas.get(position).sqType);
        if(selectedValues.containsKey(id) && !editTextMap.containsKey(id)) {
            editText.setText(selectedValues.get(id));
        }
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

        editTextMap.put(id, editText);
    }

    /**
     * 上一组|下一组
     *
     * @param holder
     * @param position
     */
    private void onBindViewHolder_bottom_btn(AbsViewHolder holder, final int position) {

        holder.getView(R.id.info_back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post("reduceGroupIndex");
            }
        });
        Log.e("11", "isLastGroup:" + isLastGroup);
        if (isLastGroup) {
            holder.setText(R.id.info_go_btn, "提交");
            holder.getView(R.id.info_go_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String shIdAndqgId = String.valueOf(shId) + String.valueOf(qgId);
                    boolean isSaved = QuestionUtil.saveValue(new NiceValue(shIdAndqgId, selectedValues, selectedStrutionValues));
                    if (!isSaved) {
                        Toast.makeText(NiceApplication.instance(), "保存本地失败", Toast.LENGTH_SHORT).show();
                    } else {
                        saveEditTextValue();
                        Toast.makeText(NiceApplication.instance(), "保存成功", Toast.LENGTH_SHORT).show();
                        ((Activity) context).finish();
                    }
                }
            });
        } else {
            holder.getView(R.id.info_go_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveEditTextValue();
                    String shIdAndqgId = String.valueOf(shId) + String.valueOf(qgId);
                    boolean isSaved = QuestionUtil.saveValue(new NiceValue(shIdAndqgId, selectedValues, selectedStrutionValues));
                    if (isSaved) {
                        Toast.makeText(NiceApplication.instance(), "保存成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post("addGroupIndex");
                    } else {
                        Toast.makeText(NiceApplication.instance(), "保存本地失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * 分组名称
     *
     * @param holder
     * @param position
     */
    private void onBindViewHolder_group_name(AbsViewHolder holder, final int position) {
        holder.setText(R.id.group_name, groupName);
    }

    /**
     * 签名
     *
     * @return
     */
    private void onBindViewHolder_sign(AbsViewHolder holder, final int position) {
        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionSignActivity.class);
                intent.putExtra("sqId", datas.get(position).sqId);
                context.startActivity(intent);
            }
        });
    }

    /**
     * 上传图片
     *
     * @param holder
     * @param position
     */
    private void onBindViewHolder_upload_image(AbsViewHolder holder, final int position) {
        final long sqId = datas.get(position).sqId;
        int imageIndex = -1;
        SharedPreferences preferences = NiceApplication.instance().getQuestValuePreferencesQuest();
        NiceImageView[] imageViews = new NiceImageView[3];
        imageViews[0] = holder.getView(R.id.photo1);
        imageViews[1] = holder.getView(R.id.photo2);
        imageViews[2] = holder.getView(R.id.photo3);
        holder.getView(R.id.take_photo).setOnClickListener(null);
        for (int i = 2; i >= 0; i--) {//初始化图片
            imageViews[i].setVisibility(View.GONE);
            String filename = preferences.getString(sqId + "," + i, null);
            if (TextUtils.isEmpty(filename)) {
                imageIndex = i;
            } else {
                final int imageIndexFinal = i;
                imageViews[i].setImageBitmap(BitmapUtil.file2Bitmap(filename));
                imageViews[i].setVisibility(View.VISIBLE);
                imageViews[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        System.out.println("imageIndexFinal:" + imageIndexFinal);
                        EventBus.getDefault().post(new SqIdEvent(String.valueOf(sqId) + "," + imageIndexFinal));
                        ((QuestionContextActivity) context).startActivityForResult(intent, 1);
                    }
                });
            }
        }

        if (imageIndex != -1) {
            final int imageIndexFinal = imageIndex;
            holder.getView(R.id.take_photo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    EventBus.getDefault().post(new SqIdEvent(String.valueOf(sqId) + "," + imageIndexFinal));
                    ((QuestionContextActivity) context).startActivityForResult(intent, 1);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return null == datas ? 0 : datas.size() + 2;
    }

    /**
     * 显示时间控件
     */
    private void showTimePicker(final long id) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        if (null == datePickerDialog) {
            datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    selectedValues.put(id, year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    notifyDataSetChanged();
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        if (datePickerDialog.isShowing()) return;
        datePickerDialog.show();
    }

    private void saveEditTextValue(){
        for(long id : editTextMap.keySet()){
            System.out.println("text:" + editTextMap.get(id).getText().toString());
            selectedValues.put(id, editTextMap.get(id).getText().toString());
        }
    }

}
