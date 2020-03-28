import {action, observable} from "mobx";
import adminRepository from "../repository/AdminRepository";
import {AxiosResponse, AxiosError} from "axios";
import AdminStatisticsDTO from "../model/AdminStatisticsDTO";
import TransactionDetailsDTO from "../model/TransactionDetailsDTO";
import SubscriptionDTO from "../model/SubscriptionDTO";
import AdminSubscriptionTypesDTO from "../model/AdminSubscriptionTypesDTO";
import AdminNotificationDTO from "../model/AdminNotificationDTO";


class AdminStore {
    @observable adminStatisticsDTO: AdminStatisticsDTO = new AdminStatisticsDTO();
	@observable transactionDetailsDTO: TransactionDetailsDTO[] = [];
	@observable transactionDeleteSuccessfull: boolean = true;
	@observable subscriptionDTOList: SubscriptionDTO[] = [];
	@observable displayOnlyActiveSubscriptins: boolean = false;
	@observable subscriptionDeleteSuccessfull: boolean = true;
	@observable newSubscription: SubscriptionDTO = new SubscriptionDTO();
	@observable adminSubscriptionTypesDTO: AdminSubscriptionTypesDTO[] = [];
	@observable successfulSubscriptionSave: boolean = true;
	@observable subscriptionSaveErrorMessage: string = '';
	@observable adminNotificationDTOList: AdminNotificationDTO[] = [];
	@observable successfulNotificationsDelete: boolean = true;
	@observable responseErrorMessage: string = '';
	@observable successfulNotificationSave: boolean = true;
	@observable newNotification: AdminNotificationDTO = new AdminNotificationDTO();
	@observable editNotification: AdminNotificationDTO = new AdminNotificationDTO();

    getAdminStatistics() {
        adminRepository.getAdminStatistics(sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
                this.adminStatisticsDTO = response.data;
            }))
			.catch(action(() => {
                // TODO
            }));
    }

	getTransactionDetails(username: string, transactionId: string) {
        adminRepository.getTransactionDetails(username, transactionId, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.transactionDetailsDTO = response.data;
            }))
			.catch(action(() => {
                this.transactionDetailsDTO = [];
            }));
    }

	@action
	async deleteTransactionDetails(id: number) {
        await adminRepository.deleteTransactionDetails(id, sessionStorage.getItem('token')!)
            .then(action(() => {
            	this.transactionDeleteSuccessfull = true;
            }))
			.catch(action(() => {
                this.transactionDeleteSuccessfull = false;
            }));
		return this.transactionDeleteSuccessfull;
    }

	getAllSubscriptions(active: boolean) {
        adminRepository.getAllSubscriptions(active, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.subscriptionDTOList = response.data;
            }))
			.catch(action(() => {
                this.subscriptionDTOList = [];
            }));
    }

	@action
	async deleteSubscription(username: string, subscriptionId: number) {
        await adminRepository.deleteSubscription(username, subscriptionId, sessionStorage.getItem('token')!)
            .then(action(() => {
            	this.subscriptionDeleteSuccessfull = true;
            }))
			.catch(action(() => {
                this.subscriptionDeleteSuccessfull = false;
            }));
		return this.subscriptionDeleteSuccessfull;
    }

	getSubscriptionTypes() {
	        adminRepository.getSubscriptionTypes(sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.adminSubscriptionTypesDTO = response.data;
            }))
			.catch(action(() => {
                this.adminSubscriptionTypesDTO = [];
            }));
    }

	@action
	async saveSubscription(subscriptionDTO: SubscriptionDTO) {
        await adminRepository.saveSubscription(subscriptionDTO, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.successfulSubscriptionSave = true;
            }))
			.catch(action((error: AxiosError) => {
				if(error.response) {
					this.subscriptionSaveErrorMessage = error.response.data.message;
				}
                this.successfulSubscriptionSave = false;
            }));
		return this.successfulSubscriptionSave;
    }

	getNotifications(srcUser: string, destUser: string) {
        adminRepository.getNotifications(srcUser, destUser, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.adminNotificationDTOList = response.data;
            }))
			.catch(action(() => {
                this.adminNotificationDTOList = [];
            }));
    }

	async deleteNotifications(notificationIdList: number[]) {
        await adminRepository.deleteNotifications(notificationIdList, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.successfulNotificationsDelete = true;
            }))
			.catch(action((error: AxiosError) => {
				if(error.response) {
					this.responseErrorMessage = error.response.data.message;
				}
                this.successfulNotificationsDelete = false;
            }));
		return this.successfulNotificationsDelete;
    }

	@action
	async addNotification(adminNotificationDTO: AdminNotificationDTO) {
        await adminRepository.addNotification(adminNotificationDTO, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.successfulNotificationSave = true;
            }))
			.catch(action((error: AxiosError) => {
				if(error.response) {
					this.responseErrorMessage = error.response.data.message;
				}
                this.successfulNotificationSave = false;
            }));
		return this.successfulNotificationSave;
    }

	@action
	async updateNotification(adminNotificationDTO: AdminNotificationDTO) {
        await adminRepository.updateNotification(adminNotificationDTO, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.successfulNotificationSave = true;
            }))
			.catch(action((error: AxiosError) => {
				if(error.response) {
					this.responseErrorMessage = error.response.data.message;
				}
                this.successfulNotificationSave = false;
            }));
		return this.successfulNotificationSave;
    }



	/*** Util functions ***/
	@action
    setDisplayOnlyActiveSubscriptins(displayOnlyActiveSubscriptins: boolean) {
        this.displayOnlyActiveSubscriptins = displayOnlyActiveSubscriptins;
    }

	@action
    newSubscriptionHolder(value: any, key: string) {
        // @ts-ignore
        this.newSubscription[key] = value;
    }

	@action
    newNotificationHolder(value: any, key: string) {
        // @ts-ignore
        this.newNotification[key] = value;
    }

	@action
    editNotificationHolder(value: any, key: string) {
        // @ts-ignore
        this.editNotification[key] = value;
    }

	@action
    setEditNotification(editNotification: AdminNotificationDTO) {
        this.editNotification = editNotification;
    }

    
}

const adminStore = new AdminStore();

export default adminStore;