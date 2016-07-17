package com.nice.ui.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nice.NiceApplication;
import com.nice.R;
import com.nice.adapter.QuestionContextAdapter;
import com.nice.httpapi.response.dataparser.NiceValuePaser;
import com.nice.model.NIcetSheetQuestion;
import com.nice.model.NiceValue;
import com.nice.model.NicetSheetQuestionGroup;
import com.nice.ui.QuestionContextActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExamFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SHID = "shId";
    private static final String ARG_PARAM1 = "group";
    private static final String ISLASTGROUP = "isLastGroup";
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private QuestionContextAdapter adapter;

    private DatePickerDialog datePickerDialog;

    private NicetSheetQuestionGroup group;
    private long shId;
    private int isLastGroup = 0;

    public ExamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment ExamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExamFragment newInstance(long shId, NicetSheetQuestionGroup param1, int isLastGroup) {
        ExamFragment fragment = new ExamFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putLong(SHID, shId);
        args.putInt(ISLASTGROUP, isLastGroup);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            group = (NicetSheetQuestionGroup) getArguments().getSerializable(ARG_PARAM1);
            isLastGroup = getArguments().getInt(ISLASTGROUP);
            shId = getArguments().getLong(SHID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_exam, container, false);
        ButterKnife.bind(this, view);

        initLayout();

        return view;
    }

    private void initLayout() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        for(int i =1;i<group.SheetQuestion.size();i++){
            for(int j = 0;j<group.SheetQuestion.size();j++){
                if(group.SheetQuestion.get(i).sqSequence<group.SheetQuestion.get(j).sqSequence){
                    NIcetSheetQuestion temp = group.SheetQuestion.get(i);
                    group.SheetQuestion.set(i,group.SheetQuestion.get(j));
                    group.SheetQuestion.set(j,temp);
                }
            }
        }
        adapter = new QuestionContextAdapter(((QuestionContextActivity) getActivity()).entity, shId, group.qgId, getNiceValue(), group.qgName, isLastGroup, group.SheetQuestion, getActivity()
                , R.layout.item_signleselect //	单项选择题 0
                , R.layout.item_selectinstruction//单选说明题 1
                , R.layout.view_completion_normal//简答题 2
                , R.layout.item_signname//签名 3
                , R.layout.item_takephoto//上传图片 4
                , R.layout.item_multiple_select//多选 5
                , R.layout.item_bottom_btn//底部按钮 6
                , R.layout.item_group_name//分组名称 7
        );
        recyclerView.setAdapter(adapter);
    }

    private NiceValue getNiceValue() {
        String value = NiceApplication.instance()
                .getQuestValuePreferencesQuest()
                .getString(String.valueOf(shId) + String.valueOf(group.qgId), null);
        if(TextUtils.isEmpty(value)) return null;
        try {
            return NiceValuePaser.paser2NiceValue(new JSONObject(value));
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void notifyDateChange() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        ((QuestionContextActivity) getActivity()).isLastGroup = 0;
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public boolean saveValues(){
        return adapter.saveValues();
    }

    public void addValue(String sqId, String value){
        adapter.addValue(sqId, value);
    }

    public boolean saveIsEnable(){
        return adapter.saveIsEnable();
    }
}
