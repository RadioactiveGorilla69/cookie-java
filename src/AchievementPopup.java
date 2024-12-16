import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AchievementPopup {
	private JButton text;

	public AchievementPopup(String AchievementName, JPanel panel, CookieClickerGame game) {
		text = new JButton(AchievementName);
		text.setBounds(668, 635, 200, 100);
		text.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		
		text.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.remove(text);
				//text.setVisible(false);
				panel.revalidate();
				panel.repaint();

			}
		});
		panel.add(text);
	}
}
