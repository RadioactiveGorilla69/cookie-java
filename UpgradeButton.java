import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpgradeButton {

	private JButton button;

	public UpgradeButton(int upgradeNumber, Upgrade upgrade, JPanel panel, CookieClickerGame game) {

		if(upgrade.getBuildingMult() == 1.00) {
			button = new JButton(upgrade.getUpgradeName() + " Boost : " + (upgrade.getTotalMult() - 1.00) * 100 + "%");
		}
		else {
			button = new JButton(upgrade.getUpgradeName() + " Boost: " + (upgrade.getBuildingMult() - 1.00) * 100 + "%");
		}

		button.setBounds(50, 50, 100, 100);

		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(game.getCurrentCookies() >= upgrade.getPrice()) {
					game.addToPurchasedUpgrades(upgrade);
					game.removeFromAvailableUpgrades(upgradeNumber);
					game.setCurrentCookies(game.getCurrentCookies() - upgrade.getPrice());
					button.setVisible(false);
					//game.getPurchasedUpgrades().get(upgradeNumber).buyUpgrade(game, upgrade);
				}
				else {
				}

			}
		});

		panel.add(button);

	}

}
