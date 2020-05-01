import axios, {AxiosResponse} from "axios";

class SubscriptionRepository {

    getAllSubscriptions(active: boolean): Promise<AxiosResponse> {
        return axios.get(`api/subscriptions/getAllSubscriptions?active=${active}`);
    }

    getActiveSubscriptionsByUser(username: string): Promise<AxiosResponse> {
        return axios.get(`api/subscriptions/getActiveSubscription?username=${username}`);
    }

    getInactiveSubscriptionsByUser(username: string): Promise<AxiosResponse> {
        return axios.get(`api/subscriptions/getInactiveSubscription?username=${username}`);
    }
}

const subscriptionRepository = new SubscriptionRepository();

export default subscriptionRepository;