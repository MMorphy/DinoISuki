import {observable} from "mobx";

export default class AdminUploadedVideoDTO {

    @observable id : string = "";
    @observable location: string = "";
    @observable uploadedAt: Date = new Date();
	@observable videoName: string = "";
	@observable archived: boolean = false;

}