import {observable} from "mobx";

export default class AdminSubscriptionTypesDTO {
	
	@observable id: string = '';
	@observable name: string = '';
	@observable price: number = 0;

}
