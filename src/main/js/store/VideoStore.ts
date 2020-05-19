import {action, computed, observable} from "mobx";
import videoRepository from "../repository/VideoRepository";
import {AxiosResponse} from "axios";
import VideoDTO from "../model/VideoDTO";

class VideoStore {

    @observable videoGallery : Array<VideoDTO> = [];
    @observable chosenVideoId: string = "";
    @observable showChosenVideo: boolean = false;

    getVideoGallery() {
        videoRepository.getVideoGallery()
            .then(action((response: AxiosResponse) => {
                this.videoGallery = response.data;
            }))
    }

    @computed get videoDates() { //možda bude ih trebalo sortati
        let datesSet = new Set();
        this.videoGallery.map((video: VideoDTO) => new Date(video.startedTimestamp))
                         .sort((a: any, b: any) => a - b)
                         .map((date: Date) => this.mapDateToDateString(date))
                         .forEach((date: string) => datesSet.add(date));

        return Array.from(datesSet);
    }

    areDateAndTimestampStringsEqual(date: string, videoTimestamp: string) {
        let videoDate = this.mapTimestampStringToDateString(videoTimestamp);

        return date === videoDate;
    }

    @action
    chooseVideo(videoId: string) {
		this.showChosenVideo = false;
		setTimeout( () => this.chooseMyVideo(videoId), 500);
    }
	
	@action
    private chooseMyVideo = (videoId: string) =>  {
		 this.chosenVideoId = videoId;
        this.showChosenVideo = true;
	}

    private mapTimestampStringToDateString(timestamp: string) {
        let split = timestamp.split("T")[0].split("-");

        return split[2] + "." + split[1] + "." + split[0] + ".";
    }

    private mapDateToDateString(date: Date) {
        return ("0" + date.getDate()).slice(-2) + "." + ("0" + (date.getMonth() + 1)).slice(-2) + "." + date.getFullYear() + ".";
    }
}

const videoStore = new VideoStore();
export default videoStore;