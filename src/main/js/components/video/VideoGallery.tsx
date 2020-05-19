import * as React from "react";
import {observer} from "mobx-react";
import videoStore from "../../store/VideoStore";
import Video from "./Video";
import VideoSlideshow from "./VideoSlideshow";

@observer
export default class VideoGallery extends React.Component<{}, {}> {
    render() {
        document.title = "Video";

        let videoSlideshowHtmls: Array<any> = [];

        videoStore.videoDates.forEach((date: any, index: number) => {
            videoSlideshowHtmls.push(
                <VideoSlideshow date={date} index={index} key={index}/>
            );
        });

        return (
            <div className="video-gallery">
                {
					videoStore.showChosenVideo
                    ?	<div><Video videoName={videoStore.chosenVideoId} playing={true} width={"1024px"} height={"720px"} onStart={() => {}}/></div>
					: 	<div/>
                }
                {
                    videoSlideshowHtmls
                }
            </div>
        );
    }

    componentDidMount(): void {
        videoStore.getVideoGallery();
    }

	componentDidUpdate(): void {
        window.scrollTo(0, 100);
    }
}