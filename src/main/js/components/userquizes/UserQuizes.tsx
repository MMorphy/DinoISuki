import React from "react";
import {observer} from "mobx-react";
import quizStore from "../../store/QuizStore";
import { MDBDataTable, MDBBtn } from 'mdbreact';
import {Card, Form, FormGroup, Col, FormLabel} from "react-bootstrap";
import QuizDTO from "../../model/QuizDTO";
import ErrorMessage from "../utils/ErrorMessage";

@observer
export default class UserQuizes extends React.Component<{}, {quizQuestions: any, id: number, displayQuizQuestions: boolean, currentQuizName: string}> {
	constructor(props: any) {
    	super(props);
		quizStore.getNewQuizesForUser(sessionStorage.getItem('username')!);
		quizStore.getQuizesTakenByUser(sessionStorage.getItem('username')!);
    	this.state = {
			quizQuestions: [],
			id: -1,
			displayQuizQuestions: false,
			currentQuizName: ''
		};
	}
	
	takeNewQuiz = async (initial: boolean) => {
		
		if(initial) {
			const _id: number = this.getId(event);
			this.setState({
		    	id: _id,
				displayQuizQuestions: true,
				currentQuizName: quizStore.quizForUserNewDTO[_id].name
		    });
			
			await quizStore.initializeNewQuizAnswers(quizStore.quizForUserNewDTO[_id]);
		}
		
		let questions: any[] = [];
		
		for (var i = 0; i < quizStore.newQuizAnswers.length; i++) {
			const questionNumber: number = i + 1;
			const questionLabel: string = 'Pitanje broj ' + questionNumber + ': ' + quizStore.newQuizAnswers[i].question;
			const questionNo: number = parseInt(`${i}`);
			let answers = [];
			for (var j = 0; j < quizStore.quizForUserNewDTO[this.state.id].questions[i].answers.length; j++) {
				const answerNo: number = parseInt(`${j}`);
				answers.push(	<Col style={{marginBottom:'10px', float: "right"}} key={questionNo + answerNo}>
									<MDBBtn className="admin-table-button" id={questionNo + '-' + answerNo} color="cyan" style={{float:"left"}} size="sm" onClick={() => this.takeTheAnswer(questionNo, answerNo)}>{quizStore.quizForUserNewDTO[this.state.id].questions[i].answers[j]}</MDBBtn>
									{
					                    (quizStore.newQuizAnswers[i].answer !== undefined && quizStore.newQuizAnswers[i].answer !== '' && quizStore.quizForUserNewDTO[this.state.id].questions[i].correctAnswer === quizStore.quizForUserNewDTO[this.state.id].questions[i].answers[j])
					                        ? 	<i id={j.toString()} style={{color:"green", marginLeft: "12px"}} className="fas fa-check"></i>
					                        : <div/>
					                }
									{
					                    (quizStore.newQuizAnswers[i].answer !== undefined && quizStore.newQuizAnswers[i].answer !== '' && quizStore.quizForUserNewDTO[this.state.id].questions[i].correctAnswer !== quizStore.newQuizAnswers[i].answer && quizStore.newQuizAnswers[i].answer === quizStore.quizForUserNewDTO[this.state.id].questions[i].answers[j])
					                        ? 	<i id={j.toString()} style={{color:"red", marginLeft: "12px"}}  className="fas fa-times"></i>
					                        : <div/>
					                }
									
								</Col>);
			}
			
			questions.push(	<FormGroup controlId={'q' + questionNo} style={{marginLeft:'20px'}} key={'q' + questionNo}>
								<FormLabel><h5 className="font-color font-size">{questionLabel}</h5></FormLabel>
								{answers}
							</FormGroup>
			);
		}
		
		this.setState({
	    	quizQuestions: questions
	    });
	};
	
