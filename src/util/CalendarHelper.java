package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarHelper {

	// singleton pattern
	private static CalendarHelper helper = new CalendarHelper();

	public static CalendarHelper getInstance() {
		return helper;
	}

	private Calendar calendar;
	private int firstDayOfMonth;

	private CalendarHelper() {
		// default value
		this.calendar = Calendar.getInstance();
		this.firstDayOfMonth = calculateFirstDayOfMonth();
	}

	private int calculateFirstDayOfMonth() {
		int oldDay = this.calendar.get(Calendar.DATE);
		this.calendar.set(Calendar.DATE, 1);
		int firstDayOfMonth = this.calendar.get(Calendar.DAY_OF_WEEK) - 1;
		this.calendar.set(Calendar.DATE, oldDay);
		return firstDayOfMonth;
	}

	public Calendar getCalendar() {
		return this.calendar;
	}

	public int getFirstDayOfMonth() {
		return this.firstDayOfMonth;
	}

	public String toLongDate() {
		SimpleDateFormat format = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH);
		return format.format(calendar.getTime());
	}
	
	public String toLongDateWithoutDay() {
		SimpleDateFormat format = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
		return format.format(calendar.getTime());
	}

	public void nextMonth() {
		int year = this.calendar.get(Calendar.YEAR);
		int month = this.calendar.get(Calendar.MONTH);
		int day = 1;
		if (month == 11) {
			month = 0;
			year++;
		} else {
			month++;
		}
		this.calendar.set(year, month, day);
		this.firstDayOfMonth = calculateFirstDayOfMonth();
	}

	public void previousMonth() {
		int year = this.calendar.get(Calendar.YEAR);
		int month = this.calendar.get(Calendar.MONTH);
		int day = 1;
		if (month == 0) {
			month = 11;
			year--;
		} else {
			month--;
		}
		this.calendar.set(year, month, day);
		this.firstDayOfMonth = calculateFirstDayOfMonth();
	}

	public void changeDate(int currentIndex, int targetIndex) {
		int currentDay = this.calendar.get(Calendar.DATE);
		int targetDay = currentDay + targetIndex - currentIndex;
		this.calendar.set(Calendar.DATE, targetDay);
	}
}
