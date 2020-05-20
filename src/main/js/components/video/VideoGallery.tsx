import * as React from "react";
import {observer} from "mobx-react";
import videoStore from "../../store/VideoStore";
import Video from "./Video";
import VideoSlideshow from "./VideoSlideshow";
import {Modal, Button} from "react-bootstrap";
import VideoDTO from "../../model/VideoDTO";

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
                    ?	<div>

							<Modal show={videoStore.showChosenVideo} onHide={() => {}} size='lg' autoFocus keyboard centered aria-labelledby="contained-modal-title-vcenter" className='edit-modal-color'>
				                <Modal.Header>
				                    <Modal.Title className='font-color'>{videoStore.chosenVideoDTO.locationName} {new Date(videoStore.chosenVideoDTO.startedTimestamp.substring(0, videoStore.chosenVideoDTO.startedTimestamp.indexOf('.')) + 'Z').toLocaleString()}</Modal.Title>
				                </Modal.Header>
				                <Modal.Body>
				                    <Video videoName={videoStore.chosenVideoDTO.fileName} playing={true} width={"640px"} height={"480px"} onStart={() => {}}/>
				                </Modal.Body>
				                <Modal.Footer className='admin-notifications-modal-footer'>
				                    <Button className='admin-notifications-modal-footer-cancel-button' onClick={() => videoStore.chooseVideo(new VideoDTO())}><b>Zatvori</b></Button>
				                </Modal.Footer>
				            </Modal>

						</div>
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
    }
}