import React from "react";
import {Card} from "react-bootstrap";
import HorizontalScroll from 'react-scroll-horizontal';
import Video from "./Video";
import VideoDTO from "../../model/VideoDTO";
import videoStore from "../../store/VideoStore";

interface VideoSlideshowProps {
    date: string;
    index: number;
}

export default class VideoSlideshow extends React.Component<VideoSlideshowProps, {}> {
    render() {
        let videoThumbnailHtmls : Array<any> = [];

        videoStore.videoGallery.forEach((video: VideoDTO, index: number) => {
            if (videoStore.areDateAndTimestampStringsEqual(this.props.date, video.startedTimestamp)) {
                videoThumbnailHtmls.push(
                    <div className='video-horizontal-scroll-child-width' key={index}>
                        <Video videoName={video.id} isVideoThumbnail={true} playing={false} width={"360px"} height={"240px"}
                               onStart={() => videoStore.chooseVideo(video.id)}/>
                        <br/>
                        Lokacija: {video.locationName}
                        <br/>
                        Vrijeme: {this.convertTimestampString(video.startedTimestamp)}
                    </div>
                );
            }
        });

        return (
            <Card border="danger">
                <Card.Header className="video-card-header-background">
                    <h5>Ponedjeljak - {this.props.date}</h5> {/*ubaciti dan u tjednu*/}
                </Card.Header>
                    <Card.Body>
                        <div className='video-horizontal-scroll-parent-width'>
                            <HorizontalScroll>
                                {videoThumbnailHtmls}
                            </HorizontalScroll></div>
                    </Card.Body>
            </Card>
        );
    }

    private convertTimestampString(timestamp: string) {
        let date = timestamp.split("T")[0].split("-");
        let time = timestamp.split("T")[1];

        return date[2] + "." + date[1] + "." + date[0] + ". " + time.split(".")[0];
    }
}