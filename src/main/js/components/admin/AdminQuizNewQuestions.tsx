import React from "react";
import {observer} from "mobx-react";
import quizStore from "../../store/QuizStore";
import {Card, Button, Col, Form, FormControl, FormGroup, FormLabel} from "react-bootstrap";
import ErrorMessage from "../utils/ErrorMessage";
import Dropdown from 'react-dropdown';
import QuizDTO from '../../model/QuizDTO';

@observer
export default class AdminQuizNewQuestions extends React.Component<{}, {isError: boolean, errorMessage: string, successfulCreate: boolean, successfulUpdate: boolean}> {
	constructor(props: any) {
    	super(props);
    	this.state = {
			isError: false,
			errorMessage: '',
			successfulCreate: false,
			successfulUpdate: false
		};
	}
	
	storeQuiz = async (e: any) => {
		e.preventDefault();
		if(quizStore.newQuizQuestions[0].question === undefined || quizStore.newQuizQuestions[0].question === '') {
			this.setState({
		    	isError: true,
				errorMessage: 'Niste unijeli prvo pitanje'
		    });
			return;
		}
		let foundEmptyQuestion: boolean = false;
		for (var i = 0; i < quizStore.newQuizQuestions.length; i++) {
			if(quizStore.newQuizQuestions[i].question !== undefined && quizStore.newQuizQuestions[i].question !== '') {
				// questions are not entered in order
				if(foundEmptyQuestion) {
					this.setState({
				    	isError: true,
						errorMessage: 'Niste unosili pitanja po redu. Premijestite ili obrišite pitanje: ' + `${i+1}`
				    });
					return;
				}
				// question is entered, are other fields entered?
				for (var j = 0; j < quizStore.newQuizQuestions[i].answers.length; j++) {
					if(quizStore.newQuizQuestions[i].answers[j] === undefined || quizStore.newQuizQuestions[i].answers[j] === '') {
						this.setState({
					    	isError: true,
							errorMessage: 'Niste unijeli sve odgovore na pitanje broj: ' + `${i+1}`
					    });
						return;
					}
				}
				if(quizStore.newQuizQuestions[i].correctAnswer === undefined || quizStore.newQuizQuestions[i].correctAnswer === '') {
					this.setState({
				    	isError: true,
						errorMessage: 'Niste unijeli točan odgovor na pitanje broj: ' + `${i+1}`
				    });
					return;
				}
			} else {
				// found empty question - there should be no additional question from this pont on
				foundEmptyQuestion = true;
			}
		}
		// everything entered OK
		this.setState({
	    	isError: false,
			errorMessage: ''
	    });
		// preparing quiz - removing empty questions
		for (var i = 0; i < quizStore.newQuizQuestions.length; i++) {
			if(quizStore.newQuizQuestions[i].question === undefined || quizStore.newQuizQuestions[i].question === '') {
				foundEmptyQuestion = true;
				quizStore.newQuizDTOHolder(i, "noOfQuestions");
				quizStore.newQuizQuestionsRemoveExcess(i);
				break;
			}
		}
		
		if(quizStore.adminDisplayNewQuizForm) {
			// saving the new quiz
			quizStore.newQuizDTOHolder(quizStore.newQuizQuestions, "questions");
			const success: boolean = await quizStore.addNewQuiz(quizStore.newQuizDTO);
			if(success) {
				this.setState({
			    	successfulCreate: true,
			    });
				quizStore.getQuiz('');
			}
		} else {
			// updating existing quiz
			quizStore.newQuizDTOHolder(quizStore.newQuizQuestions, "questions");
			const success: boolean = await quizStore.updateQuiz(quizStore.newQuizDTO);
			if(success) {
				this.setState({
			    	successfulUpdate: true,
			    });
			}
		}
	};
	
	dropdownChange = (param: any, questionNo: number) => {
		quizStore.newQuizEmptyQuestionHolder(param.value, "correctAnswer", questionNo, 0);
	}
	
	backToNewQuiz = () => {
		quizStore.setNewQuizDTO(new QuizDTO());
		quizStore.setDisplayNewQuizForm(true);
	}
	
