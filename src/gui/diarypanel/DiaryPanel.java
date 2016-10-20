package gui.diarypanel;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import models.DiaryModel;

public class DiaryPanel extends JPanel {

	private DiaryModel model;

	public ExercisePanel exercisePanel;
	public NutritionPanel nutritionPanel;

	private JButton saveButton;

	public DiaryPanel(DiaryModel diaryModel) {
		this.model = diaryModel;

		setBorder(BorderFactory.createLineBorder(Color.black));

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1;
		gbc.weighty = 10;
		gbc.gridx = 0;
		gbc.gridy = 0;
		exercisePanel = new ExercisePanel(this.model.getExerciseModel());
		nutritionPanel = new NutritionPanel(this.model.getNutritionModel());
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add("Exercise", exercisePanel);
		tabbedPane.add("Nutrition", nutritionPanel);
		tabbedPane.setSelectedComponent(exercisePanel);
		add(tabbedPane, gbc);

		gbc.fill = GridBagConstraints.CENTER;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.saveButton = new JButton("Save All");
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(this.saveButton);
		add(buttonPanel, gbc);
	}
	
	public JButton getSaveButton() {
		return this.saveButton;
	}
}
