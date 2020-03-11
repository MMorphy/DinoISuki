package hr.go2.play.DTO;

import java.util.List;

import hr.go2.play.entities.SubscriptionStatistics;

public class AdminStatisticsDTO {

	private int numberOfUsers;
	private int numberOfLocations;
	private int numberOfFields;
	private int numberOfCameras;
	private int numberOfActiveVideos;
	private List<SubscriptionStatistics> subscriptionStatistics;
	private List<DiskSpaceInfo> diskSpaceInfo;

	public AdminStatisticsDTO() {
	}

	public int getNumberOfUsers() {
		return numberOfUsers;
	}

	public void setNumberOfUsers(int numberOfUsers) {
		this.numberOfUsers = numberOfUsers;
	}

	public int getNumberOfLocations() {
		return numberOfLocations;
	}

	public void setNumberOfLocations(int numberOfLocations) {
		this.numberOfLocations = numberOfLocations;
	}

	public int getNumberOfFields() {
		return numberOfFields;
	}

	public void setNumberOfFields(int numberOfFields) {
		this.numberOfFields = numberOfFields;
	}

	public int getNumberOfCameras() {
		return numberOfCameras;
	}

	public void setNumberOfCameras(int numberOfCameras) {
		this.numberOfCameras = numberOfCameras;
	}

	public int getNumberOfActiveVideos() {
		return numberOfActiveVideos;
	}

	public void setNumberOfActiveVideos(int numberOfActiveVideos) {
		this.numberOfActiveVideos = numberOfActiveVideos;
	}

	public List<SubscriptionStatistics> getSubscriptionStatistics() {
		return subscriptionStatistics;
	}

	public void setSubscriptionStatistics(List<SubscriptionStatistics> subscriptionStatistics) {
		this.subscriptionStatistics = subscriptionStatistics;
	}

	public List<DiskSpaceInfo> getDiskSpaceInfo() {
		return diskSpaceInfo;
	}

	public void setDiskSpaceInfo(List<DiskSpaceInfo> diskSpaceInfo) {
		this.diskSpaceInfo = diskSpaceInfo;
	}

}
