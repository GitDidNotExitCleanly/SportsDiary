package models;

public class DiaryModel {

	// singleton pattern
	private static DiaryModel instance;
	
	public static DiaryModel getInstance() {
		if (instance == null) {
			instance = new DiaryModel();
		}
		return instance;
	}
	
	private ExerciseModel exerciseModel;
	private NutritionModel nutritionModel;
	
	private DiaryModel() {
		this.exerciseModel = ExerciseModel.getInstance();
		this.nutritionModel = NutritionModel.getInstance();
	}
	
	public ExerciseModel getExerciseModel() {
		return this.exerciseModel;
	}
	
	public NutritionModel getNutritionModel() {
		return this.nutritionModel;
	}
}
