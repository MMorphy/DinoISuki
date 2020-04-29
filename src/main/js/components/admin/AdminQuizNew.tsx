import React from "react";
import {observer} from "mobx-react";
import quizStore from "../../store/QuizStore";
import {Card, Button, Col, Form, FormControl, FormGroup, FormLabel} from "react-bootstrap";
import ErrorMessage from "../utils/ErrorMessage";

@observer
export default class AdminQuizNew extends React.Component<{}, {isError: boolean, errorMessage: string}> {
	constructor(props: any) {
    	super(props);
    	this.state = {
			isError: false,
			errorMessage: ''
		};
	}
	
	createNewQuiz = async () => {
		if(quizStore.adminDisplayNewQuizFormQuestions) {
			quizStore.setDisplayNewQuizFormQuestions(false);
			return;
		}
		if(quizStore.newQuizDTO.name === undefined || quizStore.newQuizDTO.name === null || quizStore.newQuizDTO.name === '') {
			this.setState({
		    	isError: true,
				errorMessage: 'Ime kviza je obavezno'
		    });
			return;
		}
		if(quizStore.newQuizDTO.noOfQuestions === undefined || quizStore.newQuizDTO.noOfQuestions === null || quizStore.newQuizDTO.noOfQuestions <= 0) {
			this.setState({
		    	isError: true,
				errorMessage: 'Broj pitanja mora biti veći od 0'
		    });
			return;
		}
		if(quizStore.newQuizQuestionsNumber === undefined || quizStore.newQuizQuestionsNumber === null || quizStore.newQuizQuestionsNumber <= 1) {
			this.setState({
		    	isError: true,
				errorMessage: 'Broj odgovora na pitanja mora bit veći od 1'
		    });
			return;
		}
		this.setState({
	    	isError: false,
			errorMessage: ''
	    });
		// creating empty placeholders for questions/answers
		quizStore.addNewQuizEmptyQuestions(quizStore.newQuizDTO.noOfQuestions);
		quizStore.successfulQuizAdd = true;
		quizStore.responseErrorMessage = '';
		quizStore.setDisplayNewQuizFormQuestions(true);
	};
	
    render() {
	
        return (
            <div>
				<Card className="my-profile-card">
					<Card.Header>
	                    <div className="row">
							<h5 className="h5-my-profile-card-title">Unos novog kviza</h5>
	                    </div>
	                </Card.Header>
	                <Card.Body>
	                    <div className="row">
							<Form className="admin-subscription-form-width">
			                    <FormGroup controlId="name">
			                        <Col>
			                            <FormLabel><h5 className="font-color font-size">Ime kviza</h5></FormLabel>
			                        </Col>
			                        <Col>
			                            <FormControl value={quizStore.newQuizDTO.name} type="username" onChange={(e: any) => quizStore.newQuizDTOHolder(e.target.value, "name")}/>
			                        </Col>
			                    </FormGroup>
			                    <FormGroup controlId="numberOfQuestions">
			                        <Col>
			                            <FormLabel><h5 className="font-color font-size">Broj pitanja</h5></FormLabel>
			                        </Col>
			                        <Col>
										{/*
										 // @ts-ignore */}
			                            <FormControl type="number" value={quizStore.newQuizDTO.noOfQuestions} onChange={(e: any) => quizStore.newQuizDTOHolder(e.target.value, "noOfQuestions")}/>
			                        </Col>
			                    </FormGroup>
								<FormGroup controlId="numberOfAnswers">
			                        <Col>
			                            <FormLabel><h5 className="font-color font-size">Broj ponuđenih odgovora</h5></FormLabel>
			                        </Col>
			                        <Col>
										{/*
										 // @ts-ignore */}
			                            <FormControl type="number" value={quizStore.newQuizQuestionsNumber} onChange={(e: any) => quizStore.setNewQuizQuestionsNumber(e.target.value)}/>
			                        </Col>
			                    </FormGroup>

								<FormGroup>
			                        <Col className="login-registration-button-center">
			                            <Button type="submit" className="login-registration-button-color" onClick={() => this.createNewQuiz()}><b>Kreiraj novi kviz</b></Button>
			                        </Col>
			                    </FormGroup>
								{
				                    this.state.isError
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
    }


}

