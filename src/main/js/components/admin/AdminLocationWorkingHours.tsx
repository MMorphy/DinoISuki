import React from "react";
import {Card, Button, Col, Form, FormGroup, FormLabel} from "react-bootstrap";
import adminStore from "../../store/AdminStore";
import {observer} from "mobx-react";
import { MDBDataTable, MDBBtn } from 'mdbreact';
import {Modal} from "react-bootstrap";
import DateTime from 'react-datetime';
import 'react-datetime/css/react-datetime.css';
import AdminLocationWithWorkingHoursDTO from "../../model/AdminLocationWithWorkingHoursDTO";
import WorkingHoursDTO from "../../model/WorkingHoursDTO";
import ErrorMessage from "../utils/ErrorMessage";

@observer
export default class AdminLocationWorkingHours extends React.Component<{}, {modalVisible: boolean, modalLocationName: string, locationId: number, successfulStore: boolean}> {
	constructor(props: any) {
    	super(props);
    	this.state = {
			modalVisible: false,
			modalLocationName: '',
			locationId: -1,
			successfulStore: false
		};
	}
	
	showEditMessageDialog = async () => {
		// @ts-ignore
		const id = event.srcElement.id;
		
		this.prepareWorkingHoursModal(id);
		
		this.setState({
		    	modalVisible: true,
				modalLocationName: adminStore.adminLocationWithWorkingHoursDTOList[id].name,
				locationId: id
		    });
	};
	
	private onModalClose() {
        this.setState({
			modalVisible: false,
			modalLocationName: '',
			locationId: -1,
			successfulStore: false
	    });
    }
	
	mapWorkingHoursDayType = (dayType: string) => {
		switch(dayType) {
			case 'WORKDAY': {
				return 'RADNI DAN';
			}
			case 'WEEKEND': {
				return 'VIKEND';
			}
			case 'HOLIDAY': {
				return 'BLAGDAN';
			}
		}
		return '';
	};
	
	prepareWorkingHoursModal = (id: number) => {
		// first setting the defaults (actual/saved working hours might not be available)
		adminStore.saveWorkingHoursDTO((new Date("2020-01-01T08:00:00.000Z")), 'WORKDAY', "fromTime");
		adminStore.saveWorkingHoursDTO((new Date("2020-01-01T20:00:00.000Z")), 'WORKDAY', "toTime");
		adminStore.saveWorkingHoursDTO((new Date("2020-01-01T08:00:00.000Z")), 'WEEKEND', "fromTime");
		adminStore.saveWorkingHoursDTO((new Date("2020-01-01T20:00:00.000Z")), 'WEEKEND', "toTime");
		adminStore.saveWorkingHoursDTO((new Date("2020-01-01T08:00:00.000Z")), 'HOLIDAY', "fromTime");
		adminStore.saveWorkingHoursDTO((new Date("2020-01-01T20:00:00.000Z")), 'HOLIDAY', "toTime");
		
		for(let i: number = 0; i < adminStore.adminLocationWithWorkingHoursDTOList[id].workingHours.length; i++) {
			// console.log('day:' + adminStore.adminLocationWithWorkingHoursDTOList[id].workingHours[i].dayType + ' from:' + adminStore.adminLocationWithWorkingHoursDTOList[id].workingHours[i].fromTime + ' to:' + adminStore.adminLocationWithWorkingHoursDTOList[id].workingHours[i].toTime);
			
			let dateTimeFrom = new Date();
			let dateTimeTo = new Date();
			dateTimeFrom.setHours(parseInt(adminStore.adminLocationWithWorkingHoursDTOList[id].workingHours[i].fromTime.substr(0, 2)));
			dateTimeFrom.setMinutes(parseInt(adminStore.adminLocationWithWorkingHoursDTOList[id].workingHours[i].fromTime.substr(3, 2)));
			dateTimeFrom.setSeconds(parseInt(adminStore.adminLocationWithWorkingHoursDTOList[id].workingHours[i].fromTime.substr(6, 2)));
			dateTimeTo.setHours(parseInt(adminStore.adminLocationWithWorkingHoursDTOList[id].workingHours[i].toTime.substr(0, 2)));
			dateTimeTo.setMinutes(parseInt(adminStore.adminLocationWithWorkingHoursDTOList[id].workingHours[i].toTime.substr(3, 2)));
			dateTimeTo.setSeconds(parseInt(adminStore.adminLocationWithWorkingHoursDTOList[id].workingHours[i].toTime.substr(6, 2)));
			
			switch(adminStore.adminLocationWithWorkingHoursDTOList[id].workingHours[i].dayType) {
				case 'WORKDAY': {
					adminStore.saveWorkingHoursDTO(dateTimeFrom, 'WORKDAY', "fromTime");
					adminStore.saveWorkingHoursDTO(dateTimeTo, 'WORKDAY', "toTime");
					break;
				}
				case 'WEEKEND': {
					adminStore.saveWorkingHoursDTO(dateTimeFrom, 'WEEKEND', "fromTime");
					adminStore.saveWorkingHoursDTO(dateTimeTo, 'WEEKEND', "toTime");
					break;
				}
				case 'HOLIDAY': {
					adminStore.saveWorkingHoursDTO(dateTimeFrom, 'HOLIDAY', "fromTime");
					adminStore.saveWorkingHoursDTO(dateTimeTo, 'HOLIDAY', "toTime");
					break;
				}
			}
		}
	};
	
