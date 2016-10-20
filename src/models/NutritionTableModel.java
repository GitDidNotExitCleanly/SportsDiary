package models;

import javax.swing.table.DefaultTableModel;

public class NutritionTableModel extends DefaultTableModel {

	private static final String[] colHeadings = { "Food", "Meal Type", "Category", "Time", "Calories", "Fat", "Sugar", "Water" };

	private boolean isModified;
	private int totalCalories;
	
	public NutritionTableModel() {
		this.setColumnIdentifiers(colHeadings);
		this.isModified = false;
		this.totalCalories = 0;
	}
	
	public boolean isModified() {
		return this.isModified;
	}
	
	public void setIsModified(boolean isModified) {
		this.isModified = isModified;
	}
	
	public int getTotalCalories() {
		return totalCalories;
	}
	
	public void increaseTotalCalories(int value) {
		this.totalCalories += value;
	}

	public void decreaseTotalCalories(int value) {
		this.totalCalories -= value;
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}