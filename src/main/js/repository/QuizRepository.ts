import axios, {AxiosResponse} from 'axios';
import QuizDTO from '../model/QuizDTO';

class QuizRepository {

    getQuiz(name: string, token: string): Promise<AxiosResponse> {
		return axios.get(`/api/admin/getQuiz?name=${name}`,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

	updateQuiz(quizDTO: QuizDTO, token: string): Promise<AxiosResponse> {
        return axios.post("/api/admin/updateQuiz", quizDTO,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

	addNewQuiz(quizDTO: QuizDTO, token: string): Promise<AxiosResponse> {
        return axios.post("/api/admin/addNewQuiz", quizDTO,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

	getNewQuizesForUser(username: string, token: string): Promise<AxiosResponse> {
		return axios.get(`/api/admin/getNewQuizesForUser?username=${username}`,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

	getQuizesTakenByUser(username: string, token: string): Promise<AxiosResponse> {
		return axios.get(`/api/admin/getQuizesTakenByUser?username=${username}`,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

	addQuizAnswers(quizDTO: QuizDTO, token: string): Promise<AxiosResponse> {
        return axios.post("/api/admin/addQuizAnswers", quizDTO,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

}

const quizRepository = new QuizRepository();

export default quizRepository;