	saveWorkingHours = async () => {
		
		let adminLocationWithWorkingHoursDTO: AdminLocationWithWorkingHoursDTO = adminStore.adminLocationWithWorkingHoursDTOList[this.state.locationId];
		
		let allWorkingHoursDTO: WorkingHoursDTO[] = [];
		let workingHoursDTOWorkday: WorkingHoursDTO = new WorkingHoursDTO();
		workingHoursDTOWorkday.dayType = 'WORKDAY';
		workingHoursDTOWorkday.fromTime = adminStore.workingHoursDTOWorkdayTimeFrom.toTimeString().substr(0, 8);
		workingHoursDTOWorkday.toTime = adminStore.workingHoursDTOWorkdayTimeTo.toTimeString().substr(0, 8);
		allWorkingHoursDTO.push(workingHoursDTOWorkday);
		let workingHoursDTOWeekend: WorkingHoursDTO = new WorkingHoursDTO();
		workingHoursDTOWeekend.dayType = 'WEEKEND';
		workingHoursDTOWeekend.fromTime = adminStore.workingHoursDTOWeekendTimeFrom.toTimeString().substr(0, 8);
		workingHoursDTOWeekend.toTime = adminStore.workingHoursDTOWeekendTimeTo.toTimeString().substr(0, 8);
		allWorkingHoursDTO.push(workingHoursDTOWeekend);
		let workingHoursDTOHoliday: WorkingHoursDTO = new WorkingHoursDTO();
		workingHoursDTOHoliday.dayType = 'HOLIDAY';
		workingHoursDTOHoliday.fromTime = adminStore.workingHoursDTOHolidayTimeFrom.toTimeString().substr(0, 8);
		workingHoursDTOHoliday.toTime = adminStore.workingHoursDTOHolidayTimeTo.toTimeString().substr(0, 8);
		allWorkingHoursDTO.push(workingHoursDTOHoliday);
		
		adminLocationWithWorkingHoursDTO.workingHours = allWorkingHoursDTO;
		
		const success: boolean = await adminStore.saveLocationWorkingHours(adminLocationWithWorkingHoursDTO);
		if(success) {
			this.setState({
				successfulStore: true
		    });
		}
		
	};
	
