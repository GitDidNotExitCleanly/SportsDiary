package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import database.DatabaseHelper;
import gui.diarypanel.DiaryPanel;
import models.DiaryModel;
import models.UserInfoModel;
import util.CalendarHelper;

public class MainFrame extends JFrame {

	private UserInfoModel userInfoModel = null;

	//private DiaryModel diaryModel = null;
	private DiaryModel diaryModel = null;
	
	private Splash splash = null;

	private UserInfoPanel usrInfo = null;

	private SummaryPanel summary = null;

	private CalendarPanel calendar = null;

	private DiaryPanel diary = null;

	public MainFrame() {
		// create and show splash
		createSplash();
		showSplash();

		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {

					// load models
					loadModels();

					// prepare UI component
					createUserInfoPanel();
					createSummaryPanel();
					createCalendarPanel();
					createDiaryPanel();

					// attach listeners
					attachListeners();

					JPanel mainPanel = new JPanel();
					mainPanel.setLayout(new GridBagLayout());
					GridBagConstraints gbc = new GridBagConstraints();

					gbc.fill = GridBagConstraints.BOTH;
					gbc.gridx = 0;
					gbc.gridy = 0;
					gbc.weightx = 1;
					gbc.weighty = 1;
					mainPanel.add(usrInfo, gbc);

					gbc.gridx = 0;
					gbc.gridy = 1;
					gbc.weightx = 1;
					gbc.weighty = 1;
					mainPanel.add(summary, gbc);

					gbc.gridx = 0;
					gbc.gridy = 2;
					gbc.weightx = 1;
					gbc.weighty = 1;
					mainPanel.add(calendar, gbc);

					gbc.gridx = 1;
					gbc.gridy = 0;
					gbc.gridheight = 3;
					gbc.weightx = 3;
					gbc.weighty = 2;
					mainPanel.add(diary, gbc);
					setContentPane(mainPanel);					
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1200, 900));
		pack();
		setLocationRelativeTo(null);
		setTitle("Welcome to Sports Diary");

