import axios, {AxiosResponse} from 'axios';

class AdminRepository {

    getAdminStatistics(token: string): Promise<AxiosResponse> {
		return axios.get("/api/admin/getAdminStatistics",
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

	getTransactionDetails(username: string, transactionId: string, token: string): Promise<AxiosResponse> {
        return axios.get(`/api/transactions/getTransactionDetails?username=${username}&transactionId=${transactionId}`,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

	deleteTransactionDetails(id: number, token: string): Promise<AxiosResponse> {
        return axios.get(`/api/transactions/deleteTransactionDetails?id=${id}`,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

}

const adminRepository = new AdminRepository();

export default adminRepository;