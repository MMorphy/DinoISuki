import React from "react";
import ReactPlayer from "react-player";

interface VideoProps {
    videoName: string;
    isVideoThumbnail: boolean;
    width: string;
    height: string;
    onStart: () => void;
    playing: boolean;
}

export default class Video extends React.Component<VideoProps, {}> {
    private player: any = null;

    ref = (player: any) => {
        this.player = player
    };

    render() {
        return (
            <div className="video-player-inline">
                <ReactPlayer url={`https://localhost:8443/api/video/getVideo/${this.props.videoName}`}
                             light={this.props.isVideoThumbnail}
                             playing={this.props.playing}
                             controls={true}
                             width={this.props.width}
                             height={this.props.height}
                             onReady={this.props.onStart}
                             onSeek={seconds => this.handleSeek(seconds)}
                             ref={this.ref}/>
            </div>
        );
    }

    private handleSeek(seconds: number) {
        this.player.seekTo(seconds, "seconds");
    }
}
