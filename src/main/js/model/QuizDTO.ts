import {observable} from "mobx";
import QuizQuestion from "./QuizQuestion";
import QuizAnswer from "./QuizAnswer";

const QuizStatusEnum = Object.freeze({"NOT_PUBLISHED":0, "PUBLISHED":1, "DELETED":2});

export default class QuizDTO {
	
	@observable id: number = 0;
	@observable createdAt: Date = new Date();
	@observable name: string = '';
	@observable noOfQuestions: number = 0;
	@observable status = QuizStatusEnum.NOT_PUBLISHED;
	@observable questions: QuizQuestion[] = [];
	@observable answers: QuizAnswer[] = [];
	@observable correctAnswers: number = 0;
	@observable username: string = '';
	
}

