import React from "react";
import {observer} from "mobx-react";
import { MDBDataTable, MDBBtn } from 'mdbreact';
import adminStore from "../../store/AdminStore";
import {Card, Button, Col, Form, FormControl, FormGroup, FormLabel} from "react-bootstrap";
import ErrorMessage from "../utils/ErrorMessage";
import Dropdown from 'react-dropdown';
import 'react-dropdown/style.css';
import DateTime from 'react-datetime';
import 'react-datetime/css/react-datetime.css';

@observer
export default class AdminSubscriptions extends React.Component<{}, {saveSubscriptionFinished: boolean}> {
	constructor(props: any) {
    	super(props);
    	this.state = {
			saveSubscriptionFinished: false
		};
		this.saveSubscription = this.saveSubscription.bind(this);
	}
	
	delete = async () => {
		if(event !== undefined && event.srcElement !== null) {
			// @ts-ignore
			let subscriptionId: number = adminStore.subscriptionDTOList[event.srcElement.id].id;
			// @ts-ignore
			let username: string = adminStore.subscriptionDTOList[event.srcElement.id].username;
			let deleted: boolean = await adminStore.deleteSubscription(username, subscriptionId);
			if(deleted) {
				await adminStore.getAllSubscriptions(adminStore.displayOnlyActiveSubscriptins);	// does not return a Promise (void)
				this.forceUpdate();
			}
		} 
  	};
	
	onlyActiveChange = async () => {
		if(event !== undefined && event.target !== null) {
			// @ts-ignore
			adminStore.setDisplayOnlyActiveSubscriptins(event.target.checked);
			await adminStore.getAllSubscriptions(adminStore.displayOnlyActiveSubscriptins);
			this.forceUpdate();
		}
	};
	
	dropdownChange = (param: any) => {
		adminStore.newSubscriptionHolder(param.value, "subscriptionTypeName");
	}
	
	setValidFrom = (moment: any) => {
		adminStore.newSubscriptionHolder(this.getDate(moment), "validFrom");
	}
	
	setValidTo = (moment: any) => {
		adminStore.newSubscriptionHolder(this.getDate(moment), "validTo");
	}
	
	getDate = (moment: string) => {
		return new Date(moment);
	}
	
	saveSubscription = async () => {
		this.setState({
	      saveSubscriptionFinished: false
	    });
		const success: boolean = await adminStore.saveSubscription(adminStore.newSubscription);
		this.setState({
	      saveSubscriptionFinished: true
	    });
		if(success) {
			await adminStore.getAllSubscriptions(adminStore.displayOnlyActiveSubscriptins);
		}
	}

