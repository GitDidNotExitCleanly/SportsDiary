package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import models.DiaryModel;
import util.CalendarHelper;

public class SummaryPanel extends JPanel {

	private DiaryModel model;

	private JLabel todayTotalTime;
	private JLabel todayTotalCalories;
	private JLabel thisMonthTotalTime;
	private JLabel thisMonthTotalCalories;
	
	private JPanel popupPanel;

	/*************************** VIEW PART ***************************/

	public SummaryPanel(DiaryModel diaryModel) {
		this.model = diaryModel;

		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		/* tabbed panel */
		JTabbedPane contentPanel = new JTabbedPane();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;

		// today's data
		JPanel todayPanel = new JPanel();
		todayPanel.setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 0;
		todayPanel.add(new JLabel("Total Time (h):"), gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.todayTotalTime = new JLabel(String.valueOf(this.model.getExerciseModel().getCurrentTableModel().getTotalTime()));
		todayPanel.add(this.todayTotalTime, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		todayPanel.add(new JLabel("Total Calories (C):"), gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		this.todayTotalCalories = new JLabel(String.valueOf(this.model.getNutritionModel().getCurrentTableModel().getTotalCalories()));
		todayPanel.add(this.todayTotalCalories, gbc);
		contentPanel.addTab("Today", todayPanel);

		// this month's data
		JPanel thisMonthPanel = new JPanel();
		thisMonthPanel.setLayout(new GridBagLayout());
		gbc.fill = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 0;
		thisMonthPanel.add(new JLabel("Total Time (h):"), gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.thisMonthTotalTime = new JLabel(String.valueOf(this.model.getExerciseModel().getTotalTime()));
		thisMonthPanel.add(this.thisMonthTotalTime, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		thisMonthPanel.add(new JLabel("Total Calories (C):"), gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		this.thisMonthTotalCalories = new JLabel(
				String.valueOf(this.model.getNutritionModel().getTotalCalories()));
		thisMonthPanel.add(this.thisMonthTotalCalories, gbc);
		contentPanel.addTab("This Month", thisMonthPanel);

		/* main panel */
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 2;
		add(contentPanel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		JButton detailButton = new JButton("See Details");
		add(detailButton, gbc);

		// create pop-up panel
		createPopupPanel();

		// attach listeners
		SeeDetailsButtonListener listener = new SeeDetailsButtonListener();
		detailButton.addActionListener(listener);
	}
	
	public void createPopupPanel() {
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel panel = new JPanel(new GridBagLayout());
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		JLabel dateLabel = new JLabel(CalendarHelper.getInstance().toLongDateWithoutDay());
		panel.add(dateLabel,gbc);
		
		int numOfDays = CalendarHelper.getInstance().getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);
		JPanel subpanel = new JPanel(new GridLayout(numOfDays,1));
		for (int i=0;i<numOfDays;i++) {
			JPanel itemPanel = new JPanel(new GridBagLayout());
			itemPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
			
			JLabel label = new JLabel("Day "+(i+1+" :"));
			label.setHorizontalAlignment(SwingConstants.CENTER);
			gbc.gridx = 0;
			gbc.gridy = 0 + i*3;
			gbc.gridwidth = 2;
			gbc.gridheight = 1;
			gbc.weightx = 2;
			gbc.weighty = 1;
			itemPanel.add(label,gbc);
			
			JLabel totalTime = new JLabel("total time (h):");
			totalTime.setHorizontalAlignment(SwingConstants.CENTER);
			gbc.gridx = 0;
			gbc.gridy = 1  + i*3;
			gbc.gridwidth = 1;
			gbc.weightx = 1;
			itemPanel.add(totalTime,gbc);
			JLabel totalTimeValue = new JLabel(String.valueOf(this.model.getExerciseModel().getTableModelAt(i).getTotalTime()));		
			totalTimeValue.setHorizontalAlignment(SwingConstants.CENTER);
			gbc.gridx = 1;
			itemPanel.add(totalTimeValue,gbc);
			
			JLabel totalCalories = new JLabel("total calories (C):");
			totalCalories.setHorizontalAlignment(SwingConstants.CENTER);
			gbc.gridx = 0;
			gbc.gridy = 2  + i*3;
			itemPanel.add(totalCalories,gbc);
			JLabel totalCaloriesValue = new JLabel(String.valueOf(this.model.getNutritionModel().getTableModelAt(i).getTotalCalories()));
			totalCaloriesValue.setHorizontalAlignment(SwingConstants.CENTER);
			gbc.gridx = 1;
			itemPanel.add(totalCaloriesValue,gbc);
			
			subpanel.add(itemPanel);
		}
		JScrollPane scrollPane = new JScrollPane(subpanel);
		scrollPane.setPreferredSize(new Dimension(500,500));
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		panel.add(scrollPane,gbc);
		
		this.popupPanel = panel;
	}

	/*************************** CONTROLLER PART ***************************/
	// change view actions
	public void repaintToday() {
		this.todayTotalTime.setText(String.valueOf(this.model.getExerciseModel().getCurrentTableModel().getTotalTime()));
		this.todayTotalCalories.setText(String.valueOf(this.model.getNutritionModel().getCurrentTableModel().getTotalCalories()));
		this.todayTotalTime.repaint();
		this.todayTotalCalories.repaint();
	}

	public void repaintThisMonth() {
		this.thisMonthTotalTime.setText(String.valueOf(this.model.getExerciseModel().getTotalTime()));
		this.thisMonthTotalCalories.setText(String.valueOf(this.model.getNutritionModel().getTotalCalories()));
		this.thisMonthTotalTime.repaint();
		this.thisMonthTotalCalories.repaint();
	}

	private class SeeDetailsButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(getParent(), popupPanel, "Details", JOptionPane.PLAIN_MESSAGE);
		}
	}
}
