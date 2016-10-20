package gui;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;

import util.Util;

public class Splash {

	private final long MIN_WAIT_TIME = 500;

	private long start;

	private JWindow splash;

	public Splash() {
		this.start = 0;
		this.splash = null;

		ImageIcon imageIcon = Util.createImageIcon("/resources/Running_Icon.jpg", null);

		this.splash = new JWindow();
		JPanel panel = new JPanel();
		JLabel picLabel = new JLabel(imageIcon);
		panel.add(picLabel);
		this.splash.setContentPane(panel);
		this.splash.pack();
		this.splash.setLocationRelativeTo(null);
	}

	public void show() {
		this.start = System.currentTimeMillis();
		this.splash.setVisible(true);
	}

	public void hide() {
		long end = System.currentTimeMillis();
		long elapsedTime = end - this.start;
		if (elapsedTime < MIN_WAIT_TIME) {
			try {
				Thread.sleep(MIN_WAIT_TIME - elapsedTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.start = 0;
		this.splash.setVisible(false);
	}
}