		// show component
		hideSplash();
		setVisible(true);
	}

	private void createSplash() {
		this.splash = new Splash();
	}

	private void showSplash() {
		this.splash.show();
	}

	private void hideSplash() {
		this.splash.hide();
	}

	private void loadModels() {
		this.userInfoModel = DatabaseHelper.getInstance().readUserInfo();
		this.diaryModel = DatabaseHelper.getInstance().readDiary();
	}

	private void createUserInfoPanel() {
		this.usrInfo = new UserInfoPanel(this.userInfoModel);
	}

	private void createSummaryPanel() {
		this.summary = new SummaryPanel(this.diaryModel);
	}

	private void createCalendarPanel() {
		this.calendar = new CalendarPanel();
	}

	private void createDiaryPanel() {
		this.diary = new DiaryPanel(this.diaryModel);
	}

	private void attachListeners() {
		// for calendar panel
		ChangeDateButtonListener buttonListener = new ChangeDateButtonListener();
		calendar.attachPreviousButtonListener(CALENDAR_PREVIOUS_COMMAND, buttonListener);
		calendar.attachNextButtonListener(CALENDAR_NEXT_COMMAND, buttonListener);
		ChangeDateLabelListener labelListener = new ChangeDateLabelListener();
		calendar.attachLabelListener(CALENDAR_LABEL_COMMAND_PREFIX, labelListener);

		// for exercise panel
		ExerciseChangeTableListener eListener = new ExerciseChangeTableListener();
		diary.exercisePanel.attachAddButtonListener(EXERCISE_ADD_COMMAND, eListener);
		diary.exercisePanel.attachDeleteButtonListener(EXERCISE_DELETE_COMMAND, eListener);

		// for nutrition panel
		NutritionChangeTableListener nListener = new NutritionChangeTableListener();
		diary.nutritionPanel.attachAddButtonListener(NUTRITION_ADD_COMMAND, nListener);
		diary.nutritionPanel.attachDeleteButtonListener(NUTRITION_DELETE_COMMAND, nListener);
		diary.nutritionPanel.attachInputWizardButtonListener(NUTRITION_INPUT_WIZARD_COMMAND, nListener);
		
		// for save button
		SaveButtonListener saveButtonListener = new SaveButtonListener();
		diary.getSaveButton().addActionListener(saveButtonListener);
	}

	private static final String CALENDAR_PREVIOUS_COMMAND = "previous";
	private static final String CALENDAR_NEXT_COMMAND = "next";

	private class ChangeDateButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean isDataSaved = !userInfoModel.isModified() 
								&& !diaryModel.getExerciseModel().isModified()
								&& !diaryModel.getNutritionModel().isModified();
			if (!isDataSaved) {
				int option = JOptionPane.showConfirmDialog(getParent(), "Do you want to save your data ?", "", JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					diary.getSaveButton().doClick();
				}
			}
			new SwingWorker<Void,Void>() {
				@Override
				protected Void doInBackground() throws Exception {
					String command = e.getActionCommand();
					if (command.compareTo(CALENDAR_PREVIOUS_COMMAND) == 0) {
						CalendarHelper.getInstance().previousMonth();
					} else {
						CalendarHelper.getInstance().nextMonth();
					}
					DatabaseHelper.getInstance().readDiary();
					return null;
				}
				
				@Override
				protected void done() {
					calendar.repaintDate();
					calendar.repaintDayPanel();
					summary.repaintToday();
					summary.repaintThisMonth();
					summary.createPopupPanel();
					diary.exercisePanel.repaintTable();
					diary.nutritionPanel.repaintTable();
				}
			}.execute();
		}
	}

	private static final String CALENDAR_LABEL_COMMAND_PREFIX = "day_";

	private class ChangeDateLabelListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			int index = Integer.valueOf(command.split("_")[1]);

			// change date
			CalendarHelper.getInstance().changeDate(calendar.getSelectedIndex(), index);

			// change previous selected label
			calendar.setSelectedLabelReleased();
			// change current selected label
			calendar.setNewSelectedLabel(index);
			// change date panel
			calendar.repaintDate();
			// change selected index
			calendar.setSelectedIndex(index);

			// update model and refresh table
			int newDateIndex = CalendarHelper.getInstance().getCalendar().get(Calendar.DATE) - 1;
			diary.exercisePanel.changeCurrentTable(newDateIndex);
			diary.nutritionPanel.changeCurrentTable(newDateIndex);
			diary.exercisePanel.repaintTable();
			diary.nutritionPanel.repaintTable();

			// refresh summary panel
			summary.repaintToday();
		}
	}

	private static final String EXERCISE_ADD_COMMAND = "add";
	private static final String EXERCISE_DELETE_COMMAND = "delete";

	private class ExerciseChangeTableListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if (command.compareTo(EXERCISE_ADD_COMMAND) == 0) {
				diary.exercisePanel.addItemToModel();
			} else {
				diary.exercisePanel.removeItemFromModel();
			}
			summary.repaintToday();
			summary.repaintThisMonth();
		}
	}

	private static final String NUTRITION_ADD_COMMAND = "add";
	private static final String NUTRITION_DELETE_COMMAND = "delete";
	private static final String NUTRITION_INPUT_WIZARD_COMMAND = "input";

	private class NutritionChangeTableListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String command = e.getActionCommand();
			if (command.compareTo(NUTRITION_ADD_COMMAND) == 0) {
				diary.nutritionPanel.addItemToModel();
				summary.repaintToday();
				summary.repaintThisMonth();
			} else if (command.compareTo(NUTRITION_DELETE_COMMAND) == 0) {
				diary.nutritionPanel.removeItemFromModel();
				summary.repaintToday();
				summary.repaintThisMonth();	
			} else {
				diary.nutritionPanel.addItemToModelFromPopupWindow();
			}
		}
	}
	
	private class SaveButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			new SwingWorker<Void,Void>() {
				@Override
				protected Void doInBackground() throws Exception {
					if (userInfoModel.isModified()) {
						DatabaseHelper.getInstance().writeUserInfo(userInfoModel);
					}
					if (diaryModel.getExerciseModel().isModified() || diaryModel.getNutritionModel().isModified()) {
						DatabaseHelper.getInstance().writeDiary(diaryModel);
					}
					return null;
				}
				
				@Override
				protected void done() {
					JOptionPane.showMessageDialog(getParent(), "Save Successfully");
				}
			}.execute();
		}
	}
}
