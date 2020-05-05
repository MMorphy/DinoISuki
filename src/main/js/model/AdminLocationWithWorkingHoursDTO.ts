import {observable} from "mobx";
import WorkingHoursDTO from "./WorkingHoursDTO";

export default class AdminLocationWithWorkingHoursDTO {
	
	@observable id: number = 0;
	@observable name: string = '';
	@observable address: string = '';
	@observable workingHours: WorkingHoursDTO[] = [];
	@observable contactTel: string = '';
	@observable contactEmail: string = '';
	
}
