package gui.diarypanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import models.ExerciseModel;
import util.Util;

public class ExercisePanel extends JPanel {

	private ExerciseModel model;

	private JComboBox<String> muscleGroup;
	private JComboBox<String> exercise;
	private ButtonGroup workoutRate;
	private JComboBox<String> time;
	private ButtonGroup difficulty;

	private JButton add;
	private JButton delete;

	private JTable table;

	public ExercisePanel(ExerciseModel myExerciseModel) {
		this.model = myExerciseModel;

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 2;
		gbc.weighty = 1;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		JPanel optionPanel = createOptionPanel();
		add(optionPanel, gbc);

		gbc.weightx = 2;
		gbc.weighty = 3;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 1;
		JPanel tablePanel = createTablePanel();
		add(tablePanel, gbc);
	}

	/*************************** VIEW PART ***************************/

	private JPanel createOptionPanel() {
		GridBagConstraints gbc = new GridBagConstraints();
		DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
		dlcr.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 3));

		JPanel subpanel = new JPanel();
		subpanel.setLayout(new GridBagLayout());
		subpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel label = new JLabel("Muscle Group");
		final String[] muscleGroup = { "chest", "shoulders", "triceps", "back", "biceps", "abs", "hamstrings", "quads", "calves" };
		this.muscleGroup = new JComboBox<String>(muscleGroup);
		this.muscleGroup.setSelectedIndex(0);
		this.muscleGroup.setRenderer(dlcr);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.CENTER;
		subpanel.add(label, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		subpanel.add(this.muscleGroup, gbc);
		panel.add(subpanel);

		subpanel = new JPanel();
		subpanel.setLayout(new GridBagLayout());
		subpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		label = new JLabel("Exercise");
		final String[] exercise = { "ab crunch", "backward drag", "cable crossover", "rack delivery", "rack pulls" };
		this.exercise = new JComboBox<String>(exercise);
		this.exercise.setSelectedIndex(0);
		this.exercise.setRenderer(dlcr);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.CENTER;
		subpanel.add(label, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		subpanel.add(this.exercise, gbc);
		panel.add(subpanel);

		subpanel = new JPanel();
		subpanel.setLayout(new GridBagLayout());
		subpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		label = new JLabel("Workout Rate");
		JRadioButton wr_button1 = new JRadioButton("Excellent");
		wr_button1.setActionCommand("Excellent");
		JRadioButton wr_button2 = new JRadioButton("Good");
		wr_button2.setActionCommand("Good");
		JRadioButton wr_button3 = new JRadioButton("Bad");
		wr_button3.setActionCommand("Bad");
		gbc.fill = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 3;
		gbc.weighty = 1;
		subpanel.add(label, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		subpanel.add(wr_button1, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		subpanel.add(wr_button2, gbc);
		gbc.gridx = 2;
		gbc.gridy = 1;
		subpanel.add(wr_button3, gbc);
		panel.add(subpanel);
		this.workoutRate = new ButtonGroup();
		this.workoutRate.add(wr_button1);
		this.workoutRate.add(wr_button2);
		this.workoutRate.add(wr_button3);
		wr_button1.setSelected(true);

		subpanel = new JPanel();
		subpanel.setLayout(new GridBagLayout());
		subpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		label = new JLabel("Time (min)");
		final String[] time = { "30", "60", "90", "120", "150", "180" };
		this.time = new JComboBox<String>(time);
		this.time.setSelectedIndex(0);
		this.time.setRenderer(dlcr);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.CENTER;
		subpanel.add(label, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.BOTH;
		subpanel.add(this.time, gbc);
		panel.add(subpanel);

		subpanel = new JPanel();
		subpanel.setLayout(new GridBagLayout());
		subpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		label = new JLabel("Difficulty");
		JRadioButton d_button1 = new JRadioButton("Easy");
		d_button1.setActionCommand("Easy");
		JRadioButton d_button2 = new JRadioButton("Medium");
		d_button2.setActionCommand("Mod.");
		JRadioButton d_button3 = new JRadioButton("Hard");
		d_button3.setActionCommand("Hard");
		gbc.fill = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 3;
		gbc.gridheight = 1;
		gbc.weightx = 3;
		gbc.weighty = 1;
		subpanel.add(label, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		subpanel.add(d_button1, gbc);
		gbc.gridx = 1;
		gbc.gridy = 1;
		subpanel.add(d_button2, gbc);
		gbc.gridx = 2;
		gbc.gridy = 1;
		subpanel.add(d_button3, gbc);
		panel.add(subpanel);
		this.difficulty = new ButtonGroup();
		this.difficulty.add(d_button1);
		this.difficulty.add(d_button2);
		this.difficulty.add(d_button3);
		d_button1.setSelected(true);

		subpanel = new JPanel();
		subpanel.setLayout(new GridLayout(1, 2));
		subpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add = new JButton("Add");
		this.delete = new JButton("Delete");
		subpanel.add(this.add);
		subpanel.add(this.delete);
		panel.add(subpanel);

		return panel;
	}
	
	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new GridLayout(1,1));
		// table names and model
		this.table = new JTable(this.model.getCurrentTableModel());
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// align cell center
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		this.table.setDefaultRenderer(Object.class, centerRenderer);
		this.table.setRowHeight(50);
		
		JScrollPane scrollPane = new JScrollPane(this.table);
		scrollPane.setPreferredSize(new Dimension(100,100));
		panel.add(scrollPane);
		return panel;
	}

	/*************************** CONTROLLER PART ***************************/
	// change model actions
	public void addItemToModel() {
		String time = Util.isNumber((String) this.time.getSelectedItem())? (String) this.time.getSelectedItem() : "0";
		String[] rowData = {(String) this.muscleGroup.getSelectedItem(),
				(String) this.exercise.getSelectedItem(),
				this.workoutRate.getSelection().getActionCommand(),
				time,
				this.difficulty.getSelection().getActionCommand()};
		int timeValue = Integer.valueOf(time);
		this.model.getCurrentTableModel().increaseTotalTime(timeValue);
		this.model.increaseTotalTime(timeValue);
		this.model.getCurrentTableModel().addRow(rowData);
		this.model.getCurrentTableModel().setIsModified(true);
		this.model.setIsModified(true);
	}

	public void removeItemFromModel() {
		int selectedRow = this.table.getSelectedRow();
		if (selectedRow != -1) {
			int timeValue = Integer.valueOf((String)this.table.getValueAt(selectedRow, 3));
			this.model.getCurrentTableModel().decreaseTotalTime(timeValue);
			this.model.decreaseTotalTime(timeValue);
			this.model.getCurrentTableModel().removeRow(selectedRow);
			this.model.getCurrentTableModel().setIsModified(true);
			this.model.setIsModified(true);
		}
	}

	public void changeCurrentTable(int index) {
		this.model.setCurrentTableModel(index);
	}

	// change view actions
	public void attachAddButtonListener(String actionCommand, ActionListener listener) {
		this.add.setActionCommand(actionCommand);
		this.add.addActionListener(listener);
	}

	public void attachDeleteButtonListener(String actionCommand, ActionListener listener) {
		this.delete.setActionCommand(actionCommand);
		this.delete.addActionListener(listener);
	}
	
	public void repaintTable() {
		this.table.setModel(this.model.getCurrentTableModel());
	}
}
