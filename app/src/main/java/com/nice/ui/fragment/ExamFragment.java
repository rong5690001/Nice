package com.nice.ui.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.nice.NiceApplication;
import com.nice.R;
import com.nice.adapter.QuestionContextAdapter;
import com.nice.model.NicetSheetQuestionGroup;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExamFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "group";
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    private DatePickerDialog datePickerDialog;

    private NicetSheetQuestionGroup group;

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
    public static ExamFragment newInstance(NicetSheetQuestionGroup param1) {
        ExamFragment fragment = new ExamFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            group = (NicetSheetQuestionGroup) getArguments().getSerializable(ARG_PARAM1);
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
        QuestionContextAdapter adapter = new QuestionContextAdapter(group.SheetQuestion, getActivity()
                , R.layout.item_signleselect //	单项选择题
                , R.layout.item_selectinstruction//单选说明题
                , R.layout.view_completion_normal//简答题
                , R.layout.item_signname//签名
                , R.layout.item_takephoto//上传图片
                , R.layout.item_multiple_select//多选
        );
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEventMainThread(String event){
        if("showTime".equals(event)){
            System.out.println("时间_get");
            showTimePicker();
        }
    }

    private void showTimePicker(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        if(null == datePickerDialog) {
            datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        }
        if(datePickerDialog.isShowing()) return;
        datePickerDialog.show();
    }


}
