import {observable} from "mobx";

export default class AdminStatisticsDTO {
	
	@observable numberOfUsers: number = 0;
	@observable numberOfLocations: number = 0;
	@observable numberOfFields: number = 0;
	@observable numberOfCameras: number = 0;
	@observable numberOfActiveVideos: number = 0;
	@observable subscriptionStatistics: SubscriptionStatistics[] = [];
	@observable diskSpaceInfo: DiskSpaceInfo[] = [];

}

export class SubscriptionStatistics {
	yyyymm: string = "";
	subscriptionType: string = "";
	sumPerType: string = "";
}

export class DiskSpaceInfo {
	partition: string = "";
	available: string = "";
	used: string = "";
	total: string = "";
}