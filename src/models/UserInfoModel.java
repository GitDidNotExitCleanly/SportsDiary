package models;

public class UserInfoModel {

	// singleton pattern
	private static UserInfoModel model;

	public static UserInfoModel getInstance() {
		if (model == null) {
			model = new UserInfoModel();
		}
		return model;
	}

	private int height;
	private int weight;
	private boolean isModified;

	private UserInfoModel() {
		// default value
		this.height = 170;
		this.weight = 50;
		this.isModified = false;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		this.isModified = true;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
		this.isModified = true;
	}

	public boolean isModified() {
		return this.isModified;
	}
	
	public void setIsModified(boolean value) {
		this.isModified = value;
	}
}
