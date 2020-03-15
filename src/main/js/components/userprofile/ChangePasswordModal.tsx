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
                    <Modal.Title className='font-color'>Change password</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <FormGroup controlId="password">
                        <Col sm={{span: 12}}>
                            <FormLabel><h5 className="font-color font-size">Old password</h5></FormLabel>
                        </Col>
                        <Col sm={{span: 12}}>
                            <FormControl type="password" placeholder="Password" onChange={(e: any) => {userStore.changeOldPasswordForUpdate(e.target.value)}}/>
                        </Col>
                    </FormGroup>
                    {
                        appStore.showWrongOldPasswordErrorMessage
                            ? <ErrorMessage errorMessage="Wrong password!" loginButton={false}/>
                            : <div></div>
                    }
                    <FormGroup controlId="password">
                        <Col sm={{span: 12}}>
                            <FormLabel><h5 className="font-color font-size">New password</h5></FormLabel>
                        </Col>
                        <Col sm={{span: 12}}>
                            <FormControl type="password" placeholder="Password" onChange={(e: any) => {userStore.changeNewPasswordForUpdate(e.target.value)}}/>
                        </Col>
                    </FormGroup>

                    <FormGroup controlId="password">
                        <Col sm={{span: 12}}>
                            <FormLabel><h5 className="font-color font-size">Confirm new password</h5></FormLabel>
                        </Col>
                        <Col sm={{span: 12}}>
                            <FormControl type="password" placeholder="Password" onChange={(e: any) => {userStore.changeConfirmedPasswordForUpdate(e.target.value)}}/>
                        </Col>
                    </FormGroup>
                    {
                        appStore.showDifferentConfirmedPasswordErrorMessage
                            ? <ErrorMessage errorMessage="Passwords don't match!" loginButton={false}/>
                            : <div/>
                    }

                    {
                        appStore.showUnsuccessfulPasswordUpdateMessage
                            ? <ErrorMessage errorMessage="Your password is not updated! Try again." loginButton={false}/>
                            : <div/>
                    }

                    {
                        appStore.showSuccessfulPasswordUpdateMessage
                            ? <ErrorMessage errorMessage="Password is successfully updated!" loginButton={false}/>
                            : <div/>
                    }
                </Modal.Body>
                <Modal.Footer>
                    <Button className='login-registration-button-color' onClick={() => userStore.updatePassword()}><b>Submit</b></Button>
                    <Button className='login-registration-button-color' onClick={() => this.onModalClose()}><b>Close</b></Button>
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