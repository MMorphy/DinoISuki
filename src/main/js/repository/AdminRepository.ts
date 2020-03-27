import axios, {AxiosResponse} from 'axios';
import SubscriptionDTO from '../model/SubscriptionDTO';

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

	getAllSubscriptions(active: boolean, token: string): Promise<AxiosResponse> {
        return axios.get(`/api/subscriptions/getAllSubscriptions?active=${active}`,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

	deleteSubscription(username: string, subscriptionId: number, token: string): Promise<AxiosResponse> {
        return axios.get(`/api/subscriptions/deleteSubscription?username=${username}&subscriptionId=${subscriptionId}`,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

	getSubscriptionTypes(token: string): Promise<AxiosResponse> {
        return axios.get(`/api/subscriptions/getSubscriptionTypes`,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

	saveSubscription(subscriptionDTO: SubscriptionDTO, token: string): Promise<AxiosResponse> {
        return axios.post("/api/subscriptions/saveSubscription", subscriptionDTO,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

}

const adminRepository = new AdminRepository();

export default adminRepository;