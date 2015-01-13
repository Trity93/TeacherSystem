//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.1.
//


package com.slidingmenu.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import com.teachersystem.R.layout;
import org.androidannotations.api.BackgroundExecutor;
import org.androidannotations.api.builder.FragmentBuilder;
import org.androidannotations.api.view.HasViews;
import org.androidannotations.api.view.OnViewChangedListener;
import org.androidannotations.api.view.OnViewChangedNotifier;

public final class LeaveFragment_
    extends com.slidingmenu.fragment.LeaveFragment
    implements HasViews, OnViewChangedListener
{

    private final OnViewChangedNotifier onViewChangedNotifier_ = new OnViewChangedNotifier();
    private View contentView_;
    private Handler handler_ = new Handler(Looper.getMainLooper());

    @Override
    public void onCreate(Bundle savedInstanceState) {
        OnViewChangedNotifier previousNotifier = OnViewChangedNotifier.replaceNotifier(onViewChangedNotifier_);
        init_(savedInstanceState);
        super.onCreate(savedInstanceState);
        OnViewChangedNotifier.replaceNotifier(previousNotifier);
    }

    @Override
    public View findViewById(int id) {
        if (contentView_ == null) {
            return null;
        }
        return contentView_.findViewById(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView_ = super.onCreateView(inflater, container, savedInstanceState);
        if (contentView_ == null) {
            contentView_ = inflater.inflate(layout.fragment_leave, container, false);
        }
        return contentView_;
    }

    private void init_(Bundle savedInstanceState) {
        OnViewChangedNotifier.registerOnViewChangedListener(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onViewChangedNotifier_.notifyViewChanged(this);
    }

    public static LeaveFragment_.FragmentBuilder_ builder() {
        return new LeaveFragment_.FragmentBuilder_();
    }

    @Override
    public void onViewChanged(HasViews hasViews) {
        btnAddOvertime = ((Button) hasViews.findViewById(com.teachersystem.R.id.leave_btn_add));
        leaveMsg = ((ListView) hasViews.findViewById(com.teachersystem.R.id.leave_lv_table));
        if (btnAddOvertime!= null) {
            btnAddOvertime.setOnClickListener(new OnClickListener() {


                @Override
                public void onClick(View view) {
                    LeaveFragment_.this.addLeave();
                }

            }
            );
        }
        userIdget();
    }

    @Override
    public void updateListView() {
        handler_.post(new Runnable() {


            @Override
            public void run() {
                LeaveFragment_.super.updateListView();
            }

        }
        );
    }

    @Override
    public void updateWork() {
        BackgroundExecutor.execute(new BackgroundExecutor.Task("", 0, "") {


            @Override
            public void execute() {
                try {
                    LeaveFragment_.super.updateWork();
                } catch (Throwable e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }

        }
        );
    }

    public static class FragmentBuilder_
        extends FragmentBuilder<LeaveFragment_.FragmentBuilder_, com.slidingmenu.fragment.LeaveFragment>
    {


        @Override
        public com.slidingmenu.fragment.LeaveFragment build() {
            LeaveFragment_ fragment_ = new LeaveFragment_();
            fragment_.setArguments(args);
            return fragment_;
        }

    }

}
