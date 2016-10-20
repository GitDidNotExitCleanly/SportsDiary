package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import util.CalendarHelper;

public class CalendarPanel extends JPanel {

	private JButton previous;
	private JButton next;
	private JLabel date;

	private JButton[] days;
	private int selectedIndex;

	public CalendarPanel() {
		setBorder(BorderFactory.createLineBorder(Color.black));

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		gbc.weightx = 4;
		gbc.weighty = 2;
		JLabel smallLogo = new JLabel("Calendar");
		smallLogo.setFont(new Font(smallLogo.getName(), Font.PLAIN, smallLogo.getFont().getSize() * 3));
		add(smallLogo, gbc);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		previous = new JButton("<");
		add(previous, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.weightx = 2;
		gbc.weighty = 1;
		createDatePanel();
		add(date, gbc);

		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		next = new JButton(">");
		add(next, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 4;
		gbc.weightx = 4;
		gbc.weighty = 1;
		add(createWeekPanel(), gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 4;
		gbc.weightx = 4;
		gbc.weighty = 6;
		add(createDayPanel(), gbc);
	}

	/*************************** VIEW PART ***************************/

	private void createDatePanel() {
		String date = CalendarHelper.getInstance().toLongDate();
		this.date = new JLabel(date, SwingConstants.CENTER);
	}

	private JPanel createWeekPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 7));
		panel.add(new JLabel("SUN", SwingConstants.CENTER));
		panel.add(new JLabel("MON", SwingConstants.CENTER));
		panel.add(new JLabel("TUE", SwingConstants.CENTER));
		panel.add(new JLabel("WED", SwingConstants.CENTER));
		panel.add(new JLabel("THU", SwingConstants.CENTER));
		panel.add(new JLabel("FRI", SwingConstants.CENTER));
		panel.add(new JLabel("SAT", SwingConstants.CENTER));
		return panel;
	}

	private JPanel createDayPanel() {
		JPanel dayPanel = new JPanel();
		dayPanel.setLayout(new GridLayout(6, 7));

		int firstDayIndex = CalendarHelper.getInstance().getFirstDayOfMonth();
		int currentDayIndex = firstDayIndex + CalendarHelper.getInstance().getCalendar().get(Calendar.DATE) - 1;
		this.selectedIndex = currentDayIndex;
		this.days = new JButton[42];
		int numOfDays = CalendarHelper.getInstance().getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);
		int count = 1;
		for (int i = 0; i < 42; i++) {
			this.days[i] = new JButton("");
			this.days[i].setEnabled(false);
		}
		for (int i = firstDayIndex; i < firstDayIndex + numOfDays; i++) {
			this.days[i] = new JButton(String.valueOf(count));
			this.days[i].setEnabled(true);
			this.days[i].setOpaque(true);
			this.days[i].setBackground(Color.white);
			count++;
		}
		this.days[currentDayIndex].setBackground(Color.black);
		for (int i = 0; i < 42; i++) {
			dayPanel.add(this.days[i]);
		}
		return dayPanel;
	}

	/*************************** CONTROLLER PART ***************************/
	// change view actions
	public void attachPreviousButtonListener(String actionCommand, ActionListener listener) {
		this.previous.setActionCommand(actionCommand);
		this.previous.addActionListener(listener);
	}

	public void attachNextButtonListener(String actionCommand, ActionListener listener) {
		this.next.setActionCommand(actionCommand);
		this.next.addActionListener(listener);
	}

	public void attachLabelListener(String actionCommandPrefix, ActionListener listener) {
		for (int i = 0; i < 42; i++) {
			this.days[i].setActionCommand(actionCommandPrefix + i);
			this.days[i].addActionListener(listener);
		}
	}
	
	public void repaintDayPanel() {
		int firstDayIndex = CalendarHelper.getInstance().getFirstDayOfMonth();
		int currentDayIndex = firstDayIndex + CalendarHelper.getInstance().getCalendar().get(Calendar.DATE) - 1;
		this.selectedIndex = currentDayIndex;
		int numOfDays = CalendarHelper.getInstance().getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);
		int count = 1;
		for (int i = 0; i < 42; i++) {
			this.days[i].setText("");
			this.days[i].setOpaque(false);
			this.days[i].setEnabled(false);
		}
		for (int i = firstDayIndex; i < firstDayIndex + numOfDays; i++) {
			this.days[i].setText(String.valueOf(count));
			this.days[i].setOpaque(true);
			this.days[i].setBackground(Color.white);
			this.days[i].setEnabled(true);
			count++;
		}
		this.days[currentDayIndex].setBackground(Color.black);
		for (int i = 0; i < 42; i++) {
			this.days[i].repaint();
		}
	}

	public void repaintDate() {
		String date = CalendarHelper.getInstance().toLongDate();
		this.date.setText(date);
		this.date.repaint();
	}

	public void setSelectedLabelReleased() {
		this.days[this.selectedIndex].setBackground(Color.white);
		this.days[this.selectedIndex].repaint();
	}

	public void setNewSelectedLabel(int index) {
		this.days[index].setBackground(Color.black);
		this.days[index].repaint();
	}

	public int getSelectedIndex() {
		return this.selectedIndex;
	}

	public void setSelectedIndex(int index) {
		this.selectedIndex = index;
	}
}
