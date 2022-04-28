package customerEntity;

import java.util.ArrayList;

import customerUtils.Size;

public class Airplane {
	private final long tailNum;
	private long economy;
	private long business;
	private long firstClass;
	// Constructor
	public Airplane(long tailNum, long economy, long business, long firstClass) {
		this.tailNum = tailNum;
		this.economy = economy;
		this.business = business;
		this.firstClass = firstClass;
	}
	
	public Airplane(long tailNum) {
		this.tailNum = tailNum;
	}
	
	public long getTailNum() {
		return tailNum;
	}
	
	public long getEconomy() {
		return economy;
	}

	public long getBusiness() {
		return business;
	}

	public long getFirstClass() {
		return firstClass;
	}
	
	public long getCapacity() {
		return economy + business + firstClass;
	}
	
	public void setEconomy(long economy) {
		this.economy = economy;
	}

	public void setBusiness(long business) {
		this.business = business;
	}

	public void setFirstClass(long firstClass) {
		this.firstClass = firstClass;
	}

	@Override
	public String toString() {
		return String.format("Airplane [tailNum=%s, economy=%s, business=%s, firstClass=%s]", tailNum, economy,
				business, firstClass);
	}

}
