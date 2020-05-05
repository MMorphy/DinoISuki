import {observable} from "mobx";

export default class WorkingHoursDTO {
	
	@observable fromTime: string = '';
	@observable toTime: string = '';
	@observable dayType: string = '';
	
}
