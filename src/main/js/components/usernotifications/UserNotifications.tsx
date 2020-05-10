import React from "react";
import {observer} from "mobx-react";
import notificationsStore from "../../store/NotificationsStore";
import { MDBDataTable, MDBBtn } from 'mdbreact';
import {Card, Button, Col, Form, FormControl, FormGroup, FormLabel} from "react-bootstrap";
import ErrorMessage from "../utils/ErrorMessage";
import {Modal} from "react-bootstrap";
import {action} from "mobx";

@observer
export default class UserNotifications extends React.Component<{}, {modalVisible: boolean, editNotificationFinished: boolean}> {
	constructor(props: any) {
    	super(props);
		notificationsStore.getUserNotifications(sessionStorage.getItem('username')!);
    	this.state = {
			modalVisible: false,
			editNotificationFinished: false
		};
	}
	
	showEditMessageDialog = () => {
		if(event != undefined) {
			let id: number = 0;
			// @ts-ignore
			if(event.target.localName === 'td') {
				// @ts-ignore
				id = parseInt(event.path[1].cells[0].textContent);
			} else {
				// @ts-ignore
				id = parseInt( event.target.parentElement.parentElement.cells[0].textContent );
			}
			if(id !== undefined && id != null) {
				id--;
				notificationsStore.setEditNotification(notificationsStore.userNotificationDTOList[id]);
				this.setState({
			    	modalVisible: true
			    });
			}
		}
	};
	
	updateNotification = async () => {
		this.setState({
	      editNotificationFinished: false
	    });
		notificationsStore.editNotificationHolder("DELETED", "notificationType");
		const success: boolean = await notificationsStore.updateNotification(notificationsStore.editNotification);
		this.setState({
	      editNotificationFinished: true
	    });
		if(success) {
			await notificationsStore.getUserNotifications(notificationsStore.editNotification.destUser);
		}
		this.onModalClose();
	}
	
	updateNotificationToSeen = async () => {
		if(notificationsStore.editNotification.notificationType === 'UNREAD') {
			notificationsStore.editNotificationHolder("SEEN", "notificationType");
			await notificationsStore.updateNotification(notificationsStore.editNotification);
		}
	}
	
