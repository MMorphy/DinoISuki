import {observable} from "mobx";

export default class QuizQuestion {
	@observable question: string = "";
	@observable answers: string[] = [];
	@observable correctAnswer: string = "";
}