package com.example.ehk.hospital;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private DatePicker datePicker;
    private TextView dateText;
    private Button searchBtn;
    private String date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(this);
        datePicker = findViewById(R.id.datePicker);
        dateText = findViewById(R.id.dateTextView);
        datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), new DatePicker.OnDateChangedListener() {

            //값이 바뀔 때마다 텍스트 뷰 값 변경
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                dateText.setText(String.format("%d년 %d월 %d일",year,month+1,day));
            }
        });

        dateText.setText(String.format("%d년 %d월 %d일",datePicker.getYear(), datePicker.getMonth()+1, datePicker.getDayOfMonth()));
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,ReservationActivity.class);
        date = String.format("%d년 %d월 %d일",datePicker.getYear(),datePicker.getMonth()+1,datePicker.getDayOfMonth());
        intent.putExtra("날짜",String.valueOf(date));

        if (TextUtils.isEmpty(dateText.getText())) {
            Toast toast = Toast.makeText(this,"예약 날짜를 선택하세요!",Toast.LENGTH_SHORT);
            View toastView = toast.getView();
            toastView.setBackgroundResource(R.color.toast);//색변경
            toast.show();
            Log.d("미선택","미선택");
        } else {
            startActivity(intent);
        }
    }
}
