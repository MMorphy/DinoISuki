import {observable} from "mobx";

export default class VideoDTO {

    @observable id : string = "";
    @observable locationName: string = "";
    @observable startedTimestamp: string = "";
}