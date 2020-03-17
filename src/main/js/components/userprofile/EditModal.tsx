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
                    <Modal.Title className='font-color'>Uredi profil</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <EditModalFields/>
                    {
                        appStore.showSuccessfulUpdateMessage
                            ? <ErrorMessage errorMessage="Uspješna promjena podataka!" loginButton={false}/>
                            : <div/>
                    }
                    {
                        appStore.showUpdateErrorMessage
                        ? <ErrorMessage errorMessage="Neuspješna promjena podataka!" loginButton={false}/>
                        : <div/>
                    }
                </Modal.Body>
                <Modal.Footer>
                    <Button className='login-registration-button-color' onClick={() => userStore.submitUserUpdate()}><b>Promijeni</b></Button>
                    <Button className='login-registration-button-color' onClick={() => this.onModalClose()}><b>Zatvori</b></Button>
                </Modal.Footer>
            </Modal>
        );
    }

    @action
    private onModalClose() {
        appStore.showSuccessfulUpdateMessage = false;
        appStore.showUpdateErrorMessage = false;
        appStore.changeEditModalVisibility();
        userStore.resetDate();
    }
}