	setWorkingHours = async (value: any, dayType: string, timeType: string) => {
		let myDate = new Date(value);
		adminStore.saveWorkingHoursDTO(myDate, dayType, timeType);
		
	};
	
	
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
	        label: 'Naziv lokacije',
	        field: 'name',
	        sort: 'asc',
	        width: 30
      	},
		{
	        label: 'Adresa lokacije',
	        field: 'address',
	        sort: 'asc',
	        width: 30
      	},
		{
	        label: 'Kontakt lokacije',
	        field: 'contact',
	        sort: 'asc',
	        width: 30
      	},
		{
	        label: 'Radno vrijeme',
	        field: 'view',
	        width: 10
      	}
		];
		const rows = [];
		for (var i = 0; i < adminStore.adminLocationWithWorkingHoursDTOList.length; i++){
			let _contact: string = adminStore.adminLocationWithWorkingHoursDTOList[i].contactEmail;
			if(_contact === undefined || _contact === '' || _contact === null) {
				_contact = adminStore.adminLocationWithWorkingHoursDTOList[i].contactTel;
			} else {
				if(adminStore.adminLocationWithWorkingHoursDTOList[i].contactTel !== undefined && adminStore.adminLocationWithWorkingHoursDTOList[i].contactTel !== '' && adminStore.adminLocationWithWorkingHoursDTOList[i].contactTel !== null) {
					_contact = _contact + ' / ' + adminStore.adminLocationWithWorkingHoursDTOList[i].contactTel
				}
			}
			rows.push(
				{
					id: adminStore.adminLocationWithWorkingHoursDTOList[i].id,
					name: adminStore.adminLocationWithWorkingHoursDTOList[i].name,
					address: adminStore.adminLocationWithWorkingHoursDTOList[i].address,
					contact: _contact,
					view: <MDBBtn className="admin-table-button" id={i} color="cyan" size="sm" onClick={() => this.showEditMessageDialog()}>Uredi</MDBBtn>
				}
			)
		}

		// combined columns and rows for notifications data
		const locationsData = {
		    columns,
		    rows
		};
		

        return (
            <div>
				<Card className="my-profile-card card-style-center">
	                <Card.Header>
	                    <div className="row">
							<h5 className="h5-my-profile-card-title">Lokacije</h5>
	                    </div>
	                </Card.Header>
	                <Card.Body>
						<div>
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
								infoLabel={["Prikazano", "do", "od", "lokacija"]}
								entriesLabel="Prikaži lokacija"
								searchLabel="Pronađi..."
								noRecordsFoundLabel="Nema podataka"
								tbodyTextWhite
								data={locationsData}
						    />
	                    </div>
	                </Card.Body>

	            </Card>

				
				<Modal show={this.state.modalVisible} onHide={() => {}} size='lg' autoFocus keyboard className='edit-modal-color modal-padding'>
	                <Modal.Header>
	                    <Modal.Title className='font-color'>Uredi radno vrijeme lokacije: {this.state.modalLocationName}</Modal.Title>
	                </Modal.Header>
	                <Modal.Body>
	                    <Form className="admin-subscription-form-width">
							<FormGroup>
		                        <Col>
		                            <FormLabel><h5 className="font-color font-size">{this.mapWorkingHoursDayType('WORKDAY')}</h5></FormLabel>
		                        </Col>
		                        <Col style={{width: "maxContent", paddingBottom: "40px"}}>
									<div style={{float: "left", paddingRight: "15px"}}>
										<DateTime dateFormat={false} value={adminStore.workingHoursDTOWorkdayTimeFrom}
			                                onChange={param => this.setWorkingHours(param, 'WORKDAY', "fromTime")}
											timeFormat='HH:mm' />
									</div>
									<div style={{float: "right", paddingRight: "20px"}}>
		                            	<DateTime dateFormat={false} value={adminStore.workingHoursDTOWorkdayTimeTo}
			                                onChange={param => this.setWorkingHours(param, 'WORKDAY', "toTime")}
											timeFormat='HH:mm' />
									</div>
		                        </Col>
		                    </FormGroup>
							<FormGroup>
		                        <Col>
		                            <FormLabel><h5 className="font-color font-size">{this.mapWorkingHoursDayType('WEEKEND')}</h5></FormLabel>
		                        </Col>
		                        <Col style={{width: "maxContent", paddingBottom: "40px"}}>
									<div style={{float: "left", paddingRight: "15px"}}>
										<DateTime dateFormat={false} value={adminStore.workingHoursDTOWeekendTimeFrom}
			                                onChange={param => this.setWorkingHours(param, 'WEEKEND', "fromTime")}
											timeFormat='HH:mm' />
									</div>
									<div style={{float: "right", paddingRight: "20px"}}>
		                            	<DateTime dateFormat={false} value={adminStore.workingHoursDTOWeekendTimeTo}
			                                onChange={param => this.setWorkingHours(param, 'WEEKEND', "toTime")}
											timeFormat='HH:mm' />
									</div>
		                        </Col>
		                    </FormGroup>
							<FormGroup>
		                        <Col>
		                            <FormLabel><h5 className="font-color font-size">{this.mapWorkingHoursDayType('HOLIDAY')}</h5></FormLabel>
		                        </Col>
		                        <Col style={{width: "maxContent", paddingBottom: "40px"}}>
									<div style={{float: "left", paddingRight: "15px"}}>
										<DateTime dateFormat={false} value={adminStore.workingHoursDTOHolidayTimeFrom}
			                                onChange={param => this.setWorkingHours(param, 'HOLIDAY', "fromTime")}
											timeFormat='HH:mm' />
									</div>
									<div style={{float: "right", paddingRight: "20px"}}>
		                            	<DateTime dateFormat={false} value={adminStore.workingHoursDTOHolidayTimeTo}
			                                onChange={param => this.setWorkingHours(param, 'HOLIDAY', "toTime")}
											timeFormat='HH:mm' />
									</div>
		                        </Col>
		                    </FormGroup>
		                </Form>
	                </Modal.Body>
	                <Modal.Footer className='admin-notifications-modal-footer'>
						<Button className='login-registration-button-color' onClick={() => this.saveWorkingHours()}><b>Spremi</b></Button>
	                    <Button className='admin-notifications-modal-footer-cancel-button' onClick={() => this.onModalClose()}><b>Zatvori</b></Button>
	                </Modal.Footer>
					{
	                    !adminStore.successfulWorkingHoursSave
	                        ? <div className="updateErrorMessage">
    								<b>Pogreška kod unosa radnog vremena - tekst pogreške:</b>
									<p>{adminStore.errorMessage}</p>
								</div>
	                        : <div/>
	                }
					{
	                    this.state.successfulStore
	                        ? <ErrorMessage errorMessage="Radni sati su uspješno uneseni" loginButton={false}/>
	                        : <div/>
	                }
	            </Modal>

            </div>
        );
    }

	componentDidMount(): void {
        adminStore.getLocationWithWorkingHours('');
    }
}