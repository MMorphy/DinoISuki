import React from "react";
import {observer} from "mobx-react";
import appStore from "../../store/AppStore";
import {Button, Col, FormControl, FormGroup, FormLabel, Modal} from "react-bootstrap";
import ErrorMessage from "../utils/ErrorMessage";
import userStore from "../../store/UserStore";
import {action} from "mobx";

@observer
export default class ChangePasswordModal extends React.Component<{}, {}>{
    render() {
        return (
            <Modal show={appStore.isChangePasswordModalVisible} onHide={() => {}} size='lg' autoFocus keyboard className='edit-modal-color modal-padding'>
                <Modal.Header>
                    <Modal.Title className='font-color'>Promjena lozinke</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <FormGroup controlId="password" className="edit-modal-input-form-size">
                        <Col>
                            <FormLabel><h5 className="font-color font-size">Stara lozinka</h5></FormLabel>
                        </Col>
                        <Col>
                            <FormControl type="password" onChange={(e: any) => {userStore.changeOldPasswordForUpdate(e.target.value)}}/>
                        </Col>
                    </FormGroup>
                    {
                        appStore.showWrongOldPasswordErrorMessage
                            ? <ErrorMessage errorMessage="Pogrešna lozinka!" loginButton={false}/>
                            : <div></div>
                    }
                    <FormGroup controlId="password" className="edit-modal-input-form-size">
                        <Col>
                            <FormLabel><h5 className="font-color font-size">Nova lozinka</h5></FormLabel>
                        </Col>
                        <Col>
                            <FormControl type="password" onChange={(e: any) => {userStore.changeNewPasswordForUpdate(e.target.value)}}/>
                        </Col>
                    </FormGroup>

                    <FormGroup controlId="password" className="edit-modal-input-form-size">
                        <Col>
                            <FormLabel><h5 className="font-color font-size">Potvrdi novu lozinku</h5></FormLabel>
                        </Col>
                        <Col>
                            <FormControl type="password" onChange={(e: any) => {userStore.changeConfirmedPasswordForUpdate(e.target.value)}}/>
                        </Col>
                    </FormGroup>
                    {
                        appStore.showDifferentConfirmedPasswordErrorMessage
                            ? <ErrorMessage errorMessage="Lozinke ne odgovarajju!" loginButton={false}/>
                            : <div/>
                    }

                    {
                        appStore.showUnsuccessfulPasswordUpdateMessage
                            ? <ErrorMessage errorMessage="Neuspješna promjena lozinke! Pokušaj ponovo." loginButton={false}/>
                            : <div/>
                    }

                    {
                        appStore.showSuccessfulPasswordUpdateMessage
                            ? <ErrorMessage errorMessage="Uspješna promjena lozinke!" loginButton={false}/>
                            : <div/>
                    }
                </Modal.Body>
                <Modal.Footer>
                    <Button className='login-registration-button-color' onClick={() => userStore.updatePassword()}><b>Promijeni</b></Button>
                    <Button className='login-registration-button-color' onClick={() => this.onModalClose()}><b>Zatvori</b></Button>
                </Modal.Footer>
            </Modal>
        );
    }

    @action
    private onModalClose() {
        appStore.showSuccessfulPasswordUpdateMessage = false;
        appStore.showUnsuccessfulPasswordUpdateMessage = false;
        appStore.showDifferentConfirmedPasswordErrorMessage = false;
        appStore.changeChangePasswordModalVisibility();
    }
}