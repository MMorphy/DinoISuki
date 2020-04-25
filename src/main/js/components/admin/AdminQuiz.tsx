import React from "react";
import {observer} from "mobx-react";
import { MDBDataTable, MDBBtn } from 'mdbreact';
import quizStore from "../../store/QuizStore";
import {Card} from "react-bootstrap";
import ErrorMessage from "../utils/ErrorMessage";
import AdminQuizNew from "./AdminQuizNew";
import AdminQuizNewQuestions from "./AdminQuizNewQuestions";

@observer
export default class AdminQuiz extends React.Component<{}, {}> {
	constructor(props: any) {
    	super(props);
    	this.state = {
			
		};
	}
	
	detailsAction = async () => {
		// @ts-ignore
		const id: number = event.srcElement.id;
		// @ts-ignore
		const status: string = quizStore.quizDTO[id].status;
		if(status === 'NOT_PUBLISHED') {
			// edit existing
			quizStore.setNewQuizDTO(quizStore.quizDTO[id]);
			quizStore.setNewQuizQuestions(quizStore.quizDTO[id].questions);
			quizStore.setDisplayNewQuizForm(false);
		}
		
	};
	
	updateStatus = async () => {
		if(event !== undefined && event.srcElement !== null) {
			// @ts-ignore
			quizStore.setUpdateQuizDTO(quizStore.quizDTO[event.srcElement.id]);
			let newStatus: string = 'NOT_PUBLISHED';
			// @ts-ignore
			const oldStatus: string = quizStore.updateQuizDTO.status;
			switch(oldStatus) {
				case 'NOT_PUBLISHED': {
					newStatus = 'PUBLISHED';
					break;
				}
				case 'PUBLISHED': {
					newStatus = 'DELETED';
					break;
				}
				case 'DELETED': {
					newStatus = 'NOT_PUBLISHED';
					break;
				}
			}
			
			quizStore.updateQuizDTOHolder(newStatus, "status");
			await quizStore.updateQuiz(quizStore.updateQuizDTO);
		}
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
	        label: 'Vrijedi od',
	        field: 'createdAt',
	        sort: 'asc',
	        width: 30
      	},
		{
	        label: 'Ime kviza',
	        field: 'name',
	        sort: 'asc',
	        width: 30
      	},
		{
	        label: 'Broj pitanja',
	        field: 'noOfQuestions',
	        sort: 'asc',
	        width: 30
      	},
		{
	        label: 'Status',
	        field: 'status',
	        sort: 'asc',
	        width: 30
      	},
		{
	        label: 'Sudjelovalo korisnika',
	        field: 'participated',
	        width: 40
      	},
		{
	        label: 'Promjena statusa',
	        field: 'changeStatus',
	        width: 20
      	},
		{
	        label: 'Detalji',
	        field: 'view',
	        width: 10
      	}
		];
		const rows = [];
		for (var i = 0; i < quizStore.quizDTO.length; i++){
			let createdAtStr: string = quizStore.quizDTO[i].createdAt.toString();
			let createdAt: Date = new Date(createdAtStr.substring(0, createdAtStr.indexOf('.')) + 'Z');
			rows.push(
				{
					id: quizStore.quizDTO[i].id,
					createdAt: createdAt.toLocaleString(),
					name: quizStore.quizDTO[i].name,
					noOfQuestions: quizStore.quizDTO[i].noOfQuestions,
					status: quizStore.quizDTO[i].status,
					participated: 0,
					changeStatus: <MDBBtn className="admin-table-button" id={i} color="cyan" size="sm" onClick={() => this.updateStatus()}>Promijeni status</MDBBtn>,
					view: <MDBBtn className="admin-table-button" id={i} color="cyan" size="sm" onClick={() => this.detailsAction()}>Detalji</MDBBtn>
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
							<h5 className="h5-my-profile-card-title">Kvizovi</h5>
	                    </div>
	                </Card.Header>
	                <Card.Body>
						<div>
						{
		                    !quizStore.successfulQuizUpdate
		                        ? <ErrorMessage errorMessage="Pogreška kod promjene statusa kviza" loginButton={false}/>
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
								infoLabel={["Prikazano", "do", "od", "kvozova"]}
								entriesLabel="Prikaži kvizova"
								searchLabel="Pronađi..."
								tbodyTextWhite
								data={notificationsData}
						    />
	                    </div>
	                </Card.Body>

	            </Card>

				{
                    quizStore.adminDisplayNewQuizForm
                        ? <AdminQuizNew/>
                        : <div/>
                }
				{
                    (quizStore.adminDisplayNewQuizFormQuestions || !quizStore.adminDisplayNewQuizForm)
                        ? <AdminQuizNewQuestions/>
                        : <div/>
                }
				
				

            </div>
        );
    }

	componentDidMount(): void {
		quizStore.getQuiz('');
    }


}

