import React from "react";
import { observer } from "mobx-react";
import { MDBDataTable, MDBBtn } from 'mdbreact';
import adminStore from "../../store/AdminStore";
import UserStore from "../../store/UserStore";
import { Card, Button, Col, Form, FormControl, FormGroup, FormLabel, ProgressBar } from "react-bootstrap";
import ErrorMessage from "../utils/ErrorMessage";
import 'react-dropdown/style.css';
import 'react-datetime/css/react-datetime.css';
import axios, {AxiosError} from 'axios';
import {action} from "mobx";
import AdminUploadedVideoDTO from "../../model/AdminUploadedVideoDTO";


@observer
export default class AdminVideoUpload extends React.Component<{}, { videoName: string, file: any, uploadPercentage: number, errorMessage: string }> {
	constructor(props: any) {
		super(props);
		this.state = {
			videoName: '',
			file: [],
			uploadPercentage: 0,
			errorMessage: ''
		};
	}
	
	@action
	changeArchived = async () => {
		const id: number = this.getId(event);
		let adminUploadedVideoDTO: AdminUploadedVideoDTO = adminStore.adminUploadedVideoDTOList[id];
		adminUploadedVideoDTO.archived = !adminUploadedVideoDTO.archived;
		await adminStore.updateUploadedVideo(adminUploadedVideoDTO);
	};
	
	getId = (event: any) => {
		let _id: any = event.srcElement.id;
		if(_id === undefined || _id === null || _id == "") {
			_id = event.srcElement.parentNode.id;
		}
		return _id;
	}

	setVideoName = (videoName: string) => {
		this.setState({ videoName: videoName })
	};
	
	// @ts-ignore
	setFile = ({ target: { files } }) => {
		this.setState({ file: files[0] })
	};

	uploadFile = (e: any) => {
		e.preventDefault();
		this.setState({ errorMessage: '' });
		if(this.state.videoName === undefined || this.state.videoName === '') {
			this.setState({ errorMessage: 'Niste unijeli naziv snimke' });
			return;
		}
		if(this.state.file === undefined || this.state.file === null || this.state.file.length === 0) {
			this.setState({ errorMessage: 'Niste unijeli snimku' });
			return;
		}
		
		let uploadVideo = new FormData();
		uploadVideo.append('uploadVideo', this.state.file)
		const token: string = sessionStorage.getItem('token')!;
		const fileName: string = this.state.videoName;

		const options = {
			onUploadProgress: (progressEvent: any) => {
				const { loaded, total } = progressEvent;
				let percent = Math.floor((loaded * 100) / total);
				console.log(`${loaded}kb of ${total}kb | ${percent}%`);

				if (percent < 100) {
					this.setState({ uploadPercentage: percent });
				}
			},
			headers: {
                    'content-type': 'multipart/form-data',
                    'Authorization': `Bearer ${token}`
                }
		}
		
		axios.post(`/api/admin/adminUploadVideo?name=${fileName}`, uploadVideo, options		
		).then(res => {
			this.setState({ uploadPercentage: 100 }, () => {
				setTimeout(() => {
					this.setState({ uploadPercentage: 0 });
					this.setState({ errorMessage: 'Snimka uspješno pohranjena.',
									videoName: '',
									file: []
					 });
				}, 2000);
			})
		}).catch(action((error: AxiosError) => {
			if(error.response) {
				if(error.response.data.toString().includes("Expired or invalid JWT token")) {
					UserStore.clearSessionStorage();
				}
			}
			this.setState({ errorMessage: 'Neuspješan prijenos snimke. Pokušajte ponovo.' });
			return;
        }));
		
	}


	render() {

		// data for table of uploaded videos
		const columns = [
			{
				label: '#',
				field: 'id',
				sort: 'asc',
				width: 10
			},
			{
				label: 'File',
				field: 'file',
				sort: 'asc',
				width: 30
			},
			{
				label: 'Kreiran',
				field: 'uploadedAt',
				sort: 'asc',
				width: 30
			},
			{
				label: 'Naziv snimke',
				field: 'videoName',
				sort: 'asc',
				width: 30
			},
			{
				label: 'Status',
				field: 'archived',
				width: 20
			},
			{
		        label: 'Promijena statusa',
		        field: 'changeArchived',
		        width: 10
	      	}
		];
		const rows = [];
		for (var i = 0; i < adminStore.adminUploadedVideoDTOList.length; i++) {
			let uploadedAtStr: string = adminStore.adminUploadedVideoDTOList[i].uploadedAt.toString();
			let uploadedAt: Date = new Date(uploadedAtStr.substring(0, uploadedAtStr.indexOf('.')) + 'Z');
			let archived: string = 'aktivan';
			let status: string = 'Arhiviraj';
			if (adminStore.adminUploadedVideoDTOList[i].archived) {
				archived = 'arhiviran';
				status = 'Aktiviraj';
			}
			rows.push(
				{
					id: adminStore.adminUploadedVideoDTOList[i].id,
					file: adminStore.adminUploadedVideoDTOList[i].location,
					uploadedAt: uploadedAt.toLocaleString(),
					videoName: adminStore.adminUploadedVideoDTOList[i].videoName,
					archived: archived,
					changeArchived: <MDBBtn className="admin-table-button" id={i} color="cyan" size="sm" onClick={() => this.changeArchived()}>{status}</MDBBtn>
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
							<h5 className="h5-my-profile-card-title">Pregled unesenih snimki</h5>
						</div>
					</Card.Header>
					<Card.Body>
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
								entriesLabel="Prikaži snimaka"
								searchLabel="Pronađi..."
								noRecordsFoundLabel="Nema podataka"
								tbodyTextWhite
								data={notificationsData}
							/>
						</div>
					</Card.Body>

					<Card.Header>
						<div className="row">
							<h5 className="h5-my-profile-card-title">Unos nove snimke</h5>
						</div>
					</Card.Header>
					<Card.Body>
						<div className="row">
							<Form className="admin-subscription-form-width">
								<FormGroup controlId="username">
									<Col>
										<FormLabel><h5 className="font-color font-size">Naziv snimke</h5></FormLabel>
									</Col>
									<Col>
										<FormControl value={this.state.videoName} onChange={(e: any) => this.setVideoName(e.target.value)} />
									</Col>
								</FormGroup>
								<FormGroup>
									<Col>
										<FormLabel><h5 className="font-color font-size">Snimka</h5></FormLabel>
									</Col>
									<Col>
										<input type="file" onChange={this.setFile} />
										
									</Col>
								</FormGroup>

								<FormGroup>
									<Col className="login-registration-button-center">
										<Button type="submit" className="login-registration-button-color" onClick={(e: any) => this.uploadFile(e)}><b>Unesi novu snimku</b></Button>
									</Col>
								</FormGroup>
								
								{ this.state.uploadPercentage > 0 && <ProgressBar now={this.state.uploadPercentage} animated label={`${this.state.uploadPercentage}%`} /> }
								
								{
				                    (this.state.errorMessage !== '')
				                        ? <ErrorMessage errorMessage={this.state.errorMessage} loginButton={false}/>
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
		adminStore.getUploadedVideo('');
	}

	componentWillUnmount(): void {
	}
}

