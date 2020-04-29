import {observable} from "mobx";

export default class QuizAnswer {
	@observable question: string = "";
	@observable answer: string = "";
}