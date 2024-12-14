import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BuildingButton {

	private JButton button;

	public BuildingButton(int buildingNumber, JPanel panel, CookieClickerGame game) {
		JLabel tooltip = new JLabel();
		tooltip.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
		Building building = game.getBuildings()[buildingNumber];
		button = new JButton("<html>" + building.getName() + "<br>Price: " + building.getPrice() + " Cookies" + "<br>Owned: " + building.getBuildingCount() + "</html>");
		button.setBounds(1236, 250 + (buildingNumber*120), 300, 120);
		button.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		
		JLabel labelOwned = new JLabel("Owned: " + building.getBuildingCount());
        labelOwned.setBounds(670, 45 + (buildingNumber*100), 180, 15);
		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Building building = game.getBuildings()[buildingNumber];

				if (game.getCurrentCookies() >= building.getPrice()) {
					building.buyBuilding(game, buildingNumber, 1, building.getPrice());

					int newPrice = (int)(building.getPrice() * 1.1);
					building.setPrice((int)newPrice);
					button.setText("<html>" + building.getName() + "<br>Price: " + building.getPrice() + " Cookies" + "<br>Owned: " + building.getBuildingCount() + "</html>");
				} 

				else {

				}
			}
		});

		button.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				
				int cPS = game.makeCookiesPerSecond(game.getBuildings(), game.getPurchasedUpgrades());
				
				if(building.getBuildingCount() == 0) {
					tooltip.setText("???");
				}
				else{
					tooltip.setText("<html>" + building.getBuildingCount() + " " + building.getName() + " making " + game.makeCookiesPerSecond(game.getBuildings(), game.getPurchasedUpgrades()) + " cookies per second" + "<br>" + ((building.getCookiesPerSecond()*building.getBuildingCount()*100*game.getPurchasedUpgradeCursorPercent(game.getPurchasedUpgrades()))/cPS) + "% cookies per second" + "</html>");
				}

				tooltip.setBounds(1036, 250+(120*buildingNumber), 200, 120);
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
