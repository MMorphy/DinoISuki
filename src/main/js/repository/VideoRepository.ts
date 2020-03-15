import axios, {AxiosResponse} from 'axios';

class VideoRepository {

    getVideoGallery(): Promise<AxiosResponse> {
        return axios.get("/api/video/getAllVideos");
    }
}

const videoRepository = new VideoRepository();
export default videoRepository;