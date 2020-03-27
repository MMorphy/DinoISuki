import {observable} from "mobx";

export default class SubscriptionDTO {
	
	@observable id: number = 0;
	@observable valid: boolean = false;
	@observable validFrom: Date = new Date();
	@observable validTo: Date = new Date();
	@observable subscriptionTypeName: string = "";
	@observable username: string = ""
	
}