    render() {
		document.title = "Korisničke poruke";
		
		// data for table of notifications
		const columns = [
		{
			label: '#',
	        field: 'id',
	        sort: 'asc',
	        width: 10
	  	},
		{
	        label: 'Poslana u',
	        field: 'createdAt',
	        sort: 'desc',
	        width: 30
	  	},
		{
	        label: 'Naslov',
	        field: 'subject',
	        sort: 'asc',
	        width: 30
	  	},
		{
	        label: 'Poruka',
	        field: 'body',
	        width: 140
	  	}
		];
		const rows = [];
		for (var i = 0; i < notificationsStore.userNotificationDTOList.length; i++){
			let createdAtStr: string = notificationsStore.userNotificationDTOList[i].createdAt.toString();
			let createdAt: Date = new Date(createdAtStr.substring(0, createdAtStr.indexOf('.')) + 'Z');
			let messageStr: string = notificationsStore.userNotificationDTOList[i].message;
			if(messageStr.length > 40) {
				messageStr = messageStr.substr(0, 37) + '...';
			}
			let idText: string = String(i + 1);
			if(notificationsStore.userNotificationDTOList[i].notificationType === 'UNREAD') {
				rows.push(
					{
						id: <FormLabel className="user-notifications-table-row-bold">{idText}</FormLabel>,
						createdAt: <FormLabel className="user-notifications-table-row-bold">{createdAt.toLocaleString()}</FormLabel>,
						subject: <FormLabel className="user-notifications-table-row-bold">{notificationsStore.userNotificationDTOList[i].subject}</FormLabel>,
						body: <FormLabel className="user-notifications-table-row-bold">{messageStr}</FormLabel>,
						clickEvent: () => this.showEditMessageDialog()
					}
				)
			} else {
				rows.push(
					{
						id: <FormLabel>{idText}</FormLabel>,
						createdAt: <FormLabel>{createdAt.toLocaleString()}</FormLabel>,
						subject: <FormLabel>{notificationsStore.userNotificationDTOList[i].subject}</FormLabel>,
						body: <FormLabel>{messageStr}</FormLabel>,
						view: <MDBBtn className="admin-table-button" id={i} color="cyan" size="sm" onClick={() => this.showEditMessageDialog()}>Detalji</MDBBtn>,
						clickEvent: () => this.showEditMessageDialog()
					}
				)
			}
		}
	
		// combined columns and rows for notifications data
		const notificationsData = {
		    columns,
		    rows
		};
		
        return (
            <div className="about-go2play-margin">
				<Card className="my-profile-card card-style-center">
	                <Card.Header>
	                    <div className="row">
							<h5 className="h5-my-profile-card-title">Korisničke poruke</h5>
	                    </div>
	                </Card.Header>
	                <Card.Body>
						<div>
						{
		                    !notificationsStore.successfulNotificationsDelete
		                        ? <ErrorMessage errorMessage="Pogreška kod brisanja poruke" loginButton={false}/>
		                        : <div/>
		                }
						</div>
	                    <div className="row">
							<MDBDataTable className="admin-transactions-MDBDataTable"
								autoWidth
								reponsive={"true"}
								striped
								bordered
								small
								hover
								sortable
								paginationLabel={["Prethodna", "Slijedeća"]}
								infoLabel={["Prikazano", "do", "od", "poruka"]}
								entriesLabel="Prikaži poruka"
								searchLabel="Pronađi..."
								noRecordsFoundLabel="Nema podataka"
								tbodyTextWhite
								searching={false}
								data={notificationsData}
						    >
							</MDBDataTable>
	                    </div>
	                </Card.Body>

	            </Card>

				<Modal show={this.state.modalVisible} onHide={() => {}} size='lg' autoFocus keyboard className='edit-modal-color modal-padding'>
	                <Modal.Header>
	                    <Modal.Title className='font-color'>Uredi profil</Modal.Title>
	                </Modal.Header>
	                <Modal.Body>
	                    <Form className="admin-subscription-form-width">
		                    <FormGroup controlId="username">
		                        <Col>
		                            <FormLabel><h5 className="font-color font-size">Vrijeme kreiranja poruke</h5></FormLabel>
		                        </Col>
		                        <Col>
									<FormLabel><p style={{color: 'white', margin: '0rem', fontSize: 'small'}}>{notificationsStore.editNotification.createdAt.toLocaleString()}</p></FormLabel>
		                        </Col>
		                    </FormGroup>
							<FormGroup>
		                        <Col>
		                            <FormLabel><h5 className="font-color font-size">Naslov</h5></FormLabel>
		                        </Col>
		                        <Col>
		                            <FormControl readOnly value={notificationsStore.editNotification.subject} />
		                        </Col>
		                    </FormGroup>
							<FormGroup>
		                        <Col>
		                            <FormLabel><h5 className="font-color font-size">Poruka</h5></FormLabel>
		                        </Col>
		                        <Col>
									{/*
										 // @ts-ignore */}
		                            <FormControl readOnly as="textarea" rows='3' value={notificationsStore.editNotification.message} />
		                        </Col>
		                    </FormGroup>
		                </Form>
	                </Modal.Body>
	                <Modal.Footer className='admin-notifications-modal-footer'>
						<Button className='login-registration-button-color' onClick={() => this.updateNotification()}><b>Obriši</b></Button>
	                    <Button className='admin-notifications-modal-footer-cancel-button' onClick={() => this.onModalClose()}><b>Zatvori</b></Button>
	                </Modal.Footer>
	            </Modal>
            </div>
        );
    }

	@action
    private onModalClose() {
		this.updateNotificationToSeen();
        this.setState({
	      modalVisible: false
	    });
    }

	componentWillUnmount(): void {
		// upon leaving the component, setting all "UNREAD" messages to "SEEN"
		for (var i = 0; i < notificationsStore.userNotificationDTOList.length; i++){
			if(notificationsStore.userNotificationDTOList[i].notificationType === 'UNREAD' || notificationsStore.userNotificationDTOList[i].notificationType === 'unread') {
				notificationsStore.setEditNotification(notificationsStore.userNotificationDTOList[i]);
				this.updateNotificationToSeen();
			}
		}
    }

	componentDidMount(): void {
		notificationsStore.setHasUnreadMessages(false);
	}
	
}
