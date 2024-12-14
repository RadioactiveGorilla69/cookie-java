import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import java.awt.geom.Ellipse2D;

public class Cookie {

	private JButton button;
	private Queue<JLabel> queue = new LinkedList<>();
	private boolean isPulsing = true;
	private int scaleFactor = 0;
	private Timer pulseTimer;

	public Cookie(JPanel panel, CookieClickerGame game) {

		ImageIcon buttonImage = new ImageIcon(getClass().getResource("cookie.png"));
		Image scaledImage = buttonImage.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		button = new JButton() {
			@Override
			public boolean contains(int x, int y) {
				Ellipse2D circle = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
				return circle.contains(x, y);
			}
		};
		button.setIcon(scaledIcon);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setBounds(150, 250, 200, 200);

		panel.add(button);
		//panel.setComponentZOrder(button, 0);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.setCurrentCookies(game.getCurrentCookies() + game.makeCookiesPerClick(game.getPurchasedUpgrades()));
				String cPC = "+" + game.makeCookiesPerClick(game.getPurchasedUpgrades());
				createFloatingLabel(panel, cPC);
				pulse(scaledIcon);
			}
		});
	}

	private void createFloatingLabel(JPanel panel, String text) {
		Point mousePos = MouseInfo.getPointerInfo().getLocation();
		SwingUtilities.convertPointFromScreen(mousePos, panel);

		JLabel floatingLabel = new JLabel(text);
		floatingLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		floatingLabel.setForeground(Color.WHITE);
		floatingLabel.setOpaque(false);
		floatingLabel.setBounds(mousePos.x - 15, mousePos.y - 60, 10, 10);

		panel.add(floatingLabel);
		panel.setComponentZOrder(floatingLabel, 0);
		panel.repaint();

		queue.add(floatingLabel);

		if (queue.size() > 5) {
			panel.remove(queue.poll());
			panel.remove(queue.poll());
			panel.remove(queue.poll());
			panel.remove(queue.poll());
			panel.remove(queue.poll());
			panel.repaint();
		}

		Timer timer = new Timer(20, new ActionListener() {
			private int alpha = 255;
			private int y = mousePos.y - 15;
			//Color c = new Color(255, 255, 255, alpha);

			@Override
			public void actionPerformed(ActionEvent e) {
				y -= 2;
				floatingLabel.setBounds(mousePos.x - 10, y, 40, 20);

				alpha -= 5;
				if (alpha < 0) alpha = 0; 
				floatingLabel.setForeground(new Color(255, 255, 255, alpha)); 

				if (alpha == 0) {
					((Timer) e.getSource()).stop();
					panel.remove(floatingLabel);
					panel.repaint();
				}
			}
		});
		timer.start();

	}

	public void pulse(ImageIcon icon) {
		if(pulseTimer != null && pulseTimer.isRunning()) {
			pulseTimer.stop();
		}

		pulseTimer = new Timer(10, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(isPulsing) {
					scaleFactor += 10;
					if(scaleFactor == 30) {
						isPulsing = false;
					}
				} else {
					scaleFactor -= 10;
					if(scaleFactor == 0) {
						isPulsing = true;
						pulseTimer.stop();
					}
				}

				Image scaledImage = icon.getImage().getScaledInstance(200-scaleFactor, 200-scaleFactor, Image.SCALE_SMOOTH);
				button.setIcon(new ImageIcon(scaledImage));
			}
		});
		pulseTimer.start();
	}

}

