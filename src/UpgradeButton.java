import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UpgradeButton {

	private JButton button;

	public UpgradeButton(int upgradeNumber, Upgrade upgrade, JPanel panel, CookieClickerGame game) {
		
		JButton button = new JButton();
		JLabel tooltip = new JLabel();
		tooltip.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		button.setText(upgrade.getUpgradeName());
		button.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		button.setBounds(1236, 100, 100, 100);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(game.getCurrentCookies() >= upgrade.getPrice()) {
					game.addToPurchasedUpgrades(upgrade);
					game.setCurrentCookies(game.getCurrentCookies() - upgrade.getPrice());
					button.setVisible(false);
					//game.getPurchasedUpgrades().get(upgradeNumber).buyUpgrade(game, upgrade);
				}
				else {
				}

			}
		});

		button.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				
				if(upgrade.getBuildingMult() == 1.00) {
					tooltip.setText("<html>x" + upgrade.getTotalMult()+" Total Multiplier" + "<br>Price : " + upgrade.getPrice() + "<html>");;
				}
				else {
					tooltip.setText("<html>x" + upgrade.getBuildingMult()+" Building Multiplier"+ "<br>Price : " + upgrade.getPrice() + "<html>");;
				}

				tooltip.setBounds(1036, 100, 200, 200);
				tooltip.setBackground(new Color(255, 255, 255, 60));
				tooltip.setOpaque(true);
				
				//tooltip.setBorder(BorderFactory.createLineBorder(Color.GREEN));
				tooltip.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
				panel.add(tooltip);
				panel.repaint();

			}

		});
		button.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				panel.remove(tooltip);
				panel.repaint();

			}

		});

		panel.add(button);
		panel.add(tooltip);

	}

}
