import * as React from "react";
import {observer} from "mobx-react";
import {Button, Modal} from "react-bootstrap";
import appStore from "../../store/AppStore";
import userStore from "../../store/UserStore";
import EditModalFields from "./EditModalFields";
import ErrorMessage from "../utils/ErrorMessage";
import {action} from "mobx";

@observer
export default class EditModal extends React.Component<{}, {}> {
    render() {
        return (
            <Modal show={appStore.isEditModalVisible} onHide={() => {}} size='lg' autoFocus keyboard className='edit-modal-color modal-padding'>
                <Modal.Header>
                    <Modal.Title className='font-color'>Edit profile</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <EditModalFields/>
                    {
                        appStore.showSuccessfulUpdateMessage
                            ? <ErrorMessage errorMessage="You have successfully updated your informations!" loginButton={false}/>
                            : <div/>
                    }
                    {
                        appStore.showUpdateErrorMessage
                        ? <ErrorMessage errorMessage="You haven't updated your profile successfully!" loginButton={false}/>
                        : <div/>
                    }
                </Modal.Body>
                <Modal.Footer>
                    <Button className='login-registration-button-color' onClick={() => userStore.submitUserUpdate()}><b>Submit</b></Button>
                    <Button className='login-registration-button-color' onClick={() => this.onModalClose()}><b>Close</b></Button>
                </Modal.Footer>
            </Modal>
        );
    }

    @action
    private onModalClose() {
        appStore.showSuccessfulUpdateMessage = false;
        appStore.showUpdateErrorMessage = false;
        appStore.changeEditModalVisibility();
    }
}