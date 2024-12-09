import java.util.ArrayList;

public class Upgrade {

	private double buildingMult;
	private double totalMult;
	private int price;
	private String upgradeName;
	private int upgradeNumber;

	public Upgrade(String upgradeName, double buildingMult, double totalMult, int price, int upgradeNumber) {
		this.upgradeName = upgradeName;
		this.buildingMult = buildingMult;
		this.totalMult = totalMult;
		this.price = price;
		this.upgradeNumber = upgradeNumber;
	}

	//FIX THIS |
	//FIX THIS |
	//FIX THIS |
	//FIX THIS v
	
	public void buyUpgrade(CookieClickerGame game, Upgrade u) {
		game.addToPurchasedUpgrades(u);
		game.setCurrentCookies(game.getCurrentCookies() - u.price);
	}

	public double getTotalMult() {
		return totalMult;
	}

	public double getBuildingMult() {
		return buildingMult;
	}

	public String getUpgradeName() {
		return upgradeName;
	}

	public int getPrice() {
		return price;
	}
	
}
