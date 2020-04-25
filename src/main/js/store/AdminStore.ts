import {action, observable} from "mobx";
import adminRepository from "../repository/AdminRepository";
import {AxiosResponse, AxiosError} from "axios";
import AdminStatisticsDTO from "../model/AdminStatisticsDTO";
import TransactionDetailsDTO from "../model/TransactionDetailsDTO";
import SubscriptionDTO from "../model/SubscriptionDTO";
import AdminSubscriptionTypesDTO from "../model/AdminSubscriptionTypesDTO";
import UserStore from "./UserStore";

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
	@observable responseErrorMessage: string = '';

    getAdminStatistics() {
        adminRepository.getAdminStatistics(sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
                this.adminStatisticsDTO = response.data;
            }))
			.catch(action((error: AxiosError) => {
				if(error.response) {
					if(error.response.data.toString().includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
				}
            }));
    }

	getTransactionDetails(username: string, transactionId: string) {
        adminRepository.getTransactionDetails(username, transactionId, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.transactionDetailsDTO = response.data;
            }))
			.catch(action((error: AxiosError) => {
				this.transactionDetailsDTO = [];
				if(error.response) {
					if(error.response.data.toString().includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
				}
            }));
    }

	@action
	async deleteTransactionDetails(id: number) {
        await adminRepository.deleteTransactionDetails(id, sessionStorage.getItem('token')!)
            .then(action(() => {
            	this.transactionDeleteSuccessfull = true;
            }))
			.catch(action((error: AxiosError) => {
				this.transactionDeleteSuccessfull = false;
				if(error.response) {
					if(error.response.data.toString().includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
				}
            }));
		return this.transactionDeleteSuccessfull;
    }

	getAllSubscriptions(active: boolean) {
        adminRepository.getAllSubscriptions(active, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.subscriptionDTOList = response.data;
            }))
			.catch(action((error: AxiosError) => {
				this.subscriptionDTOList = [];
				if(error.response) {
					if(error.response.data.toString().includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
				}
            }));
    }

	@action
	async deleteSubscription(username: string, subscriptionId: number) {
        await adminRepository.deleteSubscription(username, subscriptionId, sessionStorage.getItem('token')!)
            .then(action(() => {
            	this.subscriptionDeleteSuccessfull = true;
            }))
			.catch(action((error: AxiosError) => {
				this.subscriptionDeleteSuccessfull = false;
				if(error.response) {
					if(error.response.data.toString().includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
				}
            }));
		return this.subscriptionDeleteSuccessfull;
    }

	getSubscriptionTypes() {
	        adminRepository.getSubscriptionTypes(sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.adminSubscriptionTypesDTO = response.data;
            }))
			.catch(action((error: AxiosError) => {
				this.adminSubscriptionTypesDTO = [];
				if(error.response) {
					if(error.response.data.toString().includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
				}
            }));
    }

	@action
	async saveSubscription(subscriptionDTO: SubscriptionDTO) {
        await adminRepository.saveSubscription(subscriptionDTO, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.successfulSubscriptionSave = true;
            }))
			.catch(action((error: AxiosError) => {
				this.adminSubscriptionTypesDTO = [];
				if(error.response) {
					if(error.response.data.toString().includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
					this.subscriptionSaveErrorMessage = error.response.data.message;
				}
				this.successfulSubscriptionSave = false;
            }));
		return this.successfulSubscriptionSave;
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

    
}

const adminStore = new AdminStore();

export default adminStore;