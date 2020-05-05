import React from "react";
import {observer} from "mobx-react";
import { MDBDataTable, MDBBtn } from 'mdbreact';
import quizStore from "../../store/QuizStore";
import {Card, Form, FormGroup, Col, FormLabel} from "react-bootstrap";

@observer
export default class AdminQuizAnswers extends React.Component<{}, {quizId: number, quizQuestionsAndAnswers: any, displayQuestionsAndAnswers: boolean, username: string}> {
	constructor(props: any) {
    	super(props);
		let quizId: number = -1;
		if(quizStore.allAnswersForQuiz.length > 0) {
			for (var i = 0; i < quizStore.quizDTO.length; i++){
				if(quizStore.quizDTO[i].name === quizStore.allAnswersForQuiz[0].name) {
					quizId = i;
					break;
				}
			}
		}
    	this.state = {
			quizId: quizId,
			quizQuestionsAndAnswers: [],
			displayQuestionsAndAnswers: false,
			username: ''
		};
	}
	
	answerDetailsAction = async () => {
		// @ts-ignore
		const id: number = event.srcElement.id;
		
		this.setState({
			displayQuestionsAndAnswers: false,
			username: quizStore.allAnswersForQuiz[id].username
	    });
		
		// recreating the quiz based on user answers
		let quizQuestionsAndAnswers: any[] = [];
		for(var i = 0; i < quizStore.quizDTO[this.state.quizId].questions.length; i++) {
			
			const questionNumber: number = i + 1;
			const questionLabel: string = 'Pitanje broj ' + questionNumber + ': ' + quizStore.quizDTO[this.state.quizId].questions[i].question;
			const questionNo: number = parseInt(`${i}`);
			let answers = [];
			for (var j = 0; j < quizStore.quizDTO[this.state.quizId].questions[i].answers.length; j++) {
				const answerNo: number = parseInt(`${j}`);
				answers.push(	<Col style={{marginBottom:'10px', float: "right"}} key={questionNo + answerNo}>
									<MDBBtn className="admin-table-button" id={questionNo + '-' + answerNo} color="cyan" style={{float:"left"}} size="sm">{quizStore.quizDTO[this.state.quizId].questions[i].answers[j]}</MDBBtn>
									{
						                (quizStore.quizDTO[this.state.quizId].questions[i].correctAnswer === quizStore.quizDTO[this.state.quizId].questions[i].answers[j])
						                    ? 	<i id={j.toString()} style={{color:"green", marginLeft: "12px"}} className="fas fa-check"></i>
						                    : <div/>
						            }
									{
						                (quizStore.allAnswersForQuiz[id].answers[i].answer === quizStore.quizDTO[this.state.quizId].questions[i].answers[j] && quizStore.allAnswersForQuiz[id].answers[i].answer !== quizStore.quizDTO[this.state.quizId].questions[i].correctAnswer)
						                    ? 	<i id={j.toString()} style={{color:"red", marginLeft: "12px"}}  className="fas fa-times"></i>
						                    : <div/>
						            }
									
								</Col>);
			}
			
			quizQuestionsAndAnswers.push(	<FormGroup controlId={'q' + questionNo} style={{marginLeft:'20px'}} key={'q' + questionNo}>
								<FormLabel><h5 className="font-color font-size">{questionLabel}</h5></FormLabel>
								{answers}
							</FormGroup>
			);
			
		}
		
		this.setState({
	    	quizQuestionsAndAnswers: quizQuestionsAndAnswers,
			displayQuestionsAndAnswers: true
	    });
		
	};

    render() {
		// data for table of user answers
		const columns = [
		{
			label: '#',
	        field: 'id',
	        sort: 'asc',
	        width: 10
      	},
      	{
	        label: 'Korisnik',
	        field: 'username',
	        sort: 'asc',
	        width: 30
      	},
		{
	        label: 'Završen u',
	        field: 'takenAt',
	        sort: 'asc',
	        width: 30
      	},
		{
	        label: 'Točnih odgovora',
	        field: 'correctAnswers',
	        sort: 'asc',
	        width: 30
      	},
		{
	        label: 'Ukupno pitanja',
	        field: 'totalNumberOfQuestions',
	        sort: 'asc',
	        width: 30
      	},
		{
	        label: 'Postotak riješenosti kviza',
	        field: 'percentage',
	        sort: 'asc',
	        width: 30
      	},
		{
	        label: 'Detalji',
	        field: 'view',
	        width: 10
      	}
		];
		const rows: any[] = [];
		
		for (var i = 0; i < quizStore.allAnswersForQuiz.length; i++){
			let createdAtStr: string = quizStore.allAnswersForQuiz[i].createdAt.toString();
			let createdAt: Date = new Date(createdAtStr.substring(0, createdAtStr.indexOf('.')) + 'Z');
			const percentageNum: number = Math.round(quizStore.allAnswersForQuiz[i].correctAnswers / quizStore.quizDTO[this.state.quizId].noOfQuestions * 100);
			const percentageStr: string = percentageNum + '%';
			rows.push(
				{
					id: quizStore.allAnswersForQuiz[i].id,
					username: quizStore.allAnswersForQuiz[i].username,
					correctAnswers: quizStore.allAnswersForQuiz[i].correctAnswers,
					totalNumberOfQuestions: quizStore.quizDTO[this.state.quizId].noOfQuestions,
					percentage: percentageStr,
					takenAt: createdAt.toLocaleString(),
					view: <MDBBtn className="admin-table-button" id={i} color="cyan" size="sm" onClick={() => this.answerDetailsAction()}>Detalji</MDBBtn>
				}
			);
		}

		// combined columns and rows for notifications data
		const quizAnswersData = {
		    columns,
		    rows
		};
		
        return (
            <div>
				<Card className="my-profile-card card-style-center" style={{margin:"auto", width:"92%"}}>
	                <Card.Header>
	                    <div className="row">
							<h5 className="h5-my-profile-card-title">Odgovori</h5>
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
								infoLabel={["Prikazano", "do", "od", "odgovora"]}
								entriesLabel="Prikaži odgovora"
								searchLabel="Pronađi..."
								noRecordsFoundLabel="Nema podataka"
								tbodyTextWhite
								data={quizAnswersData}
						    />
	                    </div>
	                </Card.Body>

	            </Card>

				{
					(this.state.displayQuestionsAndAnswers)
	                    ? 	<div>
								<Card className='my-profile-card'>
									<Card.Header>
							    	    <div className="row">
											<h5 className="h5-my-profile-card-title">Detaljni odgovori korisnika: {this.state.username}</h5>
							    		</div>
							    	</Card.Header>
							    	<Card.Body>
						
										<div className="row">
											<Form style={{margin:"auto"}}>
								
												{this.state.quizQuestionsAndAnswers}
								
											</Form>
										</div>
						
									</Card.Body>
								</Card>
							</div>
						: 	<div/>
                }

            </div>
        );
    }

	componentDidMount(): void {
    }

}

