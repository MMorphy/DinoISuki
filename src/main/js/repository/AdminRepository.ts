import axios, {AxiosResponse} from 'axios';
import SubscriptionDTO from '../model/SubscriptionDTO';
import AdminNotificationDTO from '../model/AdminNotificationDTO';

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

	getNotifications(srcUser: string, destUser: string, token: string): Promise<AxiosResponse> {
        return axios.get(`/api/notifications/getNotifications?srcUser=${srcUser}&destUser=${destUser}`,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

	deleteNotifications(notificationIdList: number[], token: string): Promise<AxiosResponse> {
        return axios.get(`/api/notifications/deleteNotifications?notificationIdList=${notificationIdList}`,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

	addNotification(adminNotificationDTO: AdminNotificationDTO, token: string): Promise<AxiosResponse> {
        return axios.post("/api/notifications/addNotification", adminNotificationDTO,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }

	updateNotification(adminNotificationDTO: AdminNotificationDTO, token: string): Promise<AxiosResponse> {
        return axios.post("/api/notifications/updateNotification", adminNotificationDTO,
            {
                headers: {'Authorization': `Bearer ${token}`}
            });
    }


}

const adminRepository = new AdminRepository();

export default adminRepository;