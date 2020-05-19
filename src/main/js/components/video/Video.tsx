import React from "react";

interface VideoProps {
    videoName: string;
    width: string;
    height: string;
    onStart: () => void;
    playing: boolean;
}

export default class Video extends React.Component<VideoProps, {}> {

    render() {
		const delimiter: string = "\\";
		let parts = this.props.videoName.split(delimiter);
		const videoLoc: string = "videos/" + parts[parts.length - 1];
        return (
            <div className="video-player-inline">
				{	this.props.playing ?
                 	<video controls autoPlay muted preload="metadata" width={this.props.width} height={this.props.height}>
		    			<source src={videoLoc} type="video/mp4"/>
					</video>
				: <video preload="metadata" width={this.props.width} height={this.props.height}>
		    			<source src={videoLoc} type="video/mp4"/>
					</video>
				}
            </div>
        );
    }

}
