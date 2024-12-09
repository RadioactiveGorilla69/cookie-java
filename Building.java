
public class Building {

	private int buildingCount;
	private int cookiesPerSecond;
	private double price;
	private String buildingName;
	private int buildingNumber;

	public Building(String buildingName, int cookiesPerSecond, int price, int buildingCount) {
		this.buildingCount = buildingCount;
		this.buildingName = buildingName;
		this.setCookiesPerSecond(cookiesPerSecond);
		this.price = price;
	}

	public void buyBuilding(CookieClickerGame game, int buildingNumber, int count, double price) {
		Building building = game.getBuildings()[buildingNumber];
		building.setBuildingCount(building.getBuildingCount() + count);
		game.setCurrentCookies(game.getCurrentCookies() - price);
	}

	public int getCookiesPerSecond() {
		return cookiesPerSecond;
	}
	public void setCookiesPerSecond(int cookiesPerSecond) {
		this.cookiesPerSecond = cookiesPerSecond;
	}

	public int getBuildingCount() {
		return buildingCount;
	}

	public void setBuildingCount(int buildingCount) {
		this.buildingCount = buildingCount;
	}

	public void setBuildings(int buildingCount) {
		this.buildingCount = buildingCount;
	}

	public int getPrice() {
		return (int)price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}

	public String getName() {
		return buildingName;
	}
}
