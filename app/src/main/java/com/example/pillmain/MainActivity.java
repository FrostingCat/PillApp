package com.example.pillmain;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pillmain.ui.BottomNavigationFragment;
import com.example.pillmain.ui.calendar.CalendarAdapter;
import com.example.pillmain.ui.calendar.MyCalendar;
import com.example.pillmain.ui.calendar.RecyclerTouchListener;
import com.example.pillmain.ui.calendar.myCalendarData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationFragment bottomNavigationFragment;
    private List<MyCalendar> calendarList= new ArrayList<>();
    private RecyclerView recyclerView;
    private CalendarAdapter mAdapter;
    private ImageView forward, reverse;
    private int currentposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.pill_plus);

        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

        // textView is the TextView view that should display it
        TextView textViewDate = findViewById(R.id.text_view_date);
        textViewDate.setText(currentDateTimeString);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new CalendarAdapter(calendarList);

        recyclerView.setHasFixedSize(true);

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentposition = getCurrentItem();

                int bottom = recyclerView.getAdapter().getItemCount()-1;
                if (bottom-currentposition <4)
                    currentposition=bottom-1;
                else
                    currentposition+=4;

                setCurrentItem(currentposition, 1);

            }
        });
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentposition = getCurrentItem();

                setCurrentItem(currentposition-5, 0);

            }
        });



        // horizontal RecyclerView

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);


        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView
                .addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView,
                                           int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        int totalItemCount = mLayoutManager.getChildCount();
                        for (int i = 0; i < totalItemCount; i++){
                            View childView = recyclerView.getChildAt(i);
                            TextView childTextView = (TextView) (childView.findViewById(R.id.day_1));
                            String childTextViewText = (String) (childTextView.getText());

                            if (childTextViewText.equals("Sun"))
                                childTextView.setTextColor(Color.RED);
                            else
                                childTextView.setTextColor(Color.BLACK);

                        }


                    }
                });
        recyclerView.setAdapter(mAdapter);


        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MyCalendar calendar = calendarList.get(position);
                TextView childTextView = (TextView) (view.findViewById(R.id.day_1));

                Animation startRotateAnimation = AnimationUtils.makeInChildBottomAnimation(getApplicationContext());
                childTextView.startAnimation(startRotateAnimation);
                childTextView.setTextColor(Color.CYAN);
                Toast.makeText(getApplicationContext(), calendar.getDay() + " is selected!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onLongClick(View view, int position) {
                MyCalendar calendar = calendarList.get(position);

                TextView childTextView = (TextView) (view.findViewById(R.id.day_1));
                childTextView.setTextColor(Color.GREEN);

                Toast.makeText(getApplicationContext(), calendar.getDate()+"/" + calendar.getDay()+"/" +calendar.getMonth()+"   selected!", Toast.LENGTH_SHORT).show();

            }
        }));

        prepareCalendarData();
    }

    private int getCurrentItem(){
        return ((LinearLayoutManager)recyclerView.getLayoutManager())
                .findLastCompletelyVisibleItemPosition();
    }

    private void setCurrentItem(int position, int incr){
        position=position+incr;

        if (position <0)
            position=0;

        recyclerView.smoothScrollToPosition(position);


    }

    /**
     * Prepares sample data to provide data set to adapter
     */
    @SuppressLint("NotifyDataSetChanged")
    private void prepareCalendarData() {

        // run a for loop for all the next 30 days of the current month starting today
        // initialize mycalendarData and get Instance
        // getnext to get next set of date etc.. which can be used to populate MyCalendarList in for loop

        myCalendarData m_calendar = new myCalendarData(-2);

        for ( int i=0; i <30; i++) {

            MyCalendar calendar = new MyCalendar(m_calendar.getWeekDay(), String.valueOf(m_calendar.getDay()), String.valueOf(m_calendar.getMonth()), String.valueOf(m_calendar.getYear()),i);
            m_calendar.getNextWeekDay(1);

            calendarList.add(calendar);

        }



        // notify adapter about data set changes
        // so that it will render the list with new data

        mAdapter.notifyDataSetChanged();

    Fragment bottom_fragment = getSupportFragmentManager().findFragmentById(R.id.bottom_navigation_id);
        if (bottom_fragment instanceof Fragment) {
            bottomNavigationFragment = (BottomNavigationFragment)bottom_fragment;
            bottomNavigationFragment.initializeComponents();
        }

    }

}