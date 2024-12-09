import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuildingButton {

	private JButton button;

	public BuildingButton(int buildingNumber, JPanel panel, CookieClickerGame game) {
		Building building = game.getBuildings()[buildingNumber];
		button = new JButton(building.getName() + " - Price: " + building.getPrice());
		button.setBounds(586, 0 + (buildingNumber*100), 200, 100);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Building building = game.getBuildings()[buildingNumber];

				if (game.getCurrentCookies() >= building.getPrice()) {
					building.buyBuilding(game, buildingNumber, 1, building.getPrice());

					int newPrice = (int)(building.getPrice() * 1.1);
					building.setPrice((int)newPrice);
					button.setText(building.getName() + " - Price: " + newPrice);
				} 

				else {

				}
			}
		});

		panel.add(button);

	}
}