	getId = (event: any) => {
		let _id: any = event.srcElement.id;
		if(_id === undefined || _id === null || _id == "") {
			_id = event.srcElement.parentNode.id;
		}
		return _id;
	}
	
	takeTheAnswer = async (questionNo: number, answerNo: number) => {
		if(quizStore.newQuizAnswers[questionNo].answer !== undefined && quizStore.newQuizAnswers[questionNo].answer !== '') {
			console.log('Question no: ' + questionNo + ' - already answered!');
			return;
		}
		
		const givenAnswer: string = quizStore.quizForUserNewDTO[this.state.id].questions[questionNo].answers[answerNo];
		
		await quizStore.setNewQuizAnswer(questionNo, givenAnswer);
		
		// refreshing questions
		this.takeNewQuiz(false);
		this.forceUpdate();
		
		// end of quiz?
		let noOfAnsweredQuestions: number = 0;
		for (var i = 0; i < quizStore.newQuizAnswers.length; i++) {
			if(quizStore.newQuizAnswers[i].answer !== undefined && quizStore.newQuizAnswers[i].answer != '') {
				noOfAnsweredQuestions++;
			}
		}
		if(noOfAnsweredQuestions === quizStore.newQuizAnswers.length) {
			// all questions answered!
			let noOfQuestionsCorrectlyAnswered: number = 0;
			for (var i = 0; i < quizStore.newQuizAnswers.length; i++) {
				if(quizStore.newQuizAnswers[i].answer === quizStore.quizForUserNewDTO[this.state.id].questions[i].correctAnswer) {
					noOfQuestionsCorrectlyAnswered++;
				}
			}
			
			let storeNewQuizAnswersDTO: QuizDTO = quizStore.quizForUserNewDTO[this.state.id];
			storeNewQuizAnswersDTO.answers = quizStore.newQuizAnswers;
			storeNewQuizAnswersDTO.correctAnswers = noOfQuestionsCorrectlyAnswered;
			storeNewQuizAnswersDTO.username = sessionStorage.getItem('username')!;
			
			const success: boolean = await quizStore.addQuizAnswers(storeNewQuizAnswersDTO);
			if(success) {
				// refresh table with new data
				quizStore.getNewQuizesForUser(sessionStorage.getItem('username')!);
				quizStore.getQuizesTakenByUser(sessionStorage.getItem('username')!);
			}
		}
		
	}
	
