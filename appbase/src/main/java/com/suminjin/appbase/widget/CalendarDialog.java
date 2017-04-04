package com.suminjin.appbase.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.TextView;

import com.suminjin.appbase.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


/**
 * Created by jspark on 2016-03-14.
 */
public class CalendarDialog extends Dialog implements View.OnClickListener {
    private final Context context;
    private TextView txtCurrentMonth;
    private TextView txtSelectedDate;
    private View btnOk;

    private ArrayList<TextView> dayCellViewList = new ArrayList<>(); // day cell TextView 모음
    private int[] selectedDate; // 선택한 년월일 정보
    private View selectedDayCellView; // 선택한 view
    private int[] showYearMonth; // 현재 보여지는 년월 정보

    private View.OnClickListener defaultClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dismiss();
        }
    };

    public CalendarDialog(Context context) {
        super(context);
        this.context = context;
        initViews();
    }

    private void initViews() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        setContentView(R.layout.dialog_calendar);

        findViewById(R.id.btnOk).setOnClickListener(defaultClickListener);
        findViewById(R.id.btnCancel).setOnClickListener(defaultClickListener);

        findViewById(R.id.btnBack).setOnClickListener(this);
        findViewById(R.id.btnFirst).setOnClickListener(this);
        findViewById(R.id.btnLast).setOnClickListener(this);
        findViewById(R.id.btnNext).setOnClickListener(this);

        txtCurrentMonth = (TextView) findViewById(R.id.txtCurrentMonth);
        txtSelectedDate = (TextView) findViewById(R.id.txtSelectedDate);
        btnOk = findViewById(R.id.btnOk);

        // 달력 grid 안의 모든 TextView들의 onClickListener를 설정하고, arrayList에 더한다.
        GridLayout gridCalendar = (GridLayout) findViewById(R.id.gridCalendar);
        int count = gridCalendar.getChildCount();
        for (int i = 0; i < count; i++) {
            TextView dayCellView = (TextView) gridCalendar.getChildAt(i);
            dayCellView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int[] date = (int[]) v.getTag();
                    selectedDate = new int[]{date[0], date[1], date[2]};
                    setSelectedDateView(date);
                    // 이전 선택 cell을 unselected하고 현재 cell을 selected로.
                    selectedDayCellView.setSelected(false);
                    v.setSelected(true);
                    selectedDayCellView = v;
                }
            });
            dayCellViewList.add(dayCellView);
        }


        // 최초에는 오늘 날짜 설정
        Calendar todayCal = Calendar.getInstance();
        int year = todayCal.get(Calendar.YEAR);
        int month = todayCal.get(Calendar.MONTH);
        int day = todayCal.get(Calendar.DAY_OF_MONTH);
        selectedDate = new int[]{year, month, day};
        showYearMonth = new int[]{year, month};

        setCalendarDayCellViews();
        setSelectedDateView(selectedDate);
    }

    /**
     * 해당 년월의 처음 시작 요일과 마지막 날짜를 찾아서 day cell TextView들에 표시한다.
     */
    private void setCalendarDayCellViews() {
        clearDayCellText();

        int year = showYearMonth[0];
        int month = showYearMonth[1];

        setCalendarTitleView(year, month);

        // 이번 달의 1일의 요일과 마지막 날짜 찾기
        Calendar monthCal = Calendar.getInstance();
        monthCal.set(year, month, 1);
        int dayOfWeek = monthCal.get(Calendar.DAY_OF_WEEK);
        int lastDay = monthCal.getActualMaximum(Calendar.DAY_OF_MONTH);

        int startIndex = dayOfWeek - 1;
        int endIndex = startIndex + lastDay;
        for (int i = startIndex; i < endIndex; i++) {
            TextView txt = dayCellViewList.get(i);
            // set text
            int dayNum = i - startIndex + 1;
            txt.setText(Integer.toString(dayNum));
            // set tag
            int[] date = new int[]{year, month, dayNum};
            txt.setTag(date);
            // set selected date
            boolean isSelected = isSelectedDay(date);
            txt.setSelected(isSelected);
            if (isSelected) {
                selectedDayCellView = txt;
            }
        }
    }

    /**
     * 어떤 날짜가 선택된 날짜와 같은지 비교.
     * Calender나 Date 객체를 생성하지 않고 단순 integer 비교로 처리.
     *
     * @param date
     * @return
     */
    private boolean isSelectedDay(int[] date) {
        boolean result = true;
        for (int i = 0; i < date.length; i++) {
            if (date[i] != selectedDate[i]) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * 현재 보이는 year, month로 title view 설정
     *
     * @param year
     * @param month
     */
    private void setCalendarTitleView(int year, int month) {
        StringBuilder sb = new StringBuilder();
        Resources res = context.getResources();

        if (Locale.getDefault().getLanguage().equals("ko")) {
            sb.append(Integer.toString(year)).append(res.getString(R.string.year))
                    .append(Integer.toString(month + 1)).append(res.getString(R.string.month));
        } else {
            sb.append(Integer.toString(year)).append(" ").append(Integer.toString(month + 1));
        }

        txtCurrentMonth.setText(sb.toString());
    }

    /**
     * 선택된 year, month, day로 선택일자 view 설정하고,
     * ok 버튼에 날짜를 tagging 한다.
     *
     * @param date
     */
    private void setSelectedDateView(int[] date) {
        StringBuilder sb = new StringBuilder();
        int year = date[0];
        int month = date[1] + 1;
        int day = date[2];

        Resources res = context.getResources();
        sb.append(res.getString(R.string.selected_date));
        if (Locale.getDefault().getLanguage().equals("ko")) {
            sb.append(Integer.toString(year)).append(res.getString(R.string.year))
                    .append(Integer.toString(month)).append(res.getString(R.string.month))
                    .append(Integer.toString(day)).append(res.getString(R.string.day));
        } else {
            sb.append(Integer.toString(year)).append(". ")
                    .append(Integer.toString(month)).append(". ")
                    .append(Integer.toString(day));
        }
        txtSelectedDate.setText(sb.toString());

        StringBuilder sb2 = new StringBuilder();
        sb2.append(Integer.toString(year)).append(String.format("%02d", month)).append(String.format("%02d", day));
        btnOk.setTag(sb2.toString());
    }

    /**
     * 달력 cell들 text 삭제
     */
    private void clearDayCellText() {
        for (int i = 0; i < dayCellViewList.size(); i++) {
            dayCellViewList.get(i).setText("");
        }
    }

    public void setOnPositiveBtnClickListener(View.OnClickListener listener) {
        btnOk.setOnClickListener(listener);
    }

    @Override
    public void onClick(View v) {
        // 현재 보여지는 년월로 calendar 설정
        Calendar cal = Calendar.getInstance();
        cal.set(showYearMonth[0], showYearMonth[1], 1);

        // 년월 변경
        int i = v.getId();
        if (i == R.id.btnBack) {
            cal.add(Calendar.MONTH, -1);
        } else if (i == R.id.btnFirst) {
            cal.add(Calendar.YEAR, -1);
        } else if (i == R.id.btnLast) {
            cal.add(Calendar.YEAR, +1);
        } else if (i == R.id.btnNext) {
            cal.add(Calendar.MONTH, +1);
        }

        // 변경된 년월을 저장하고 달력 day cell들 변경
        showYearMonth[0] = cal.get(Calendar.YEAR);
        showYearMonth[1] = cal.get(Calendar.MONTH);
        setCalendarDayCellViews();
    }
}
