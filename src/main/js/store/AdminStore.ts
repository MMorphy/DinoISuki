import {action, observable} from "mobx";
import adminRepository from "../repository/AdminRepository";
import {AxiosResponse, AxiosError} from "axios";
import AdminStatisticsDTO from "../model/AdminStatisticsDTO";
import TransactionDetailsDTO from "../model/TransactionDetailsDTO";
import SubscriptionDTO from "../model/SubscriptionDTO";
import AdminSubscriptionTypesDTO from "../model/AdminSubscriptionTypesDTO";
import AdminLocationWithWorkingHoursDTO from "../model/AdminLocationWithWorkingHoursDTO";
import AdminUploadedVideoDTO from "../model/AdminUploadedVideoDTO";
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
	@observable errorMessage: string = '';
	@observable responseErrorMessage: string = '';
	@observable adminLocationWithWorkingHoursDTOList: AdminLocationWithWorkingHoursDTO[] = [];
	@observable workingHoursDTOWorkdayTimeFrom: Date = new Date();
	@observable workingHoursDTOWorkdayTimeTo: Date = new Date();
	@observable workingHoursDTOWeekendTimeFrom: Date = new Date();
	@observable workingHoursDTOWeekendTimeTo: Date = new Date();
	@observable workingHoursDTOHolidayTimeFrom: Date = new Date();
	@observable workingHoursDTOHolidayTimeTo: Date = new Date();
	@observable successfulWorkingHoursSave: boolean = true;
	@observable adminUploadedVideoDTOList: AdminUploadedVideoDTO[] = [];
	@observable successfulUpdateUploadedVideo: boolean = true;
	

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
					this.errorMessage = error.response.data.message;
				}
				this.successfulSubscriptionSave = false;
            }));
		return this.successfulSubscriptionSave;
    }

	getLocationWithWorkingHours(name: string) {
        adminRepository.getLocationWithWorkingHours(name, sessionStorage.getItem('token')!)
        .then(action((response: AxiosResponse) => {
        	this.adminLocationWithWorkingHoursDTOList = response.data;
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
	async saveLocationWorkingHours(adminLocationWithWorkingHoursDTO: AdminLocationWithWorkingHoursDTO) {
        await adminRepository.saveLocationWorkingHours(adminLocationWithWorkingHoursDTO, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.successfulWorkingHoursSave = true;
            }))
			.catch(action((error: AxiosError) => {
				if(error.response) {
					if(error.response.data.toString().includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
					this.errorMessage = error.response.data.message;
				}
				this.successfulWorkingHoursSave = false;
            }));
		return this.successfulWorkingHoursSave;
    }

	getUploadedVideo(name: string) {
        adminRepository.getUploadedVideo(name, sessionStorage.getItem('token')!)
        .then(action((response: AxiosResponse) => {
        	this.adminUploadedVideoDTOList = response.data;
        }))
		.catch(action((error: AxiosError) => {
			this.adminUploadedVideoDTOList = [];
			if(error.response) {
				if(error.response.data.toString().includes("Expired or invalid JWT token")) {
					UserStore.clearSessionStorage();
				}
			}
        }));
    }

	@action
	async updateUploadedVideo(adminUploadedVideoDTO: AdminUploadedVideoDTO) {
        await adminRepository.updateUploadedVideo(adminUploadedVideoDTO, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.successfulUpdateUploadedVideo = true;
            }))
			.catch(action((error: AxiosError) => {
				if(error.response) {
					if(error.response.data.toString().includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
					this.errorMessage = error.response.data.message;
				}
				this.successfulUpdateUploadedVideo = false;
            }));
		return this.successfulUpdateUploadedVideo;
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
    saveWorkingHoursDTO(time: Date, dayType: string, timeType: string) {
		switch(dayType) {
			case 'WORKDAY': {
				if(timeType === 'fromTime') {
					this.workingHoursDTOWorkdayTimeFrom = time;
				}
				if(timeType === 'toTime') {
					this.workingHoursDTOWorkdayTimeTo = time;
				}
				break;
			}
			case 'WEEKEND': {
				if(timeType === 'fromTime') {
					this.workingHoursDTOWeekendTimeFrom = time;
				}
				if(timeType === 'toTime') {
					this.workingHoursDTOWeekendTimeTo = time;
				}
				break;
			}
			case 'HOLIDAY': {
				if(timeType === 'fromTime') {
					this.workingHoursDTOHolidayTimeFrom = time;
				}
				if(timeType === 'toTime') {
					this.workingHoursDTOHolidayTimeTo = time;
				}
				break;
			}
		}
    }


    
}

const adminStore = new AdminStore();

export default adminStore;