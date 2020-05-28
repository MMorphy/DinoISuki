import {observable} from "mobx";

export default class UserSessionDTO {
	@observable id: number = 0;
	@observable username: string = "";
	@observable userAgent: string = "";
	@observable sessionStart: Date = new Date();
	@observable sessionEnd: Date | undefined;
}