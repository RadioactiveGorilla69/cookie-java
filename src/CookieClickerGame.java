import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.*;

public class CookieClickerGame {

	private int currentCookies = 0;
	private Building[] buildings = new Building[5];
	private ArrayList<Upgrade> availableUpgrades = new ArrayList<Upgrade>();
	private ArrayList<Upgrade> purchasedUpgrades = new ArrayList<Upgrade>();
	private ArrayList<Upgrade> upgradeStorage = new ArrayList<Upgrade>();
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(1536, 864);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 800, 600);
		panel.setBackground(Color.LIGHT_GRAY);

		JLabel labelCurrentCookies = new JLabel();
		labelCurrentCookies.setBounds(275, 100, 200, 50);
		labelCurrentCookies.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
		frame.add(labelCurrentCookies);

		JLabel labelCPS = new JLabel();
		labelCPS.setBounds(275, 125, 300, 50);
		labelCPS.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
		frame.add(labelCPS);

		frame.add(panel);

		CookieClickerGame game = new CookieClickerGame();
		Cookie bigCookie = new Cookie(panel, game);

		game.createBuildings(game);
		
		game.createBuildingButtons(panel, game);

		Upgrade test = new Upgrade("Test", 1, 2, 20, 0);
		//game.upgradeStorage.add(test);
		game.availableUpgrades.add(test);
		UpgradeButton testButton = new UpgradeButton(0, test, panel, game);
		Upgrade cursorTest = new Upgrade("Cursor Test", 1, 2, 20, 1);
		game.upgradeStorage.add(cursorTest);
		
		panel.setComponentZOrder(labelCurrentCookies, 0);
        panel.setComponentZOrder(labelCPS, 0);
		frame.setVisible(true);

		while(true) {
			if(game.getPurchasedUpgrades().size() > 1) System.out.println("true lol");
			game.updateCurrentCookies();
			//System.out.println(game.getBuildings()[0].getPrice() + "	" + game.getBuildings()[0].getBuildingCount());
			labelCurrentCookies.setText("Cookies: " + game.getCurrentCookies());
			int cPS = game.makeCookiesPerSecond(game.buildings, game.purchasedUpgrades);
			labelCPS.setText("Cookies Per Second: " + cPS);
			game.addAvailableUpgrades(game, panel, game.upgradeStorage);
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	public int makeCookiesPerSecond(Building[] buildings, ArrayList<Upgrade> arrayList) {
		int cPS = 0;
		for(int i = 0; i < buildings.length; i++) {
			cPS += ((buildings[i].getBuildingCount()) * (buildings[i].getCookiesPerSecond()));
		}
		for(int i = 0; i < arrayList.size(); i++) {
			cPS *= (arrayList.get(i).getBuildingMult()) * (arrayList.get(i).getTotalMult());
		}
		return cPS;
	}

	public int makeCookiesPerClick(ArrayList<Upgrade> arrayList) {
		int cPC = 1;
		for(int i = 0; i < arrayList.size(); i++) {
			cPC *= (arrayList.get(i).getTotalMult());
		}
		return cPC;
	}

	public int updateCurrentCookies() {
		return setCurrentCookies(getCurrentCookies() + (makeCookiesPerSecond(getBuildings(), getPurchasedUpgrades())));
	}

	public int getCurrentCookies() {
		return currentCookies;
	}

	public int setCurrentCookies(double currentCookies) {
		this.currentCookies = (int)currentCookies;
		return (int)currentCookies;
	}

	public ArrayList<Upgrade> getPurchasedUpgrades() {
		return purchasedUpgrades;
	}

	public ArrayList<Upgrade> getAvailableUpgrades() {
		return availableUpgrades;
	}

	public void addToPurchasedUpgrades(Upgrade u) {
		purchasedUpgrades.add(u);
	}

	public Building[] getBuildings() {
		return buildings;
	}

	public void setBuildings(Building[] buildings) {
		this.buildings = buildings;
	}

	public void removeFromAvailableUpgrades(int upgradeNumber) {
		availableUpgrades.remove(upgradeNumber);
	}
	
	public void createBuildingButtons(JPanel panel, CookieClickerGame game) {
		new BuildingButton(0, panel, game);
		new BuildingButton(1, panel, game);
		new BuildingButton(2, panel, game);
		new BuildingButton(3, panel, game);
		new BuildingButton(4, panel, game);
	}
	
	public void createBuildings(CookieClickerGame game) {
		game.buildings[0] = new Building("Cursor", 1, 10, 0);
		game.buildings[1] = new Building("Grandma", 15, 100, 0);
		game.buildings[2] = new Building("Farm", 50, 1000, 0);
		game.buildings[3] = new Building("Mine", 250, 10000, 0);
		game.buildings[4] = new Building("Factory", 2000, 50000, 0);
	}
	
	public void addAvailableUpgrades(CookieClickerGame game, JPanel panel, ArrayList<Upgrade> upgradeStorage) {
		if(!game.purchasedUpgrades.contains(upgradeStorage.get(0)));
			if(game.buildings[0].getBuildingCount() > 9) {
				game.availableUpgrades.add((Upgrade)upgradeStorage.get(0));
				UpgradeButton cursorTestButton = new UpgradeButton(1, (Upgrade)upgradeStorage.get(0), panel, game);
		}
	}
	
}
