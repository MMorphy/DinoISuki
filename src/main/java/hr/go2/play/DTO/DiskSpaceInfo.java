package hr.go2.play.DTO;

public class DiskSpaceInfo {
	private String partition;
	private String available;
	private String used;
	private String total;

	public DiskSpaceInfo() {
	}

	public DiskSpaceInfo(String partition, String available, String used, String total) {
		this.partition = partition;
		this.available = available;
		this.used = used;
		this.total = total;
	}

	public String getPartition() {
		return partition;
	}

	public void setPartition(String partition) {
		this.partition = partition;
	}

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

}
