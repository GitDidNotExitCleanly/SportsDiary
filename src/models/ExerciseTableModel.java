package models;

import javax.swing.table.DefaultTableModel;

public class ExerciseTableModel extends DefaultTableModel {

	private static final String[] colHeadings = { "Muscle Group", "Exercise", "Rating", "Time", "Difficulty" };
	private boolean isModified;
	private int totalTime;
	
	public ExerciseTableModel() {
		this.setColumnIdentifiers(colHeadings);
		this.isModified = false;
		this.totalTime = 0;
	}
	
	public boolean isModified() {
		return this.isModified;
	}
	
	public void setIsModified(boolean isModified) {
		this.isModified = isModified;
	}
	
	public int getTotalTime() {
		return totalTime;
	}

	public void increaseTotalTime(int value) {
		this.totalTime += value;
	}
		
	public void decreaseTotalTime(int value) {
		this.totalTime -= value;
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
}