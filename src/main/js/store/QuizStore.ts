import {action, observable} from "mobx";
import quizRepository from "../repository/QuizRepository";
import {AxiosResponse, AxiosError} from "axios";
import QuizDTO from "../model/QuizDTO";
import UserStore from "./UserStore";
import QuizQuestion from "../model/QuizQuestion";
import QuizAnswer from "../model/QuizAnswer";


class QuizStore {
    @observable quizDTO: QuizDTO[] = [];
	@observable updateQuizDTO: QuizDTO = new QuizDTO();
	@observable newQuizDTO: QuizDTO = new QuizDTO();
	@observable successfulQuizUpdate: boolean = true;
	@observable successfulQuizAdd: boolean = true;
	@observable successfulQuizAnswersAdd: boolean = true;
	@observable responseErrorMessage: string = '';
	@observable adminDisplayNewQuizForm: boolean = true;	// new quiz form or update existing quiz form
	@observable adminDisplayNewQuizFormQuestions: boolean = false;
	@observable newQuizQuestions: QuizQuestion[] = [];
	@observable newQuizQuestionsNumber: number = 0;
	@observable quizForUserNewDTO: QuizDTO[] = [];
	@observable quizForUserTakenDTO: QuizDTO[] = [];
	@observable userHasUntakenQuizes: boolean = false;
	@observable newQuizAnswers: QuizAnswer[] = [];

    getQuiz(name: string) {
        quizRepository.getQuiz(name, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
                this.quizDTO = response.data;
            }))
			.catch(action((error: AxiosError) => {
				if(error.response) {
					if(error.response.data.toString().includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
				}
            }));
    }

	@action
	async updateQuiz(quizDTO: QuizDTO) {
        await quizRepository.updateQuiz(quizDTO, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.successfulQuizUpdate = true;
            }))
			.catch(action((error: AxiosError) => {
				if(error.response) {
					if(error.response.data.toString().includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
					this.responseErrorMessage = error.response.data.message;
				}
				this.successfulQuizUpdate = false;
            }));
		return this.successfulQuizUpdate;
    }

	@action
	async addNewQuiz(quizDTO: QuizDTO) {
        await quizRepository.addNewQuiz(quizDTO, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.successfulQuizAdd = true;
            }))
			.catch(action((error: AxiosError) => {
				if(error.response) {
					if(error.response.data.toString().includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
					this.responseErrorMessage = error.response.data.message;
				}
				this.successfulQuizAdd = false;
            }));
		return this.successfulQuizAdd;
    }

	@action
	async getNewQuizesForUser(username: string) {
		this.setUserHasUntakenQuizes(false);
        await quizRepository.getNewQuizesForUser(username, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
                this.quizForUserNewDTO = response.data;
				if(this.quizForUserNewDTO.length > 0) {
					this.setUserHasUntakenQuizes(true);
				}
            }))
			.catch(action((error: AxiosError) => {
				if(error.response) {
					if(error.response.data.toString().includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
				}
            }));
    }

	getQuizesTakenByUser(username: string) {
        quizRepository.getQuizesTakenByUser(username, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
                this.quizForUserTakenDTO = response.data;
            }))
			.catch(action((error: AxiosError) => {
				if(error.response) {
					if(error.response.data.toString().includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
				}
            }));
    }

	@action
	async addQuizAnswers(quizDTO: QuizDTO) {
        await quizRepository.addQuizAnswers(quizDTO, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.successfulQuizAnswersAdd = true;
            }))
			.catch(action((error: AxiosError) => {
				if(error.response) {
					if(error.response.data.toString().includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
					this.responseErrorMessage = error.response.data.message;
				}
				this.successfulQuizAnswersAdd = false;
            }));
		return this.successfulQuizAnswersAdd;
    }


	/*** Util functions ***/
	@action
    setUpdateQuizDTO(updateQuizDTO: QuizDTO) {
        this.updateQuizDTO = updateQuizDTO;
    }
	@action
    updateQuizDTOHolder(value: any, key: string) {
        // @ts-ignore
        this.updateQuizDTO[key] = value;
    }
	@action
    newQuizDTOHolder(value: any, key: string) {
        // @ts-ignore
        this.newQuizDTO[key] = value;
    }
	@action
    setDisplayNewQuizForm(value: boolean) {
        this.adminDisplayNewQuizForm = value;
    }
	@action
    setDisplayNewQuizFormQuestions(value: boolean) {
        this.adminDisplayNewQuizFormQuestions = value;
    }
	@action
    addNewQuizEmptyQuestions(noOfQuestions: number) {
		this.newQuizQuestions = [];
        for (var i = 0; i < noOfQuestions; i++){
			this.newQuizQuestions.push(new QuizQuestion());
			for (var j = 0; j < this.newQuizQuestionsNumber; j++){
				this.newQuizQuestions[i].answers.push('');
			}
		}
    }
	@action
    newQuizEmptyQuestionHolder(value: any, key: string, i: number, j: number) {
		if(key === 'answers') {
			this.newQuizQuestions[i].answers[j] = value;
		} else {
	        // @ts-ignore
	        this.newQuizQuestions[i][key] = value;
		}
    }
	@action
    setNewQuizQuestionsNumber(newQuizQuestionsNumber: number) {
		this.newQuizQuestionsNumber = newQuizQuestionsNumber;
    }
	@action
    newQuizQuestionsRemoveExcess(removeFrom: number) {
		quizStore.newQuizQuestions.splice(removeFrom, (quizStore.newQuizQuestions.length - removeFrom));
    }
	@action
    setNewQuizQuestions(newQuizQuestions: QuizQuestion[]) {
		this.newQuizQuestions = newQuizQuestions;
    }
	@action
    setNewQuizDTO(newQuizDTO: QuizDTO) {
		this.newQuizDTO = newQuizDTO;
    }
	@action
    setUserHasUntakenQuizes(userHasUntakenQuizes: boolean) {
		this.userHasUntakenQuizes = userHasUntakenQuizes;
    }
	@action
    async initializeNewQuizAnswers(quizDTO: QuizDTO) {
		this.newQuizAnswers = [];
		for (var i = 0; i < quizDTO.questions.length; i++) {
			let quizAnswer: QuizAnswer = new QuizAnswer();
			quizAnswer.question = quizDTO.questions[i].question;
			this.newQuizAnswers.push(quizAnswer);
		}
    }
	@action
    async setNewQuizAnswer(questionNo: number, answer: string) {
		this.newQuizAnswers[questionNo].answer = answer;
    }

}

const quizStore = new QuizStore();

export default quizStore;