package models;

import java.util.ArrayList;

public class ExerciseModel {

	// singleton pattern
	private static ExerciseModel instance;
	
	public static ExerciseModel getInstance() {
		if (instance == null) {
			instance = new ExerciseModel();
		}
		return instance;
	}

	private boolean isModified;
	private ArrayList<ExerciseTableModel> tableModelList;
	private ExerciseTableModel currentTableModel;
	
	private int totalTime;
	
	private ExerciseModel() {
		super();

		this.isModified = false;
		this.tableModelList = new ArrayList<ExerciseTableModel>(31);
		this.currentTableModel = null;
		
		this.totalTime = 0;
	}

	public boolean isModified() {
		return this.isModified;
	}

	public void setIsModified(boolean isModified) {
		this.isModified = isModified;
	}
	
	public void addTableModel(ExerciseTableModel model) {
		this.tableModelList.add(model);
	}
	
	public void removeAllTableModel() {
		this.isModified = false;
		this.tableModelList.clear();
		this.currentTableModel = null;
		this.totalTime = 0;
	}

	public ExerciseTableModel getCurrentTableModel() {
		return this.currentTableModel;
	}
	
	public void setCurrentTableModel(int index) {
		this.currentTableModel = this.tableModelList.get(index);
	}
	
	public ExerciseTableModel getTableModelAt(int index) {
		return this.tableModelList.get(index);
	}
	
	public int getTotalTime() {
		return this.totalTime;
	}
	
	public void increaseTotalTime(int value) {
		this.totalTime += value;
	}
	
	public void decreaseTotalTime(int value) {
		this.totalTime -= value;
	}
}
