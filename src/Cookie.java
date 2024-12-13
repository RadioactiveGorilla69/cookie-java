import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;

public class Cookie {

	private JButton button;
	private Queue<JLabel> queue = new LinkedList<>();

	public Cookie(JPanel panel, CookieClickerGame game) {

		ImageIcon buttonImage = new ImageIcon(getClass().getResource("cookie.png"));
		Image scaledImage = buttonImage.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		button = new JButton();
		button.setIcon(scaledIcon);
		button.setBounds(275, 175, 200, 200);
		panel.add(button);
		//panel.setComponentZOrder(button, 0);

		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.setCurrentCookies(game.getCurrentCookies() + game.makeCookiesPerClick(game.getPurchasedUpgrades()));
				String cPC = "+" + game.makeCookiesPerClick(game.getPurchasedUpgrades());
				createFloatingLabel(panel, cPC);
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
		panel.setComponentZOrder(floatingLabel, 2);
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
			private int y = mousePos.y - 60;
			//Color c = new Color(255, 255, 255, alpha);

			@Override
			public void actionPerformed(ActionEvent e) {
				y -= 2;
				floatingLabel.setBounds(mousePos.x - 15, y, 40, 20);

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
}
