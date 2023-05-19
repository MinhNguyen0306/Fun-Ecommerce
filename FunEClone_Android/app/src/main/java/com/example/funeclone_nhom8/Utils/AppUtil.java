package com.example.funeclone_nhom8.Utils;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.fragment.app.FragmentActivity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AppUtil {
    public static void closeKeyBoard(FragmentActivity fragmentActivity, EditText editText) {
        View view = fragmentActivity.getCurrentFocus();
        if(view != null) {
            InputMethodManager imm = (InputMethodManager)fragmentActivity
                    .getApplication().getSystemService(
                            fragmentActivity.getApplication().getApplicationContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        editText.setText("");
    }

    public static void openKeyBoard(FragmentActivity fragmentActivity, EditText editText) {
        View view = fragmentActivity.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager)fragmentActivity
                .getApplication().getSystemService(
                        fragmentActivity.getApplication().getApplicationContext().INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
    }

    public static Map.Entry<String, Long> getDifferentBetweenLocalDateTime(String createAt) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            LocalDateTime localDateTime = LocalDateTime.parse(createAt, dateTimeFormatter);
            LocalDateTime localDateTimeNow = LocalDateTime.now();
            LocalDateTime tempDateTime = LocalDateTime.from(localDateTime);
            long years = tempDateTime.until(localDateTimeNow, ChronoUnit.YEARS);
            tempDateTime.plusYears(years);
            long months = tempDateTime.until(localDateTimeNow, ChronoUnit.MONTHS);
            tempDateTime.plusMonths(months);
            long days = tempDateTime.until(localDateTimeNow, ChronoUnit.DAYS);
            tempDateTime.plusDays(days);
            long hours = tempDateTime.until(localDateTimeNow, ChronoUnit.HOURS);
            tempDateTime.plusHours(hours);
            long minutes = tempDateTime.until(localDateTimeNow, ChronoUnit.MINUTES);
            tempDateTime.plusMinutes(minutes);
            long seconds = tempDateTime.until(localDateTimeNow, ChronoUnit.SECONDS);
            Map<String, Long> spanTimeMap = new HashMap<>();
            spanTimeMap.put("year", years);
            spanTimeMap.put("month", months);
            spanTimeMap.put("day", days);
            spanTimeMap.put("hour", hours);
            spanTimeMap.put("minute", minutes);
            spanTimeMap.put("second", seconds);

            for (Map.Entry<String, Long> entry : spanTimeMap.entrySet()) {
                if(entry.getValue() != 0) {
                    return entry;
                }
            }
        }
        return null;
    }

    public static class MyPasswordTransformationMethod extends PasswordTransformationMethod {
        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PasswordCharSequence(source);
        }
        private class PasswordCharSequence implements CharSequence {
            private CharSequence mSource;
            public PasswordCharSequence(CharSequence source) {
                mSource = source;
            }
            public char charAt(int index) {
                char character;
                switch (index)
                {
                    case 4:
                        character = ' ';
                        break;
                    case 9:
                        character = ' ';
                        break;
                    case 14:
                        character = ' ';
                        break;
                    default:
                        if(index < 13)
                            return '*';
                        else
                            character = mSource.charAt(index);
                        break;
                }
                return character;
            }
            public int length() {
                return mSource.length();
            }
            public CharSequence subSequence(int start, int end) {
                return mSource.subSequence(start, end);
            }
        }
    };
}
