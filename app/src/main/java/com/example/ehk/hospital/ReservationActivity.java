package com.example.ehk.hospital;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ehk on 2018-01-12.
 */

public class ReservationActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPrefs prefs;
    private String date; //main 예약날짜 받을 변수
    private TextView dateText, textView_H, textView_M; //예약날짜 dataText / 시간 / 분
    private Button[] hbuttons = new Button[12]; //시간 버튼
    private Integer[] hbuttonIds = {R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11, R.id.btn12}; // 시간버튼 id
    private Button[] mbuttons = new Button[12];//분 버튼
    private Integer[] mbuttonIds = {R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9, R.id.button10, R.id.button11}; //분 버튼 id
    private ArrayList<Integer> hList = new ArrayList<>(); //시간 리스트
    private ArrayList<Integer> mList = new ArrayList<>();//분 리스트
    private List<Reservation> reservations;
    final int MAX_PERSON = 5;
    private Button selectedHButon ,topLeftBtn , topRightBtn , bottomRightBtn ,bottomLeftBtn;
    private Button selectedMButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        prefs = SharedPrefs.getInstance(getApplicationContext());

        reservations = prefs.getReservations();
        for (Reservation r : reservations) {
            Log.d("date", r.date);
            Log.d("hour", r.hour);
            Log.d("min", r.min);
        }
        Intent intent = new Intent(this.getIntent());
        date = intent.getStringExtra("날짜");
        dateText = findViewById(R.id.dateTextView);
        dateText.setText(date);

        textView_H = findViewById(R.id.textView_H);
        int index = hbuttons.length;
        for (int i = 0; i < index; i++) {
            hbuttons[i] = findViewById(hbuttonIds[i]);
            hbuttons[i].setOnClickListener(this);
            hbuttons[i].setTag(i);
            hList.add(i + 1);
        }
        textView_M = findViewById(R.id.textView_M);
        int index2 = mbuttons.length;
        for (int i = 0; i < index2; i++) {
            mbuttons[i] = findViewById(mbuttonIds[i]);
            mbuttons[i].setOnClickListener(this);
            mbuttons[i].setTag(i);
            mList.add(i * 5);
        }
    }
    public void onClick(View view) {
        Button newButton = (Button) view;
        for (Button hbutton : hbuttons) { // 시간버튼
            if (hbutton == newButton) {
                selectedHButon = hbutton;
                int position = (Integer) view.getTag();
                textView_H.setText(String.valueOf(hList.get(position)) + "시");
                for(int i = 0; i< mbuttons.length ; i++){
                    Button mbutton = mbuttons[i];
                    //갯수 체크 색 변경
                    final String date = dateText.getText().toString();
                    final String hour = hbutton.getText().toString();
                    final String min = mbutton.getText().toString();
                    Log.d("date", date + "," + hour + "," + min);
                    Log.d("reservation", String.valueOf(remainCount(date, hour, min)));

                    if (isReservation(date, hour, min)) { // 가능하면
                        switch (i){
                            case 0:
                                mbutton.setBackgroundResource(R.drawable.selector_right_mbtn);
                                break;
                            case 1:
                                mbutton.setBackgroundResource(R.drawable.selector_round_topright);
                                topRightBtn = findViewById(R.id.topRightBtn);
                                topRightBtn.setBackgroundColor(getResources().getColor(R.color.white));
                                break;
                            case 2:
                                mbutton.setBackgroundResource(R.drawable.selector_topbottom_mbtn);
                                break;
                            case 3:
                                mbutton.setBackgroundResource(R.drawable.selector_bottom_mbtn);
                                break;
                            case 4:
                                mbutton.setBackgroundResource(R.drawable.selector_round_bottomright);
                                bottomRightBtn = findViewById(R.id.bottomRightBtn);
                                bottomRightBtn.setBackgroundColor(getResources().getColor(R.color.white));
                                break;
                            case 5:
                                mbutton.setBackgroundResource(R.drawable.selector_right_mbtn);
                                break;
                            case 6:
                                mbutton.setBackgroundResource(R.drawable.selector_side_mbtn);
                                break;
                            case 7:
                                mbutton.setBackgroundResource(R.drawable.selector_round_bottomleft);
                                bottomLeftBtn = findViewById(R.id.bottomLeftBtn);
                                bottomLeftBtn.setBackgroundColor(getResources().getColor(R.color.white));
                                break;
                            case 8:
                                mbutton.setBackgroundResource(R.drawable.selector_bottom_mbtn);
                                break;
                            case 9:
                                mbutton.setBackgroundResource(R.drawable.selector_topbottom_mbtn);
                                break;
                            case 10:
                                mbutton.setBackgroundResource(R.drawable.selector_round_topleft);
                                topLeftBtn = findViewById(R.id.topLeftBtn);
                                topLeftBtn.setBackgroundColor(getResources().getColor(R.color.white));
                                break;
                            case 11:
                                mbutton.setBackgroundResource(R.drawable.selector_side_mbtn);
                                break;
                        }
                    }
                    else {
                        mbutton.setBackgroundResource(R.drawable.selector_btn);
                        switch (i){
                            case 0:
                                mbutton.setBackgroundResource(R.drawable.max_shape_right);
                                break;
                            case 1:
                                mbutton.setBackgroundResource(R.drawable.max_selector_round_topright);
                                topRightBtn = findViewById(R.id.topRightBtn);
                                topRightBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                break;
                            case 2:
                                mbutton.setBackgroundResource(R.drawable.max_shape_topbottom);
                                break;
                            case 3:
                                mbutton.setBackgroundResource(R.drawable.max_shape_bottom);
                                break;
                            case 4:
                                mbutton.setBackgroundResource(R.drawable.max_selector_round_bottomright);
                                bottomRightBtn = findViewById(R.id.bottomRightBtn);
                                bottomRightBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                break;
                            case 5:
                                mbutton.setBackgroundResource(R.drawable.max_shape_right);
                                break;
                            case 6:
                                mbutton.setBackgroundResource(R.drawable.max_shape_side);
                                break;
                            case 7:
                                mbutton.setBackgroundResource(R.drawable.max_selector_round_bottomleft);
                                bottomLeftBtn = findViewById(R.id.bottomLeftBtn);
                                bottomLeftBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                break;
                            case 8:
                                mbutton.setBackgroundResource(R.drawable.max_shape_bottom);
                                break;
                            case 9:
                                mbutton.setBackgroundResource(R.drawable.max_shape_topbottom);
                                break;
                            case 10:
                                mbutton.setBackgroundResource(R.drawable.max_selector_round_topleft);
                                topLeftBtn = findViewById(R.id.topLeftBtn);
                                topLeftBtn.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                                break;
                            case 11:
                                mbutton.setBackgroundResource(R.drawable.max_shape_side);
                                break;
                        }
                    }
                }
                Log.d("1", "시간");
            }
        }
        for (Button mbutton : mbuttons) { // 분버튼
            if (mbutton == newButton) {
                selectedMButton = mbutton;
                int position = (Integer) view.getTag();
                textView_M.setText(String.valueOf(mList.get(position)) + "분");
                Log.d("2", "분");
            }
        }
    }

    public void reserve(View v) { // (reserveBtn )예약 완료버튼 클릭 시 시간과 분 미선택 시 예약 완료 못하게
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (selectedHButon == null || selectedMButton == null) {
            Toast toast = Toast.makeText(this, "예약 시간을 선택하세요!", Toast.LENGTH_SHORT);
            View toastView = toast.getView();
            toastView.setBackgroundResource(R.color.toast);//색변경
            toast.show();
            Log.d("미선택", "미선택");
        } else {//팝업
            final String date = dateText.getText().toString();
            final String hour = selectedHButon.getText().toString();
            final String min = selectedMButton.getText().toString();
            // 예약 가능한 상태인지 조회
            boolean b = isReservation(date, hour, min);
            if (b) {
                builder.setTitle("예약 현황")
                        .setMessage(String.valueOf(MAX_PERSON - remainCount(date, hour, min)) + "명 예약 가능, 예약하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                reservations.add(new Reservation(date, hour, min));
                                prefs.setReservations(reservations);
                                Toast.makeText(getApplicationContext(), "예약이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        });
                builder.show();
            } else {
                builder.setTitle("예약 현황")
                        .setMessage("예약불가능")
                        .setCancelable(false)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                builder.show();
            }
        }
    }

    private int remainCount(String date, String hour, String min) {
        int count = 0;
        for (Reservation reservation : reservations) {
            if (reservation.date.equals(date) &&
                    reservation.hour.equals(hour) &&
                    reservation.min.equals(min)) {
                count++;
            }
        }
        return count;
    }

    private boolean isReservation(String date, String hour, String min) {
        return remainCount(date, hour, min) < MAX_PERSON;
    }

    public void topLeft(View v) {//topLeft 버튼 클릭시 50분
        if (v.isPressed()) {
            mbuttons[10].performClick();
        }
    }

    public void bottomLeft(View v) {// bottomLeft 버튼 클릭시 35분
        if (v.isPressed()) {
            mbuttons[7].performClick();
        }
    }

    public void bottomRight(View v) {//bottomRight 버튼 클릭시 20분
            mbuttons[4].performClick();
    }

    public void topRight(View v) {//topRight 버튼 클릭시 5분
        if (v.isPressed()) {
            mbuttons[1].performClick();
        }
    }
}