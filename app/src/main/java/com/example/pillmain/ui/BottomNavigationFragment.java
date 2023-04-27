package com.example.pillmain.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pillmain.MainActivity;
import com.example.pillmain.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class BottomNavigationFragment extends Fragment implements View.OnClickListener{

    View view;
    RelativeLayout home_rl, account_rl;
    ImageView iv_1, iv_3;
    FloatingActionButton floating_btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getChildFragmentManager();
        view = inflater.inflate(R.layout.include_bottom_toolbar, container, false);
        return view;
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeComponents();
    }

    public void initializeComponents() {
        if (getView() == null) return;
        home_rl = getView().findViewById(R.id.home_rl);
        account_rl = getView().findViewById(R.id.account_rl);
        floating_btn = getView().findViewById(R.id.floating_btn);

        iv_1 = getView().findViewById(R.id.iv_1);
        iv_3 = getView().findViewById(R.id.iv_3);

        home_rl.setOnClickListener(this);
        account_rl.setOnClickListener(this);
        floating_btn.setOnClickListener(this);

        setBottomNavigationView();
    }

    private void setBottomNavigationView() {
        int blue_color = getActivity().getResources().getColor(R.color.home_button_main);
        int grey_color = getActivity().getResources().getColor(R.color.background);
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            iv_1.setColorFilter(blue_color);
            iv_3.setColorFilter(grey_color);
        } else if (getActivity() != null && getActivity() instanceof AccountActivity) {
            iv_1.setColorFilter(grey_color);
            iv_3.setColorFilter(blue_color);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        getChildFragmentManager();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.home_rl:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.account_rl:
                Intent account_intent = new Intent(getActivity(), AccountActivity.class);
                getActivity().startActivity(account_intent);
                break;
            case R.id.floating_btn:
                Intent pills_intent = new Intent(getActivity(), PillsActivity.class);
                getActivity().startActivity(pills_intent);
                break;        }
    }
}
