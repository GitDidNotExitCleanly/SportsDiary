package models;

import java.util.ArrayList;


public class NutritionModel {

	// singleton pattern
	private static NutritionModel instance;
	
	public static NutritionModel getInstance() {
		if (instance == null) {
			instance = new NutritionModel();
		}
		return instance;
	}

	private boolean isModified;
	private ArrayList<NutritionTableModel> tableModelList;
	private NutritionTableModel currentTableModel;
	
	private int totalCalories;
	
	private NutritionModel() {
		super();
		
		this.isModified = false;
		this.tableModelList = new ArrayList<NutritionTableModel>(31);
		this.currentTableModel = null;
		
		this.totalCalories = 0;
	}

	public boolean isModified() {
		return this.isModified;
	}

	public void setIsModified(boolean isModified) {
		this.isModified = isModified;
	}
	
	public void addTableModel(NutritionTableModel model) {
		this.tableModelList.add(model);
	}
	
	public void removeAllTableModel() {
		this.isModified = false;
		this.tableModelList.clear();
		this.currentTableModel = null;
		this.totalCalories = 0;
	}

	public NutritionTableModel getCurrentTableModel() {
		return this.currentTableModel;
	}
	
	public void setCurrentTableModel(int index) {
		this.currentTableModel = this.tableModelList.get(index);
	}
	
	public NutritionTableModel getTableModelAt(int index) {
		return this.tableModelList.get(index);
	}
	
	public int getTotalCalories() {
		return this.totalCalories;
	}
		
	public void increaseTotalCalories(int value) {
		this.totalCalories += value;
	}
	
	public void decreaseTotalCalories(int value) {
		this.totalCalories -= value;
	}
}
