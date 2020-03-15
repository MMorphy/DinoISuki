import React from "react";
import appStore from "../../store/AppStore";
import {Modal} from "react-bootstrap";
import SecurityOfOnlinePaymentsText from "../utils/SecurityOfOnlinePaymentsText";
import {observer} from "mobx-react";

@observer
export default class SecurityOfOnlinePaymentsModal extends React.Component<{}, {}> {
    render() {
        return (
            <Modal show={appStore.isSecurityOfOnlinePaymentsModalVisible} onHide={() => appStore.changeSecurityOfOnlinePaymentsModalVisibility()} size='lg' autoFocus keyboard className='payment-modal-color modal-padding'>
                <Modal.Header closeButton>
                    <Modal.Title className='font-color'>Security of Online Payments</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <SecurityOfOnlinePaymentsText/>
                </Modal.Body>
            </Modal>
        );
    }
}