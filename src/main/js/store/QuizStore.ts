import {action, observable} from "mobx";
import quizRepository from "../repository/QuizRepository";
import {AxiosResponse, AxiosError} from "axios";
import QuizDTO from "../model/QuizDTO";
import UserStore from "./UserStore";
import QuizQuestion from "../model/QuizQuestion";


class QuizStore {
    @observable quizDTO: QuizDTO[] = [];
	@observable updateQuizDTO: QuizDTO = new QuizDTO();
	@observable newQuizDTO: QuizDTO = new QuizDTO();
	@observable successfulQuizUpdate: boolean = true;
	@observable successfulQuizAdd: boolean = true;
	@observable responseErrorMessage: string = '';
	@observable adminDisplayNewQuizForm: boolean = true;	// new quiz form or update existing quiz form
	@observable adminDisplayNewQuizFormQuestions: boolean = false;
	@observable newQuizQuestions: QuizQuestion[] = [];
	@observable newQuizQuestionsNumber: number = 0;

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

}

const quizStore = new QuizStore();

export default quizStore;