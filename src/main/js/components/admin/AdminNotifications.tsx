import React from "react";
import {observer} from "mobx-react";
import { MDBDataTable, MDBBtn } from 'mdbreact';
import adminStore from "../../store/AdminStore";
import {Card, Button, Col, Form, FormControl, FormGroup, FormLabel} from "react-bootstrap";
import ErrorMessage from "../utils/ErrorMessage";
import 'react-dropdown/style.css';
import 'react-datetime/css/react-datetime.css';
import {Modal} from "react-bootstrap";
import {action} from "mobx";

@observer
export default class AdminNotifications extends React.Component<{}, {saveNotificationFinished: boolean, editNotificationFinished: boolean, modalVisible: boolean}> {
	constructor(props: any) {
    	super(props);
    	this.state = {
			saveNotificationFinished: false,
			editNotificationFinished: false,
			modalVisible: false
		};
	}
	
	showEditMessageDialog = async () => {
		if(event !== undefined && event.srcElement !== null) {
			// @ts-ignore
			adminStore.setEditNotification(adminStore.adminNotificationDTOList[event.srcElement.id]);
			this.setState({
		    	modalVisible: true
		    });
		}
	};
	
	deleteNotification = async () => {
		const notificationId: number = adminStore.editNotification.id;
		const notificationIdList: number[] = [];
		notificationIdList.push(notificationId);
		let deleted: boolean = await adminStore.deleteNotifications(notificationIdList);
		if(deleted) {
			await adminStore.getNotifications('', '');	// does not return a Promise (void)
		}
		this.onModalClose();
  	};
	
	addNotification = async () => {
		this.setState({
	      saveNotificationFinished: false
	    });
		adminStore.newNotificationHolder(new Date(), "createdAt");
		const success: boolean = await adminStore.addNotification(adminStore.newNotification);
		this.setState({
	      saveNotificationFinished: true
	    });
		if(success) {
			await adminStore.getNotifications('', '');
			adminStore.newNotificationHolder('', "destUser");
			adminStore.newNotificationHolder('', "subject");
			adminStore.newNotificationHolder('', "message");
		}
		this.onModalClose();
	}
	
	updateNotification = async () => {
		this.setState({
	      editNotificationFinished: false
	    });
		const success: boolean = await adminStore.updateNotification(adminStore.editNotification);
		this.setState({
	      editNotificationFinished: true
	    });
		if(success) {
			await adminStore.getNotifications('', '');
		}
	}

