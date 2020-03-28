import {observable} from "mobx";

export default class AdminNotificationDTO {
	
	@observable id: number = 0;
	@observable createdAt: Date = new Date();
	@observable destUser: string = '';
	@observable srcUser: string = '';
	@observable subject: string = '';
	@observable message: string = '';
	@observable notificationType: string = '';

}
