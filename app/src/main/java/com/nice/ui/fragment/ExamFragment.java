package com.nice.ui.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nice.R;
import com.nice.adapter.QuestionContextAdapter;
import com.nice.model.NicetSheetQuestionGroup;
import com.nice.ui.QuestionContextActivity;
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
    private static final String ARG_PARAM1 = "group";
    private static final String ISLASTGROUP = "isLastGroup";
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private DatePickerDialog datePickerDialog;

    private NicetSheetQuestionGroup group;
    private boolean isLastGroup = false;

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
    public static ExamFragment newInstance(NicetSheetQuestionGroup param1, boolean isLastGroup) {
        ExamFragment fragment = new ExamFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putBoolean(ISLASTGROUP, isLastGroup);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            group = (NicetSheetQuestionGroup) getArguments().getSerializable(ARG_PARAM1);
            isLastGroup = getArguments().getBoolean(ISLASTGROUP);
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
        QuestionContextAdapter adapter = new QuestionContextAdapter(group.qgName, isLastGroup, group.SheetQuestion, getActivity()
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

    @Override
    public void onDestroyView() {
        ((QuestionContextActivity) getActivity()).isLastGroup = false;
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
