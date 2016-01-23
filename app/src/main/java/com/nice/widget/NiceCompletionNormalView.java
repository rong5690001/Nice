package com.nice.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import com.nice.R;
import butterknife.Bind;

/**
 * Created by chen on 2016/1/23.
 */
public class NiceCompletionNormalView extends RelativeLayout {

    @Bind(R.id.quest_name)
    NiceTextView questName;
    @Bind(R.id.quest_value)
    NiceEditText questValue;

    public NiceCompletionNormalView(Context context) {
        this(context, null);
    }

    public NiceCompletionNormalView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NiceCompletionNormalView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {

        View.inflate(context, R.layout.view_completion_normal, this);

    }


    public NiceTextView getQuestName() {
        return questName;
    }

    public NiceEditText getQuestValue() {
        return questValue;
    }

    public void setQuestName(String questName){
        if(null == questName) return;
        this.questName.setText(questName);
    }


}
