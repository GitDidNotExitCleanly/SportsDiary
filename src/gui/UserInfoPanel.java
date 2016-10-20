package gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import models.UserInfoModel;
import util.Util;

public class UserInfoPanel extends JPanel {

	private UserInfoModel model;

	private JComboBox<Integer> usrHeight;
	private JComboBox<Integer> usrWeight;
	private JLabel BMI;

	public UserInfoPanel(UserInfoModel userInfoModel) {
		this.model = userInfoModel;

		setBorder(BorderFactory.createLineBorder(Color.black));

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 2;
		gbc.weighty = 1.5;
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel smallLogo = createLogo();
		add(smallLogo, gbc);

		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 1;
		add(new JLabel("Height (cm):"), gbc);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 1;
		createHeightComboBox();
		add(this.usrHeight, gbc);

		gbc.fill = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 2;
		add(new JLabel("Weight (kg):"), gbc);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.gridy = 2;
		createWeightComboBox();
		add(this.usrWeight, gbc);

		gbc.fill = GridBagConstraints.CENTER;
		gbc.gridx = 0;
		gbc.gridy = 3;
		add(new JLabel("Your BMI :"), gbc);
		gbc.fill = GridBagConstraints.CENTER;
		gbc.gridx = 1;
		gbc.gridy = 3;
		createBMIPanel();
		add(this.BMI, gbc);

		// attach listeners
		this.usrHeight.addActionListener(new RecalculateBMIListener());
		this.usrWeight.addActionListener(new RecalculateBMIListener());
	}

	/*************************** VIEW PART ***************************/

	private JLabel createLogo() {
		ImageIcon imageIcon = Util.createImageIcon("/resources/welcome.jpg", null);
		return new JLabel(imageIcon);
	}

	private void createHeightComboBox() {
		Integer[] heights = { 150, 160, 170, 180, 190, 200 };
		this.usrHeight = new JComboBox<Integer>(heights);
		this.usrHeight.setSelectedIndex(0);
		this.usrHeight.setSelectedItem(this.model.getHeight());
		DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
		dlcr.setHorizontalAlignment(SwingConstants.CENTER);
		this.usrHeight.setRenderer(dlcr);
	}

	private void createWeightComboBox() {
		Integer[] weights = { 40, 50, 60, 70, 80, 90 };
		this.usrWeight = new JComboBox<Integer>(weights);
		this.usrWeight.setSelectedIndex(0);
		this.usrWeight.setSelectedItem(this.model.getWeight());
		DefaultListCellRenderer dlcr = new DefaultListCellRenderer();
		dlcr.setHorizontalAlignment(SwingConstants.CENTER);
		this.usrWeight.setRenderer(dlcr);
	}

	private void createBMIPanel() {
		int height = (Integer) this.usrHeight.getSelectedItem();
		int weight = (Integer) this.usrWeight.getSelectedItem();
		double BMIValue = calculateBMI(height, weight);
		this.BMI = new JLabel(String.format("%1$,.2f", BMIValue));
	}

	private double calculateBMI(int height, int weight) {
		return weight / (((double) height / 100) * ((double) height / 100));
	}

	/*************************** CONTROLLER PART ***************************/

	private class RecalculateBMIListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int height = (Integer) usrHeight.getSelectedItem();
			model.setHeight(height);
			int weight = (Integer) usrWeight.getSelectedItem();
			model.setWeight(weight);
			double BMIValue = calculateBMI(height, weight);
			BMI.setText(String.format("%1$,.2f", BMIValue));
			BMI.repaint();
		}
	}
}
