package com.example.easycare.Extra;

import android.app.Dialog;
import android.view.View;
import android.widget.RelativeLayout;

import com.allattentionhere.fabulousfilter.AAH_FabulousFragment;
import com.example.easycare.R;

public class MyFabulousFragment extends AAH_FabulousFragment {
    public static MyFabulousFragment newInstance() {
        MyFabulousFragment f = new MyFabulousFragment();
        return f;
    }
    @Override

    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(), R.layout.filter_sample_view, null);
        RelativeLayout rl_content = (RelativeLayout) contentView.findViewById(R.id.rl_content);
        /*LinearLayout ll_buttons = (LinearLayout) contentView.findViewById(R.id.ll_buttons);
        contentView.findViewById(R.id.btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFilter("closed");
            }
        });*/

        //params to set
        setViewMain(rl_content); //necessary; main bottomsheet view
        setMainContentView(contentView); // necessary; call at end before super
        super.setupDialog(dialog,R.style.BottomSheetDialog); //call super at last
    }
}