	viewQuizDetails = async () => {
		const _id: number = this.getId(event);
		
		let questions: any[] = [];
		
		for (var i = 0; i < quizStore.quizForUserTakenDTO[_id].questions.length; i++) {
			const questionNumber: number = i + 1;
			const questionLabel: string = 'Pitanje broj ' + questionNumber + ': ' + quizStore.quizForUserTakenDTO[_id].questions[i].question;
			const questionNo: number = parseInt(`${i}`);
			let answers = [];
			for (var j = 0; j < quizStore.quizForUserTakenDTO[_id].questions[i].answers.length; j++) {
				const answerNo: number = parseInt(`${j}`);
				answers.push(	<Col style={{marginBottom:'10px', float: "right"}} key={questionNo + answerNo}>
									<MDBBtn className="admin-table-button" id={questionNo + '-' + answerNo} color="cyan" style={{float:"left", cursor: "unset"}} size="sm">{quizStore.quizForUserTakenDTO[_id].questions[i].answers[j]}</MDBBtn>
									{
					                    (quizStore.quizForUserTakenDTO[_id].questions[i].correctAnswer === quizStore.quizForUserTakenDTO[_id].questions[i].answers[j])
					                        ? 	<i id={j.toString()} style={{color:"green", marginLeft: "12px"}} className="fas fa-check"></i>
					                        : <div/>
					                }
									{
					                    (quizStore.quizForUserTakenDTO[_id].questions[i].correctAnswer !== quizStore.quizForUserTakenDTO[_id].answers[i].answer && quizStore.quizForUserTakenDTO[_id].answers[i].answer === quizStore.quizForUserTakenDTO[_id].questions[i].answers[j])
					                        ? 	<i id={j.toString()} style={{color:"red", marginLeft: "12px"}}  className="fas fa-times"></i>
					                        : <div/>
					                }
									
								</Col>);
			}
			
			questions.push(	<FormGroup controlId={'q' + questionNo} style={{marginLeft:'20px'}} key={'q' + questionNo}>
								<FormLabel><h5 className="font-color font-size">{questionLabel}</h5></FormLabel>
								{answers}
							</FormGroup>
			);
		}
		
		this.setState({
	    	quizQuestions: questions,
			displayQuizQuestions: true,
			currentQuizName: quizStore.quizForUserTakenDTO[_id].name
	    });
		
	}
	
	
    render() {
		document.title = "Kvizovi";
		
		// data for table of quiz
		const columns = [
		{
	        label: 'Naziv kviza',
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
	        label: 'Broj točnih odgovora',
	        field: 'correctAnswers',
	        sort: 'asc',
	        width: 30
	  	},
		{
	        label: 'Akcija',
	        field: 'action',
	        width: 30
	  	}
		];
		const rows = [];
		for (var i = 0; i < quizStore.quizForUserNewDTO.length; i++){
			rows.push(
				{
					name: quizStore.quizForUserNewDTO[i].name,
					noOfQuestions: quizStore.quizForUserNewDTO[i].noOfQuestions,
					correctAnswers: "",
					action: <MDBBtn className="admin-table-button" id={i} color="cyan" size="sm" onClick={() => this.takeNewQuiz(true)}>Pokreni kviz</MDBBtn>
				}
			)
		}
		
		for (var i = 0; i < quizStore.quizForUserTakenDTO.length; i++){
			rows.push(
				{
					name: quizStore.quizForUserTakenDTO[i].name,
					noOfQuestions: quizStore.quizForUserTakenDTO[i].noOfQuestions,
					correctAnswers: quizStore.quizForUserTakenDTO[i].correctAnswers,
					action: <MDBBtn className="admin-table-button" id={i} color="cyan" size="sm" onClick={() => this.viewQuizDetails()}>Detalji</MDBBtn>
				}
			)
		}
	
		// combined columns and rows for quiz data
		const quizData = {
		    columns,
		    rows
		};
		
        return (
            <div className="about-go2play-margin">
				<Card className="my-profile-card card-style-center">
	                <Card.Header>
	                    <div className="row">
							<h5 className="h5-my-profile-card-title">Kvizovi</h5>
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
								infoLabel={["Prikazano", "do", "od", "poruka"]}
								entriesLabel="Prikaži poruka"
								searchLabel="Pronađi..."
								noRecordsFoundLabel="Nema podataka"
								tbodyTextWhite
								searching={false}
								data={quizData}
						    >
							</MDBDataTable>
	                    </div>
	                </Card.Body>
	            </Card>
				
				
				{
                    (this.state.displayQuizQuestions)
                        ? 	<div>
								<Card className='my-profile-card'>
									<Card.Header>
							    	    <div className="row">
											<h5 className="h5-my-profile-card-title">Kviz: "{this.state.currentQuizName}" - pitanja</h5>
							    		</div>
							    	</Card.Header>
							    	<Card.Body>
						
										<div className="row">
											<Form style={{margin:"auto"}}>
								
												{this.state.quizQuestions}
								
											</Form>
										</div>
						
									</Card.Body>
								</Card>
							</div>
						: 	<div/>
                }
				{
                    !quizStore.successfulQuizAnswersAdd
                        ? <ErrorMessage errorMessage="Pogreška kod spremanja odgovora na kviz" loginButton={false}/>
                        : <div/>
                }

            </div>
        );
    }

	componentDidMount(): void {
		quizStore.setUserHasUntakenQuizes(false);
	}
	
}