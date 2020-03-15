import {action, observable} from "mobx";
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

    @action
    chooseVideo(videoId: string) {
        this.chosenVideoId = videoId;
        this.showChosenVideo = true;
    }
}

const videoStore = new VideoStore();
export default videoStore;