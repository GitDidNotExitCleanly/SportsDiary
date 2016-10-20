package gui.diarypanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import models.NutritionModel;

public class NutritionPanel extends JPanel {

	private NutritionModel model;

	private JTextField food;
	private JComboBox<String> mealType;
	private JComboBox<String> category;
	private JComboBox<String> time;
	private JTextField calories;
	private JTextField fat;
	private JTextField sugar;
	private JTextField water;

	private JButton add;
	private JButton delete;

	private JTable table;

	private JButton inputWizard;
	private JPanel inputWizardPopupPanel;
	private JTable inputWizardTable;
	
	public NutritionPanel(NutritionModel myNutritionModel) {
		this.model = myNutritionModel;

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
		
		this.inputWizardPopupPanel = createPopupPanel();
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
		JLabel label = new JLabel("Food");
		this.food = new JTextField();
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
		subpanel.add(this.food, gbc);
		panel.add(subpanel);

		subpanel = new JPanel();
		subpanel.setLayout(new GridBagLayout());
		subpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		label = new JLabel("Meal Type");
		final String[] mealType = { "breakfast", "brunch", "lunch", "dinner", "supper" };
		this.mealType = new JComboBox<String>(mealType);
		this.mealType.setSelectedIndex(0);
		this.mealType.setRenderer(dlcr);
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
		subpanel.add(this.mealType, gbc);
		panel.add(subpanel);

		subpanel = new JPanel();
		subpanel.setLayout(new GridBagLayout());
		subpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		label = new JLabel("Category");
		final String[] category = { "vegetable", "meat", "fruit", "other" };
		this.category = new JComboBox<String>(category);
		this.category.setSelectedIndex(0);
		this.category.setRenderer(dlcr);
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
		subpanel.add(this.category, gbc);
		panel.add(subpanel);

		subpanel = new JPanel();
		subpanel.setLayout(new GridBagLayout());
		subpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		label = new JLabel("Time");
		final String[] time = { "1 AM", "2 AM", "3 AM", "4 AM", "5 AM", "6 AM", "7 AM", "8 AM", "9 AM", "10 AM", "11 AM",
				"12 PM", "1 PM", "2 PM", "3 PM", "4 PM", "5 PM", "6 PM", "7 PM", "8 PM", "9 PM", "10 PM", "11 PM",
				"12 AM" };
		this.time = new JComboBox<String>(time);
		this.time.setSelectedItem("6 AM");
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
		label = new JLabel("Nutrition");
		JLabel calories = new JLabel("Calories");
		this.calories = new JTextField(3);
		JLabel fat = new JLabel("Fat");
		this.fat = new JTextField(3);
		JLabel sugar = new JLabel("Sugar");
		this.sugar = new JTextField(3);
		JLabel water = new JLabel("Water");
		this.water = new JTextField(3);
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		gbc.gridheight = 1;
		gbc.weightx = 4;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.CENTER;
		subpanel.add(label, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.CENTER;
		subpanel.add(calories, gbc);
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.BOTH;
		subpanel.add(this.calories, gbc);
		gbc.gridx = 2;
		gbc.fill = GridBagConstraints.CENTER;
		subpanel.add(fat, gbc);
		gbc.gridx = 3;
		gbc.fill = GridBagConstraints.BOTH;
		subpanel.add(this.fat, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.CENTER;
		subpanel.add(sugar, gbc);
		gbc.gridx = 1;
		gbc.fill = GridBagConstraints.BOTH;
		subpanel.add(this.sugar, gbc);
		gbc.gridx = 2;
		gbc.fill = GridBagConstraints.CENTER;
		subpanel.add(water, gbc);
		gbc.gridx = 3;
		gbc.fill = GridBagConstraints.BOTH;
		subpanel.add(this.water, gbc);
		panel.add(subpanel);

		subpanel = new JPanel();
		subpanel.setLayout(new GridBagLayout());
		subpanel.setBorder(BorderFactory.createLineBorder(Color.black));
		this.add = new JButton("Add");
		this.delete = new JButton("Delete");
		this.inputWizard = new JButton("Input Wizard");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		subpanel.add(this.add, gbc);
		gbc.gridx = 0;
		gbc.gridy = 1;
		subpanel.add(this.delete, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridheight = 2;
		gbc.weighty = 2;
		subpanel.add(this.inputWizard, gbc);
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

	private JPanel createPopupPanel() {
		GridBagConstraints gbc = new GridBagConstraints();
		JPanel panel = new JPanel(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		JLabel label = new JLabel("Select from the table below :");
		panel.add(label,gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 1;
		final String[] colHeadings = { "Food", "Calories", "Fat", "Sugar", "Water" };
		final String[][] data = {{"apple","8","12","7","14"},
						   {"orange","25","8","13","20"},
						   {"peach","5","17","10","20"},
						   {"grape","10","21","18","16"},
						   {"avocado","8","14","29","26"},
						   {"banana","10","9","27","36"},
						   {"guava","16","23","31","16"},
						   {"honeydew melon","6","7","8","12"},
						   {"kiwi fruit","14","26","13","15"},
						   {"lemon","17","8","21","21"},
						   {"mango","10","17","19","28"},
						   {"pear","8","15","14","26"},
						   {"pineapple","20","10","28","8"},
						   {"strawberry","30","11","8","26"}};
		this.inputWizardTable = new JTable(data, colHeadings);
		this.inputWizardTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// set all cells uneditable
		for (int i=0;i<colHeadings.length;i++) {
			this.inputWizardTable.setDefaultEditor(this.inputWizardTable.getColumnClass(i), null);
		}
		// align cell center
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		this.inputWizardTable.setDefaultRenderer(Object.class, centerRenderer);
		this.inputWizardTable.setRowHeight(30);
		
		panel.add(new JScrollPane(this.inputWizardTable),gbc);
		return panel;
	}

	/*************************** CONTROLLER PART ***************************/
	// change model actions
	public void addItemToModel() {
		String food = (this.food.getText().compareTo("") == 0) ? "[EMPTY]" : this.food.getText();
		String calories = (this.calories.getText().compareTo("") == 0) ? "0" : this.calories.getText();
		String fat = (this.fat.getText().compareTo("") == 0) ? "0" : this.fat.getText();
		String sugar = (this.sugar.getText().compareTo("") == 0) ? "0" : this.sugar.getText();
		String water = (this.water.getText().compareTo("") == 0) ? "0" : this.water.getText();
		String[] rowData = {food,
						(String) this.mealType.getSelectedItem(),
						(String) this.category.getSelectedItem(),
						(String) this.time.getSelectedItem(),
						calories,
						fat,
						sugar,
						water};
		int caloriesValue = Integer.valueOf(calories);
		this.model.getCurrentTableModel().increaseTotalCalories(caloriesValue);
		this.model.increaseTotalCalories(caloriesValue);
		this.model.getCurrentTableModel().addRow(rowData);
		this.model.getCurrentTableModel().setIsModified(true);
		this.model.setIsModified(true);
	}

	public void removeItemFromModel() {
		int selectedRow = this.table.getSelectedRow();
		if (selectedRow != -1) {
			int caloriesValue = Integer.valueOf((String)this.table.getValueAt(selectedRow, 4));
			this.model.getCurrentTableModel().decreaseTotalCalories(caloriesValue);
			this.model.decreaseTotalCalories(caloriesValue);
			this.model.getCurrentTableModel().removeRow(selectedRow);
			this.model.getCurrentTableModel().setIsModified(true);
			this.model.setIsModified(true);
		}
	}

	public void changeCurrentTable(int index) {
		this.model.setCurrentTableModel(index);		
	}
	
	public void addItemToModelFromPopupWindow() {
		this.inputWizardTable.getSelectionModel().setSelectionInterval(0, 0);
		String[] options = {"Select","Cancel"};
		int selectedOption = JOptionPane.showOptionDialog(this.getParent(), this.inputWizardPopupPanel, "Input Wizard", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		if (selectedOption == JOptionPane.OK_OPTION) {
			int selectedRow = this.inputWizardTable.getSelectedRow();
			if (selectedRow != -1) {
				String food = (String)this.inputWizardTable.getValueAt(selectedRow, 0);
				this.food.setText(food);
				String calories = (String)this.inputWizardTable.getValueAt(selectedRow, 1);
				this.calories.setText(calories);
				String fat = (String)this.inputWizardTable.getValueAt(selectedRow, 2);
				this.fat.setText(fat);
				String sugar = (String)this.inputWizardTable.getValueAt(selectedRow, 3);
				this.sugar.setText(sugar);
				String water = (String)this.inputWizardTable.getValueAt(selectedRow, 4);
				this.water.setText(water);
			}
		}
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

	public void attachInputWizardButtonListener(String actionCommand, ActionListener listener) {
		this.inputWizard.setActionCommand(actionCommand);
		this.inputWizard.addActionListener(listener);
	}
	
	public void repaintTable() {
		this.table.setModel(this.model.getCurrentTableModel());
	}
}
