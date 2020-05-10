import React from "react";
import {observer} from "mobx-react";
import { MDBDataTable, MDBBtn } from 'mdbreact';
import notificationsStore from "../../store/NotificationsStore";
import userStore from "../../store/UserStore";
import {Card, Button, Col, Form, FormControl, FormGroup, FormLabel} from "react-bootstrap";
import ErrorMessage from "../utils/ErrorMessage";
import 'react-dropdown/style.css';
import 'react-datetime/css/react-datetime.css';
import {Modal} from "react-bootstrap";
import {action} from "mobx";

@observer
export default class AdminNotifications extends React.Component<{}, {saveNotificationFinished: boolean, editNotificationFinished: boolean, modalVisible: boolean, randomMessagesSuccessMessage: string, randomMessagesErrorMessage: string}> {
	constructor(props: any) {
    	super(props);
    	this.state = {
			saveNotificationFinished: false,
			editNotificationFinished: false,
			modalVisible: false,
			randomMessagesSuccessMessage: '',
			randomMessagesErrorMessage: ''
		};
	}
	
	showEditMessageDialog = async () => {
		if(event !== undefined && event.srcElement !== null) {
			// @ts-ignore
			notificationsStore.setEditNotification(notificationsStore.adminNotificationDTOList[event.srcElement.id]);
			this.setState({
		    	modalVisible: true
		    });
		}
	};
	
	deleteNotification = async () => {
		const notificationId: number = notificationsStore.editNotification.id;
		const notificationIdList: number[] = [];
		notificationIdList.push(notificationId);
		let deleted: boolean = await notificationsStore.deleteNotifications(notificationIdList);
		if(deleted) {
			await notificationsStore.getNotifications('', '');	// does not return a Promise (void)
		}
		this.onModalClose();
  	};
	
	addNotification = async () => {
		this.setState({
	      saveNotificationFinished: false
	    });
		notificationsStore.newNotificationHolder(new Date(), "createdAt");
		const success: boolean = await notificationsStore.addNotification(notificationsStore.newNotification);
		this.setState({
	      saveNotificationFinished: true
	    });
		if(success) {
			await notificationsStore.getNotifications('', '');
			notificationsStore.newNotificationHolder('', "destUser");
			notificationsStore.newNotificationHolder('', "subject");
			notificationsStore.newNotificationHolder('', "message");
		}
		this.onModalClose();
		
	}
	
	updateNotification = async () => {
		this.setState({
	      editNotificationFinished: false
	    });
		const success: boolean = await notificationsStore.updateNotification(notificationsStore.editNotification);
		this.setState({
	      editNotificationFinished: true
	    });
		if(success) {
			await notificationsStore.getNotifications('', '');
		}
	}
	
	sendMessagesToRandomUsers = async () => {
		// input validation
		if(notificationsStore.noOfRandomRecipients < 1) {
			this.setState( {
				randomMessagesErrorMessage: 'Broj nasumičnih korisnika mora biti veći od 0'
			});
			return;
		}
		if(notificationsStore.randomRecipientsSubject === undefined || notificationsStore.randomRecipientsSubject === '') {
			this.setState( {
				randomMessagesErrorMessage: 'Naslov poruke nije unesen'
			});
			return;
		}
		if(notificationsStore.randomRecipientsMessage === undefined || notificationsStore.randomRecipientsMessage === '') {
			this.setState( {
				randomMessagesErrorMessage: 'Tekst poruke nije unesen'
			});
			return;
		}
		
		await userStore.getAllUsers();
		let usersWhichWillReceiveMessage: string[] = [];
		if(userStore.allUsers.length > notificationsStore.noOfRandomRecipients) {
			while(usersWhichWillReceiveMessage.length < notificationsStore.noOfRandomRecipients) {
				const sendMessageToUser: string = userStore.allUsers[this.randomIntFromInterval(0, userStore.allUsers.length - 1)].username;
				// add the user if it is not already on the list or if picked user is not logged in user
				if(!usersWhichWillReceiveMessage.includes(sendMessageToUser) && sessionStorage.getItem('username') !== sendMessageToUser) {
					usersWhichWillReceiveMessage.push(sendMessageToUser);
				}
			}
		} else {
			// send message to all users
			for (var i = 0; i < userStore.allUsers.length; i++){
				usersWhichWillReceiveMessage.push(userStore.allUsers[i].username);
			}
		}
		usersWhichWillReceiveMessage = usersWhichWillReceiveMessage.sort();
		// send the messages
		notificationsStore.newNotificationHolder(new Date(), "createdAt");
		notificationsStore.newNotificationHolder(notificationsStore.randomRecipientsSubject, "subject");
		notificationsStore.newNotificationHolder(notificationsStore.randomRecipientsMessage, "message");
		notificationsStore.newNotificationHolder(sessionStorage.getItem('username'), "srcUser");
		notificationsStore.newNotificationHolder('UNREAD', "notificationType");
		let successMessage: string = '';
		let errorMessage: string = '';
		for (var i = 0; i < usersWhichWillReceiveMessage.length; i++){
			notificationsStore.newNotificationHolder(usersWhichWillReceiveMessage[i], "destUser");
			const success: boolean = await notificationsStore.addNotification(notificationsStore.newNotification);
			if(success) {
				successMessage = successMessage + usersWhichWillReceiveMessage[i] + ', ';
			} else {
				errorMessage = errorMessage + usersWhichWillReceiveMessage[i] + ', ';
			}
		}
		if(successMessage.endsWith(', ')) {
			successMessage = successMessage.substring(0, successMessage.lastIndexOf(','));
		}
		if(errorMessage.endsWith(', ')) {
			errorMessage = 'Korisnicima ' + errorMessage.substring(0, errorMessage.lastIndexOf(',')) + ' poruka nije isporučena';
		}
		
		this.setState( {
			randomMessagesSuccessMessage: successMessage,
			randomMessagesErrorMessage: errorMessage
		});
		notificationsStore.getNotifications('', '');
	}
	
