import * as React from "react";
import {observer} from "mobx-react";
import videoStore from "../../store/VideoStore";
import Video from "./Video";
import VideoDTO from "../../model/VideoDTO";


@observer
export default class VideoGallery extends React.Component<{}, {}> {
    render() {
        document.title = "Video";
        let videoThumbnailHtmls : Array<any> = [];

        videoStore.videoGallery.forEach((video: VideoDTO, index: number) => {
            videoThumbnailHtmls.push( //videoName bude string ime videa
                <Video videoName={video.id} isVideoThumbnail={true} playing={false} width={"360px"} height={"240px"} onStart={() => videoStore.chooseVideo(video.id)}
                       key={index}/>
                )
        });

        return (
            <div className="video-gallery">
                {
                    videoStore.showChosenVideo
                        ? <Video videoName={videoStore.chosenVideoId} isVideoThumbnail={false} playing={true} width={"1024px"} height={"720px"} onStart={() => {}}/>
                        : <div/>
                }

                <div>{videoThumbnailHtmls}</div>
            </div>
        );
    }

    componentDidMount(): void {
        videoStore.getVideoGallery();
    }
}