    render() {
	
		// data for table of notifications
		const columns = [
		{
			label: '#',
	        field: 'id',
	        sort: 'asc',
	        width: 10
      	},
      	{
	        label: 'Vrijedi od',
	        field: 'createdAt',
	        sort: 'asc',
	        width: 30
      	},
		{
	        label: 'Od korisnika',
	        field: 'srcUser',
	        sort: 'asc',
	        width: 30
      	},
		{
	        label: 'Za korisnika',
	        field: 'destUser',
	        sort: 'asc',
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
	        width: 40
      	},
		{
	        label: 'Status',
	        field: 'notificationType',
	        width: 20
      	},
		{
	        label: 'Detalji',
	        field: 'view',
	        width: 10
      	}
		];
		const rows = [];
		for (var i = 0; i < adminStore.adminNotificationDTOList.length; i++){
			let createdAtStr: string = adminStore.adminNotificationDTOList[i].createdAt.toString();
			let createdAt: Date = new Date(createdAtStr.substring(0, createdAtStr.indexOf('.')) + 'Z');
			let messageStr: string = adminStore.adminNotificationDTOList[i].message;
			if(messageStr.length > 40) {
				messageStr = messageStr.substr(0, 37) + '...';
			}
			rows.push(
				{
					id: adminStore.adminNotificationDTOList[i].id,
					createdAt: createdAt.toLocaleString(),
					srcUser: adminStore.adminNotificationDTOList[i].srcUser,
					destUser: adminStore.adminNotificationDTOList[i].destUser,
					subject: adminStore.adminNotificationDTOList[i].subject,
					body: messageStr,
					notificationType: adminStore.adminNotificationDTOList[i].notificationType,
					view: <MDBBtn className="admin-table-button" id={i} color="cyan" size="sm" onClick={() => this.showEditMessageDialog()}>Detalji</MDBBtn>
				}
			)
		}

		// combined columns and rows for notifications data
		const notificationsData = {
		    columns,
		    rows
		};
		
        return (
            <div>
				<Card className="my-profile-card">
	                <Card.Header>
	                    <div className="row">
							<h5 className="h5-my-profile-card-title">Poruke korisnicima</h5>
	                    </div>
	                </Card.Header>
	                <Card.Body>
						<div>
						{
		                    !adminStore.transactionDeleteSuccessfull
		                        ? <ErrorMessage errorMessage="Pogreška kod brisanja pretplate" loginButton={false}/>
		                        : <div/>
		                }
						</div>
	                    <div className="row">
							<MDBDataTable className="admin-transactions-MDBDataTable"
								autoWidth
								reponsive
								striped
								bordered
								small
								hover
								sortable
								paginationLabel={["Prethodna", "Slijedeća"]}
								infoLabel={["Prikazano", "do", "od", "poruka"]}
								entriesLabel="Prikaži poruka"
								searchLabel="Pronađi..."
								tbodyTextWhite
								data={notificationsData}
						    />
	                    </div>
	                </Card.Body>

					<Card.Header>
	                    <div className="row">
							<h5 className="h5-my-profile-card-title">Unos nove poruke</h5>
	                    </div>
	                </Card.Header>
	                <Card.Body>
	                    <div className="row">
							<Form className="admin-subscription-form-width">
			                    <FormGroup controlId="username">
			                        <Col>
			                            <FormLabel><h5 className="font-color font-size">Za korisnika</h5></FormLabel>
			                        </Col>
			                        <Col>
			                            <FormControl value={adminStore.newNotification.destUser} type="username" onChange={(e: any) => adminStore.newNotificationHolder(e.target.value, "destUser")}/>
			                        </Col>
			                    </FormGroup>
								<FormGroup>
			                        <Col>
			                            <FormLabel><h5 className="font-color font-size">Naslov</h5></FormLabel>
			                        </Col>
			                        <Col>
			                            <FormControl value={adminStore.newNotification.subject} onChange={(e: any) => adminStore.newNotificationHolder(e.target.value, "subject")}/>
			                        </Col>
			                    </FormGroup>
								<FormGroup>
			                        <Col>
			                            <FormLabel><h5 className="font-color font-size">Poruka</h5></FormLabel>
			                        </Col>
			                        <Col>
			                            <FormControl as="textarea" rows="3" value={adminStore.newNotification.message} onChange={(e: any) => adminStore.newNotificationHolder(e.target.value, "message")}/>
			                        </Col>
			                    </FormGroup>
							
			                    <FormGroup>
			                        <Col className="login-registration-button-center">
			                            <Button type="submit" className="login-registration-button-color" onClick={() => this.addNotification()}><b>Unesi novu poruku</b></Button>
			                        </Col>
			                    </FormGroup>

								{
				                    (this.state.saveNotificationFinished && !adminStore.successfulNotificationSave)
				                        ? 	<div className="updateErrorMessage">
                								<b>Pogreška kod unosa poruke - tekst pogreške:</b>
												<p>{adminStore.responseErrorMessage}</p>
											</div>
				                        : <div/>
				                }
								{
				                    (this.state.saveNotificationFinished && adminStore.successfulNotificationSave)
				                        ? 	<div className="updateErrorMessage">
                								<b>Poruka uspješno unesena!</b>
											</div>
				                        : <div/>
				                }
			                </Form>
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
		                            <FormLabel><h5 className="font-color font-size">Za korisnika</h5></FormLabel>
		                        </Col>
		                        <Col>
		                            <FormControl value={adminStore.editNotification.destUser} type="username" onChange={(e: any) => adminStore.editNotificationHolder(e.target.value, "destUser")}/>
		                        </Col>
		                    </FormGroup>
							<FormGroup>
		                        <Col>
		                            <FormLabel><h5 className="font-color font-size">Naslov</h5></FormLabel>
		                        </Col>
		                        <Col>
		                            <FormControl value={adminStore.editNotification.subject} onChange={(e: any) => adminStore.editNotificationHolder(e.target.value, "subject")}/>
		                        </Col>
		                    </FormGroup>
							<FormGroup>
		                        <Col>
		                            <FormLabel><h5 className="font-color font-size">Poruka</h5></FormLabel>
		                        </Col>
		                        <Col>
		                            <FormControl as="textarea" rows="3" value={adminStore.editNotification.message} onChange={(e: any) => adminStore.editNotificationHolder(e.target.value, "message")}/>
		                        </Col>
		                    </FormGroup>
						
							{
			                    (this.state.editNotificationFinished && !adminStore.successfulNotificationSave)
			                        ? 	<div className="updateErrorMessage">
	        								<b>Pogreška kod spremanja poruke - tekst pogreške:</b>
											<p>{adminStore.responseErrorMessage}</p>
										</div>
			                        : <div/>
			                }
							{
			                    (this.state.editNotificationFinished && adminStore.successfulNotificationSave)
			                        ? 	<div className="updateErrorMessage">
	        								<b>Poruka je uspješno pohranjena</b>
										</div>
			                        : <div/>
			                }
		                </Form>
	                </Modal.Body>
	                <Modal.Footer className='admin-notifications-modal-footer'>
	                    <Button className='login-registration-button-color' onClick={() => this.deleteNotification()}><b>Obriši</b></Button>
						<Button className='login-registration-button-color' onClick={() => this.updateNotification()}><b>Spremi</b></Button>
	                    <Button className='admin-notifications-modal-footer-cancel-button' onClick={() => this.onModalClose()}><b>Zatvori</b></Button>
	                </Modal.Footer>
	            </Modal>

            </div>
        );
    }

	componentWillMount(): void {
		adminStore.getNotifications('', '');
		// settitng default values for new notification
		adminStore.newNotificationHolder(sessionStorage.getItem('username'), "srcUser");
		adminStore.newNotificationHolder('UNREAD', "notificationType");
    }

	@action
    private onModalClose() {
        this.setState({
	      modalVisible: false
	    });
    }

}

