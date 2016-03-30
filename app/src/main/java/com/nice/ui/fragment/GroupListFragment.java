package com.nice.ui.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.nice.R;
import com.nice.adapter.GroupAdapter;
import com.nice.model.Event.SwitchGroupEvent;
import com.nice.model.NicetSheet;
import com.nice.model.NicetSheetQuestionGroup;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GroupListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "entity";
    @Bind(R.id.listview)
    ListView listview;
    private GroupAdapter groupAdapter;
    private NicetSheet entity;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param entity Parameter 2.
     * @return A new instance of fragment GroupListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupListFragment newInstance(NicetSheet entity) {
        GroupListFragment fragment = new GroupListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, entity);
        fragment.setArguments(args);
        return fragment;
    }

    public GroupListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            entity = (NicetSheet) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_list, container, false);
        ButterKnife.bind(this, view);
        if(null != entity) {
            groupAdapter = new GroupAdapter(getActivity(), getGroupNmae(), R.layout.item_group_list);
            listview.setAdapter(groupAdapter);
        }
        return view;
    }

    public List<String> getGroupNmae() {
        for(int i =0;i<entity.SheetQuestionGroup.size();i++){
            for(int j = i+1;j<entity.SheetQuestionGroup.size();j++){
                if(entity.SheetQuestionGroup.get(i).qgSequence<entity.SheetQuestionGroup.get(j).qgSequence){
                    NicetSheetQuestionGroup temp = entity.SheetQuestionGroup.get(i);
                    entity.SheetQuestionGroup.set(i,entity.SheetQuestionGroup.get(j));
                    entity.SheetQuestionGroup.set(j,temp);
                }
            }
        }
        List<String> list = new ArrayList();
        for (NicetSheetQuestionGroup group : entity.SheetQuestionGroup) {
            list.add(group.qgName);

        }
        System.out.println(list.size()+"jiaojiabin");
        List<String> list1 = new ArrayList();
        for (int i =0;i<list.size();i++){
            list1.add(list.get(list.size()-1-i));
        }
        return list1;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
