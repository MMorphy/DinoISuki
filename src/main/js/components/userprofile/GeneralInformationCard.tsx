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
                            <p className="profile-photo-message">Klikni na sliku za promjenu</p>
                        </div>
                    </Dropzone>
                );
        }

        return (
            <Card className="my-profile-card">
                <Card.Header>
                    <div className="row">
                        <div className="column my-profile-card-first-column">
                            <h5 className="h5-my-profile-card-title">Opće informacije</h5>
                        </div>
                        <div className="column my-profile-card-second-column">
                            <Button className="edit-profile-button" onClick={() => appStore.changeEditModalVisibility()}><b>Uredi profil</b></Button>
                            &nbsp;&nbsp;&nbsp;
                            <Button className="edit-profile-button" onClick={() => appStore.changeChangePasswordModalVisibility()}><b>Promijeni lozinku</b></Button>
                        </div>
                    </div>
                </Card.Header>
                <Card.Body>
                    <div className="row">
                        <div className="column my-profile-card-first-column">
                            <div className="font-margin">
                                <b className="font-color font-size">Korisničko ime:</b>&nbsp;
                                <b className="secondary-font-color font-size">{userStore.userProfileDto.username}</b>
                            </div>
                            <div className="font-margin">
                                <b className="font-color font-size">Datum rođenja:</b>&nbsp;
                                <b className="secondary-font-color font-size">{userStore.userProfileDto.dateOfBirth}</b>
                            </div>
                            <div className="font-margin">
                                <b className="font-color font-size">Email adresa:</b> &nbsp;
                                <b className="secondary-font-color font-size">{userStore.contactInformationProfileDto.email}</b>
                            </div>

                            <div className="font-margin">
                                <b className="font-color font-size">Telefonski broj:</b>&nbsp;
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