    render() {
	
		// data for table of subscriptions
		const columns = [
		{
			label: '#',
	        field: 'id',
	        sort: 'asc',
	        width: 10
      	},
      	{
	        label: 'Istekla',
	        field: 'invalid',
	        sort: 'asc',
	        width: 30
      	},
		{
	        label: 'Vrijedi od',
	        field: 'validFrom',
	        sort: 'asc',
	        width: 30
      	},
		{
	        label: 'Vrijedi do',
	        field: 'validTo',
	        sort: 'asc',
	        width: 30
      	},
		{
	        label: 'Tip pretplate',
	        field: 'subscriptionTypeName',
	        sort: 'asc',
	        width: 30
      	},
		{
	        label: 'Korisnik',
	        field: 'user',
	        width: 40
      	},
		{
	        label: 'Brisanje',
	        field: 'delete',
	        width: 20
      	}
		];
		const rows = [];
		for (var i = 0; i < adminStore.subscriptionDTOList.length; i++){
			let validFromStr: string = adminStore.subscriptionDTOList[i].validFrom.toString();
			let validFrom: Date = new Date(validFromStr.substring(0, validFromStr.indexOf('.')) + 'Z');
			let validToStr: string = adminStore.subscriptionDTOList[i].validTo.toString();
			let validTo: Date = new Date(validToStr.substring(0, validToStr.indexOf('.')) + 'Z');
			let validString: string = 'DA';
			if(adminStore.subscriptionDTOList[i].valid) {
				validString = 'NE';
			}
			rows.push(
				{
					id: adminStore.subscriptionDTOList[i].id,
					invalid: validString,
					validFrom: validFrom.toLocaleString(),
					validTo: validTo.toLocaleString(),
					subscriptionTypeName: adminStore.subscriptionDTOList[i].subscriptionTypeName,
					user: adminStore.subscriptionDTOList[i].username,
					delete: <div className="admin-table-button-div">
								<MDBBtn bs-style="primary" size="sm" className="admin-table-button" id={i} color="red" onClick={() => this.delete()}>
									<i id={i.toString()} className="fas fa-trash"></i>
	                            </MDBBtn>
							</div>
				}
			)
		}

		// combined columns and rows for subscriptions data
		const subscriptionData = {
		    columns,
		    rows
		};
		
		// new subscriptions - dropdown options
		const options = [];
		for (var i = 0; i < adminStore.adminSubscriptionTypesDTO.length; i++){
			options.push(adminStore.adminSubscriptionTypesDTO[i].name);
		}
		if(options.length > 0 && (adminStore.newSubscription.subscriptionTypeName === undefined || adminStore.newSubscription.subscriptionTypeName === '')) {
			adminStore.newSubscriptionHolder(options[0], "subscriptionTypeName");
		}
	
        return (
            <div>
				<Card className="my-profile-card">
	                <Card.Header>
	                    <div className="row">
							<h5 className="h5-my-profile-card-title">Korisničke pretplate</h5>
							<div className="admin-subscription-active-check">
								<p>Prikaži samo aktivne pretplate:</p>
								<input className="admin-subscription-active-checkbox"
							          type="checkbox"
							          name="onlyActiveSubscriptions"
							          id="onlyActiveSubscriptions"
							          onChange={this.onlyActiveChange} // Triggers the function in the Part 2
							          checked={adminStore.displayOnlyActiveSubscriptins}
							      />
							</div>
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
								striped
								bordered
								small
								hover
								sortable
								paginationLabel={["Prethodna", "Slijedeća"]}
								infoLabel={["Prikazano", "do", "od", "pretplata"]}
								entriesLabel="Prikaži pretplata"
								noRecordsFoundLabel="Nema podataka"
								searchLabel="Pronađi..."
								tbodyTextWhite
								data={subscriptionData}
						    />
	                    </div>
	                </Card.Body>

					<Card.Header>
	                    <div className="row">
							<h5 className="h5-my-profile-card-title">Unos nove pretplate</h5>
	                    </div>
	                </Card.Header>
	                <Card.Body>
	                    <div className="row">
							<Form className="admin-subscription-form-width">
			                    <FormGroup controlId="username">
			                        <Col>
			                            <FormLabel><h5 className="font-color font-size">Korisničko ime</h5></FormLabel>
			                        </Col>
			                        <Col>
			                            <FormControl value={adminStore.newSubscription.username} type="username" onChange={(e: any) => adminStore.newSubscriptionHolder(e.target.value, "username")}/>
			                        </Col>
			                    </FormGroup>
			                    <FormGroup>
			                        <Col>
			                            <FormLabel><h5 className="font-color font-size">Vrijedi od</h5></FormLabel>
			                        </Col>
			                        <Col className="admin-subscription-date-picker">
										<DateTime 
			                                value={adminStore.newSubscription.validFrom}
			                                onChange={param => this.setValidFrom(param)}
			                            />
			                        </Col>
			                    </FormGroup>
								<FormGroup>
			                        <Col>
			                            <FormLabel><h5 className="font-color font-size">Vrijedi do</h5></FormLabel>
			                        </Col>
			                        <Col  className="admin-subscription-date-picker">
										<DateTime 
			                                value={adminStore.newSubscription.validTo}
			                                onChange={param => this.setValidTo(param)}
			                            />
			                        </Col>
			                    </FormGroup>
								<FormGroup>
			                        <Col>
			                            <FormLabel><h5 className="font-color font-size">Tip pretplate</h5></FormLabel>
			                        </Col>
			                        <Col>
			                            <Dropdown options={options} onChange={param => this.dropdownChange(param)} value={adminStore.newSubscription.subscriptionTypeName} placeholder="Select an option" />
			                        </Col>
			                    </FormGroup>
							
			                    <FormGroup>
			                        <Col className="login-registration-button-center">
			                            <Button type="submit" className="login-registration-button-color" onClick={() => this.saveSubscription()}><b>Unesi novu pretplatu</b></Button>
			                        </Col>
			                    </FormGroup>

								{
				                    (this.state.saveSubscriptionFinished && !adminStore.successfulSubscriptionSave)
				                        ? 	<div className="updateErrorMessage">
                								<b>Pogreška kod unosa pretplate Tekst pogreške:</b>
												<p>{adminStore.subscriptionSaveErrorMessage}</p>
											</div>
				                        : <div/>
				                }
								{
				                    (this.state.saveSubscriptionFinished && adminStore.successfulSubscriptionSave)
				                        ? 	<div className="updateErrorMessage">
                								<b>Pretplata uspješno unesena!</b>
											</div>
				                        : <div/>
				                }
			                </Form>
	                    </div>
	                </Card.Body>
	            </Card>
            </div>
        );
    }

	componentDidMount(): void {
		adminStore.getAllSubscriptions(adminStore.displayOnlyActiveSubscriptins);
		adminStore.getSubscriptionTypes();
		// setting up predefined data for new subscription input
		var tomorrow = new Date();
		tomorrow.setDate(tomorrow.getDate() + 1);
		adminStore.newSubscriptionHolder(tomorrow, "validTo");
		adminStore.newSubscriptionHolder(true, "valid");
    }

}