    render() {
	
		// itterating trough number of questions and creating as many forms as needed
		let questions = [];
		for (var i = 0; i < quizStore.newQuizQuestions.length; i++){
			const questionNumber: number = i + 1;
			const questionLabel: string = 'Pitanje broj ' + questionNumber;
			const questionNo: number = parseInt(`${i}`);
			// @ts-ignore-start
			questions.push(	<FormGroup controlId={'q' + questionNo} key={'q' + questionNo}>
								<Col> <FormLabel><h5 className="font-color font-size">{questionLabel}</h5></FormLabel> </Col>
								{/*
								 // @ts-ignore */}
								<Col> <FormControl as="textarea" rows="3" value={quizStore.newQuizQuestions[questionNo].question} type="username" onChange={(e: any) => quizStore.newQuizEmptyQuestionHolder(e.target.value, "question", questionNo, 0)}/> </Col>
							</FormGroup>
			);
			// @ts-ignore-end				
			
			for (var j = 0; j < quizStore.newQuizQuestions[questionNo].answers.length; j++) {
				// for each question itterating trough number of answers and creating as many forms as needed
				const answerNumber: number = j + 1;
				const answerLabel: string = 'Odgovor broj ' + answerNumber;
				const answerNo: number = parseInt(`${j}`);
				
				questions.push(	<FormGroup controlId={'a' + questionNo + answerNo} style={{marginLeft:'20px'}}  key={'a' + questionNo + answerNo}>
									<Col> <FormLabel><h5 className="font-color font-size">{answerLabel}</h5></FormLabel> </Col>
									<Col> <FormControl value={quizStore.newQuizQuestions[questionNo].answers[answerNo]} type="username" onChange={(e: any) => quizStore.newQuizEmptyQuestionHolder(e.target.value, "answers", questionNo, answerNo)}/> </Col>
								</FormGroup>
				);
				
			}
			
			questions.push(	<FormGroup key={'ca' + questionNo} style={{paddingBottom:'25px'}}>
								<Col> <FormLabel><h5 className="font-color font-size">Točan odgovor</h5></FormLabel> </Col>
								<Col> <Dropdown key={questionNo} options={quizStore.newQuizQuestions[questionNo].answers} onChange={param => this.dropdownChange(param, questionNo)} value={quizStore.newQuizQuestions[questionNo].correctAnswer} placeholder="Odaberi točan odgovor" /> </Col>
							</FormGroup>
			);
			
	    }
	
        return (
            <div>
				<Card className="my-profile-card">
					<Card.Header>
	                    <div className="row">
							<h5 className="h5-my-profile-card-title">Pitanja</h5>
	                    </div>
	                </Card.Header>
	                <Card.Body>
	                    <div className="row">
							<Form className="admin-subscription-form-width">
			                    {questions}

								<FormGroup>
			                        <Col className="login-registration-button-center" style={{textAlign:"center", display:"block"}}>
			                            <Button type="submit" className="login-registration-button-color" onClick={(e: any) => this.storeQuiz(e)}><b>Spremi kviz</b></Button>
										{
						                    !quizStore.adminDisplayNewQuizForm
						                        ? <Button type="submit" className="login-registration-button-color" style={{marginLeft: "20px"}} onClick={() => this.backToNewQuiz()}><b>Novi kviz</b></Button>
						                        : <div/>
						                }
			                        </Col>
			                    </FormGroup>

			                        
								{
				                    this.state.isError
				                        ? <ErrorMessage errorMessage={this.state.errorMessage} loginButton={false}/>
				                        : <div/>
				                }
								{
				                    (!quizStore.successfulQuizAdd && quizStore.adminDisplayNewQuizForm)
				                        ? <div className="updateErrorMessage">
	            								<b>Pogreška kod kreiranja kviza - tekst pogreške:</b>
												<p>{quizStore.responseErrorMessage}</p>
											</div>
				                        : <div/>
				                }
								{
				                    (this.state.successfulCreate && quizStore.adminDisplayNewQuizForm)
				                        ? <ErrorMessage errorMessage="Kviz je uspješno kreiran" loginButton={false}/>
				                        : <div/>
				                }

								{
				                    (!quizStore.successfulQuizUpdate && !quizStore.adminDisplayNewQuizForm)
				                        ? <div className="updateErrorMessage">
	            								<b>Pogreška kod promijene kviza - tekst pogreške:</b>
												<p>{quizStore.responseErrorMessage}</p>
											</div>
				                        : <div/>
				                }
								{
				                    (this.state.successfulUpdate && !quizStore.adminDisplayNewQuizForm)
				                        ? <ErrorMessage errorMessage="Kviz je uspješno izmijenjen" loginButton={false}/>
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
    }


}

