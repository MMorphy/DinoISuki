package hr.go2.play.entities;

public class SubscriptionStatistics {
	private String yyyymm;
	private String subscriptionType;
	private String sumPerType;

	public SubscriptionStatistics() {
	}

	public SubscriptionStatistics(String yyyymm, String subscriptionType, String sumPerType) {
		this.yyyymm = yyyymm;
		this.subscriptionType = subscriptionType;
		this.sumPerType = sumPerType;
	}

	public String getYyyymm() {
		return yyyymm;
	}

	public void setYyyymm(String yyyymm) {
		this.yyyymm = yyyymm;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public String getSumPerType() {
		return sumPerType;
	}

	public void setSumPerType(String sumPerType) {
		this.sumPerType = sumPerType;
	}

}