	randomIntFromInterval = (min: number, max: number) => {
  		return Math.floor(Math.random() * (max - min + 1) + min);
	}
	
	randomMessageModalClose = () => {
		if(this.state.randomMessagesErrorMessage === '') {
			notificationsStore.setNoOfRandomRecipientsMethod(0);
			notificationsStore.setRandomRecipientsSubjectMethod('');
			notificationsStore.setRandomRecipientsMessageMethod('');
		}
		this.setState( {
			randomMessagesSuccessMessage: '',
			randomMessagesErrorMessage: ''
		});
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
		for (var i = 0; i < notificationsStore.adminNotificationDTOList.length; i++){
			let createdAtStr: string = notificationsStore.adminNotificationDTOList[i].createdAt.toString();
			let createdAt: Date = new Date(createdAtStr.substring(0, createdAtStr.indexOf('.')) + 'Z');
			let messageStr: string = notificationsStore.adminNotificationDTOList[i].message;
			if(messageStr.length > 40) {
				messageStr = messageStr.substr(0, 37) + '...';
			}
			rows.push(
				{
					id: notificationsStore.adminNotificationDTOList[i].id,
					createdAt: createdAt.toLocaleString(),
					srcUser: notificationsStore.adminNotificationDTOList[i].srcUser,
					destUser: notificationsStore.adminNotificationDTOList[i].destUser,
					subject: notificationsStore.adminNotificationDTOList[i].subject,
					body: messageStr,
					notificationType: notificationsStore.adminNotificationDTOList[i].notificationType,
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
				<Card className="my-profile-card card-style-center">
	                <Card.Header>
	                    <div className="row">
							<h5 className="h5-my-profile-card-title">Poruke korisnicima</h5>
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
			                            <FormControl value={notificationsStore.newNotification.destUser} type="username" onChange={(e: any) => notificationsStore.newNotificationHolder(e.target.value, "destUser")}/>
			                        </Col>
			                    </FormGroup>
								<FormGroup>
			                        <Col>
			                            <FormLabel><h5 className="font-color font-size">Naslov</h5></FormLabel>
			                        </Col>
			                        <Col>
			                            <FormControl value={notificationsStore.newNotification.subject} onChange={(e: any) => notificationsStore.newNotificationHolder(e.target.value, "subject")}/>
			                        </Col>
			                    </FormGroup>
								<FormGroup>
			                        <Col>
			                            <FormLabel><h5 className="font-color font-size">Poruka</h5></FormLabel>
			                        </Col>
			                        <Col>
										{
											// @ts-ignore
			                            	<FormControl as="textarea" rows="3" value={notificationsStore.newNotification.message} onChange={(e: any) => notificationsStore.newNotificationHolder(e.target.value, "message")}/>
										}
			                        </Col>
			                    </FormGroup>
							
			                    <FormGroup>
			                        <Col className="login-registration-button-center">
			                            <Button type="submit" className="login-registration-button-color" onClick={() => this.addNotification()}><b>Unesi novu poruku</b></Button>
			                        </Col>
			                    </FormGroup>

								{
				                    (this.state.saveNotificationFinished && !notificationsStore.successfulNotificationSave)
				                        ? 	<div className="updateErrorMessage">
                								<b>Pogreška kod unosa poruke - tekst pogreške:</b>
												<p>{notificationsStore.responseErrorMessage}</p>
											</div>
				                        : <div/>
				                }
								{
				                    (this.state.saveNotificationFinished && notificationsStore.successfulNotificationSave)
				                        ? 	<div className="updateErrorMessage">
                								<b>Poruka uspješno unesena!</b>
											</div>
				                        : <div/>
				                }
			                </Form>
	                    </div>
	                </Card.Body>

					<Card.Header>
	                    <div className="row">
							<h5 className="h5-my-profile-card-title">Nagradne poruke</h5>
	                    </div>
	                </Card.Header>
	                <Card.Body>
	                    <div className="row">
							<Form className="admin-subscription-form-width">
			                    <FormGroup controlId="username">
			                        <Col>
			                            <FormLabel><h5 className="font-color font-size">Koliko nasumičnih korisnika će dobiti poruku?</h5></FormLabel>
			                        </Col>
			                        <Col>
										{/*
										 // @ts-ignore */}
			                            <FormControl type="number" value={notificationsStore.noOfRandomRecipients} onChange={(e: any) => notificationsStore.setNoOfRandomRecipientsMethod(e.target.value)}/>
			                        </Col>
			                    </FormGroup>
								<FormGroup>
			                        <Col>
			                            <FormLabel><h5 className="font-color font-size">Naslov</h5></FormLabel>
			                        </Col>
			                        <Col>
			                            <FormControl value={notificationsStore.randomRecipientsSubject} onChange={(e: any) => notificationsStore.setRandomRecipientsSubjectMethod(e.target.value)}/>
			                        </Col>
			                    </FormGroup>
								<FormGroup>
			                        <Col>
			                            <FormLabel><h5 className="font-color font-size">Poruka</h5></FormLabel>
			                        </Col>
			                        <Col>
										{
											// @ts-ignore
			                            	<FormControl as="textarea" rows="3" value={notificationsStore.randomRecipientsMessage} onChange={(e: any) => notificationsStore.setRandomRecipientsMessageMethod(e.target.value)}/>
										}
			                        </Col>
			                    </FormGroup>
							
			                    <FormGroup>
			                        <Col className="login-registration-button-center">
			                            <Button type="submit" className="login-registration-button-color" onClick={() => this.sendMessagesToRandomUsers()}><b>Pošalji poruke</b></Button>
			                        </Col>
			                    </FormGroup>
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
		                            <FormControl value={notificationsStore.editNotification.destUser} type="username" onChange={(e: any) => notificationsStore.editNotificationHolder(e.target.value, "destUser")}/>
		                        </Col>
		                    </FormGroup>
							<FormGroup>
		                        <Col>
		                            <FormLabel><h5 className="font-color font-size">Naslov</h5></FormLabel>
		                        </Col>
		                        <Col>
		                            <FormControl value={notificationsStore.editNotification.subject} onChange={(e: any) => notificationsStore.editNotificationHolder(e.target.value, "subject")}/>
		                        </Col>
		                    </FormGroup>
							<FormGroup>
		                        <Col>
		                            <FormLabel><h5 className="font-color font-size">Poruka</h5></FormLabel>
		                        </Col>
		                        <Col>
									{/*
										 // @ts-ignore */}
		                            <FormControl as="textarea" rows="3" value={notificationsStore.editNotification.message} onChange={(e: any) => notificationsStore.editNotificationHolder(e.target.value, "message")}/>
		                        </Col>
		                    </FormGroup>
							{
			                    (this.state.editNotificationFinished && !notificationsStore.successfulNotificationSave)
			                        ? 	<div className="updateErrorMessage">
	        								<b>Pogreška kod spremanja poruke - tekst pogreške:</b>
											<p>{notificationsStore.responseErrorMessage}</p>
										</div>
			                        : <div/>
			                }
							{
			                    (this.state.editNotificationFinished && notificationsStore.successfulNotificationSave)
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

				<Modal show={this.state.randomMessagesSuccessMessage != '' || this.state.randomMessagesErrorMessage != ''} onHide={() => {}} size='lg' autoFocus keyboard className='edit-modal-color modal-padding'>
	                <Modal.Body>
						{
							(this.state.randomMessagesSuccessMessage != '')
							? 	<div>
									<FormLabel><h5 className="font-color font-size">Poruka je uspješno poslana korisnicima: </h5></FormLabel>
		                    		<FormLabel><h5 style={{color: "white", paddingLeft: "10px"}} className="font-size">{this.state.randomMessagesSuccessMessage}</h5></FormLabel>
								</div>
							: <div/>
						}
						{
							(this.state.randomMessagesErrorMessage != '')
							? 	<div>
									<FormLabel><h5 className="font-color font-size">Greška prilikom slanja poruke: </h5></FormLabel>
	                    			<FormLabel><h5 style={{color: "white", paddingLeft: "10px"}} className="font-size">{this.state.randomMessagesErrorMessage}</h5></FormLabel>
								</div>
							: <div/>
						}
	                </Modal.Body>
	                <Modal.Footer className='admin-notifications-modal-footer'>
	                    <Button className='admin-notifications-modal-footer-cancel-button' onClick={() => this.randomMessageModalClose()}><b>Zatvori</b></Button>
	                </Modal.Footer>
	            </Modal>

            </div>
        );
    }

	@action
    private onModalClose() {
        this.setState({
	      modalVisible: false
	    });
    }

	componentDidMount(): void {
		notificationsStore.getNotifications('', '');
		// settitng default values for new notification
		notificationsStore.newNotificationHolder(sessionStorage.getItem('username'), "srcUser");
		notificationsStore.newNotificationHolder('UNREAD', "notificationType");
    }

	componentWillUnmount(): void {
		notificationsStore.newNotificationHolder('', "destUser");
		notificationsStore.newNotificationHolder('', "subject");
		notificationsStore.newNotificationHolder('', "message");
		notificationsStore.setNoOfRandomRecipientsMethod(0);
		notificationsStore.setRandomRecipientsSubjectMethod('');
		notificationsStore.setRandomRecipientsMessageMethod('');
	}
}

