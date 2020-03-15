import React from "react";
import {Button, Card} from "react-bootstrap";
import appStore from "../../store/AppStore";
import userStore from "../../store/UserStore";
import Dropzone from "react-dropzone";
import {observer} from "mobx-react";

@observer
export default class GeneralInformationCard extends React.Component<{}, {}> {
    render() {
        function showDropzoneOrImage() {
                return (
                    <Dropzone onDrop={files => userStore.uploadProfilePhoto(files[0])} className="image-dropzone">
                        <div>
                            <img src={`data:;base64,${userStore.userProfilePhoto}`} width={200} height={200}/>
                            <p className="profile-photo-message">Click on image to change it</p>
                        </div>
                    </Dropzone>
                );
        }

        return (
            <Card className="my-profile-card">
                <Card.Header>
                    <div className="row">
                        <div className="column my-profile-card-first-column">
                            <h5 className="h5-my-profile-card-title">General Information</h5>
                        </div>
                        <div className="column my-profile-card-second-column">
                            <Button className="edit-profile-button" onClick={() => appStore.changeEditModalVisibility()}><b>Edit profile</b></Button>
                            &nbsp;&nbsp;&nbsp;
                            <Button className="edit-profile-button" onClick={() => appStore.changeChangePasswordModalVisibility()}><b>Change password</b></Button>
                        </div>
                    </div>
                </Card.Header>
                <Card.Body>
                    <div className="row">
                        <div className="column my-profile-card-first-column">
                            <div className="font-margin">
                                <b className="font-color font-size">Username:</b>&nbsp;
                                <b className="secondary-font-color font-size">{userStore.userProfileDto.username}</b>
                            </div>
                            <div className="font-margin">
                                <b className="font-color font-size">Date of Birth:</b>&nbsp;
                                <b className="secondary-font-color font-size">{userStore.userProfileDto.dateOfBirth}</b>
                            </div>
                            <div className="font-margin">
                                <b className="font-color font-size">Email:</b> &nbsp;
                                <b className="secondary-font-color font-size">{userStore.contactInformationProfileDto.email}</b>
                            </div>

                            <div className="font-margin">
                                <b className="font-color font-size">Phone Number:</b>&nbsp;
                                <b className="secondary-font-color font-size">{userStore.contactInformationProfileDto.telephoneNumber}</b>
                            </div>
                        </div>
                        <div className="column my-profile-card-second-column">
                            <div className="dropzone-margin">
                                {showDropzoneOrImage()}
                            </div>
                        </div>
                    </div>
                </Card.Body>
            </Card>
        );
    }
}