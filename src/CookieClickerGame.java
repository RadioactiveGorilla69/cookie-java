import java.util.ArrayList;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.*;

public class CookieClickerGame {

	private int currentCookies = 0;
	private int lifeTimeCookies = 0;
	private Building[] buildings = new Building[5];
	private ArrayList<Upgrade> availableUpgrades = new ArrayList<Upgrade>();
	private ArrayList<Upgrade> purchasedUpgrades = new ArrayList<Upgrade>();
	private Achievement[] achievements = new Achievement[6];
	private ArrayList<Achievement> earnedAchievements = new ArrayList<Achievement>();
	
	Upgrade test = new Upgrade("Test", 1, 100, 5, 0);
	Upgrade cursorTest = new Upgrade("Cursor Test", 1, 2, 20, 1);
	private UpgradeButton cursorTestButton;


	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(1536, 864);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(0, 0, 1536, 864);
		panel.setBackground(new Color(50,130,200));

		JLabel labelCookies = new JLabel();
		labelCookies.setBounds(50, 100, 400, 100);
		labelCookies.setOpaque(true);
		labelCookies.setBackground(new Color(111, 111, 111, 180));
		labelCookies.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		labelCookies.setHorizontalAlignment(JLabel.CENTER);
		frame.add(labelCookies);

		frame.add(panel);

		CookieClickerGame game = new CookieClickerGame();
		Cookie bigCookie = new Cookie(panel, game);

		game.createBuildings(game);
		game.createBuildingButtons(panel, game);
		
		game.createAchievements(game);

		game.availableUpgrades.add(game.test);
		game.availableUpgrades.add(game.cursorTest);
		UpgradeButton testButton = new UpgradeButton(0, game.test, panel, game);
		game.cursorTestButton = new UpgradeButton(1, game.cursorTest, panel, game);
		game.cursorTestButton.setUpgradeVisible(false);

		panel.setComponentZOrder(labelCookies, 1);
		frame.setVisible(true);

		while(true) {
			game.addAvailableUpgrades();
			game.checkAchievements(panel, game);
			game.updateCookies();
			int cPS = game.makeCookiesPerSecond(game.buildings, game.purchasedUpgrades);
			labelCookies.setText("<html>Cookies: " + game.getCurrentCookies() + "<br><span style='font-size:14px;'>per second: " + cPS + "</html>");


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

	public void updateCookies() {
		setCurrentCookies(getCurrentCookies() + (makeCookiesPerSecond(getBuildings(), getPurchasedUpgrades())));
		setLifeTimeCookies(getCurrentCookies() + (makeCookiesPerSecond(getBuildings(), getPurchasedUpgrades())));
	}

	public int getCurrentCookies() {
		return currentCookies;
	}
	
	public int getLifeTimeCookies() {
		return lifeTimeCookies;
	}

	public void setCurrentCookies(double currentCookies) {
		this.currentCookies = (int)currentCookies;
	}
	
	public void setLifeTimeCookies(double lifeTimeCookies) {
		this.lifeTimeCookies = (int)lifeTimeCookies;
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

	public void addAvailableUpgrades() {
		if(purchasedUpgrades.contains(availableUpgrades.get(1)) == false) {
			if(buildings[0].getBuildingCount() > 9) {
				cursorTestButton.setUpgradeVisible(true);
			}
		}

	}

	public double getPurchasedUpgradeTotalPercent(ArrayList<Upgrade> purchasedUpgrades) {
		double totalMult = 1;
		for(int i = 0; i < purchasedUpgrades.size(); i++) {
			totalMult *= purchasedUpgrades.get(i).getTotalMult();
		}
		return totalMult;
	}

	public double getPurchasedUpgradeCursorPercent(ArrayList<Upgrade> purchasedUpgrades) {
		double cursorMult = 1;
		for(int i = 0; i < purchasedUpgrades.size(); i++) {
			if(purchasedUpgrades.get(i).getUpgradeNumber() == 1) {
				cursorMult *= purchasedUpgrades.get(i).getBuildingMult();
			}
		}
		return cursorMult;
	}

	public void checkAchievements(JPanel panel, CookieClickerGame game) {
		if(!earnedAchievements.contains(getAchievements()[0]) && getLifeTimeCookies() > 0) {
			earnedAchievements.add(getAchievements()[0]);
			new AchievementPopup(getAchievements()[0].getName(), panel, game);
		}
		if(!earnedAchievements.contains(getAchievements()[1]) && getLifeTimeCookies() > 9) {
			earnedAchievements.add(getAchievements()[1]);
			new AchievementPopup(getAchievements()[1].getName(), panel, game);
		}
		if(!earnedAchievements.contains(getAchievements()[2]) && totalBuildingCount() > 0) {
			earnedAchievements.add(getAchievements()[2]);
			new AchievementPopup(getAchievements()[2].getName(), panel, game);
		}
		if(!earnedAchievements.contains(getAchievements()[3]) && totalBuildingCount() > 9) {
			earnedAchievements.add(getAchievements()[3]);
			new AchievementPopup(getAchievements()[3].getName(), panel, game);
		}
		if(!earnedAchievements.contains(getAchievements()[4]) && buildings[1].getBuildingCount() > 0) {
			earnedAchievements.add(getAchievements()[4]);
			new AchievementPopup(getAchievements()[4].getName(), panel, game);
		}
	}

	public Achievement[] getAchievements() {
		return achievements;
	}

	public void setAchievements(Achievement[] achievements) {
		this.achievements = achievements;
	}
	
	public void createAchievements(CookieClickerGame game) {
		achievements[0] = new Achievement("Bake a cookie", 0);
		achievements[1] = new Achievement("Bake ten cookies", 1);
		achievements[2] = new Achievement("Buy a building", 2);
		achievements[3] = new Achievement("Buy ten buildings", 3);
		achievements[4] = new Achievement("Buy a grandma", 4);
		achievements[5] = new Achievement("Bake one million cookies", 5);
	}
	
	public int totalBuildingCount() {
		int sum = 0;
		for(int i = 0; i < buildings.length; i++) {
			sum += buildings[i].getBuildingCount();
		}
		return sum;
	}
	
}
