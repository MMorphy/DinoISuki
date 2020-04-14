import {action, observable} from "mobx";
import adminRepository from "../repository/AdminRepository";
import {AxiosResponse, AxiosError} from "axios";
import AdminNotificationDTO from "../model/AdminNotificationDTO";
import UserStore from "./UserStore";

class NotificationsStore {
	@observable adminNotificationDTOList: AdminNotificationDTO[] = [];
	@observable successfulNotificationsDelete: boolean = true;
	@observable responseErrorMessage: string = '';
	@observable successfulNotificationSave: boolean = true;
	@observable newNotification: AdminNotificationDTO = new AdminNotificationDTO();
	@observable editNotification: AdminNotificationDTO = new AdminNotificationDTO();
	@observable userNotificationDTOList: AdminNotificationDTO[] = [];
	@observable hasUnreadMessages: boolean = false;

	getNotifications(srcUser: string, destUser: string) {
        adminRepository.getNotifications(srcUser, destUser, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.adminNotificationDTOList = response.data;
            }))
			.catch(action((error: AxiosError) => {
				if(error.response) {
					this.adminNotificationDTOList = [];
					if(error.response.data.includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
				}
            }));
    }

	async deleteNotifications(notificationIdList: number[]) {
        await adminRepository.deleteNotifications(notificationIdList, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.successfulNotificationsDelete = true;
            }))
			.catch(action((error: AxiosError) => {
				if(error.response) {
					if(error.response.data.includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
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
					this.adminNotificationDTOList = [];
					if(error.response.data.includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
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
					if(error.response.data.includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
					this.responseErrorMessage = error.response.data.message;
				}
				this.successfulNotificationSave = false;
            }));
		return this.successfulNotificationSave;
    }

	async getUserNotifications(username: string) {
		await adminRepository.getNotifications('', username, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.userNotificationDTOList = response.data;
            }))
			.catch(action((error: AxiosError) => {
				if(error.response) {
					this.userNotificationDTOList = [];
					if(error.response.data.includes("Expired or invalid JWT token")) {
						UserStore.clearSessionStorage();
					}
				}
            }));
		this.setHasUnreadMessages(false);
		const tempUserNotificationDTOList: AdminNotificationDTO[] = [];
		for (var i = 0; i < this.userNotificationDTOList.length; i++){
			if(this.userNotificationDTOList[i].notificationType === 'unread' || this.userNotificationDTOList[i].notificationType === 'UNREAD') {
				this.setHasUnreadMessages(true);
			}
			if(this.userNotificationDTOList[i].notificationType !== 'deleted' && this.userNotificationDTOList[i].notificationType !== 'DELETED') {
				tempUserNotificationDTOList.push(this.userNotificationDTOList[i]);
			}
		}
		this.setUserNotificationDTOList(tempUserNotificationDTOList);
	}


	/*** Util functions ***/
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

	@action
	setHasUnreadMessages(hasUnreadMessages: boolean) {
		this.hasUnreadMessages = hasUnreadMessages;
	}
	
	@action
    private setUserNotificationDTOList(userNotificationDTOList: AdminNotificationDTO[]) {
        this.userNotificationDTOList = userNotificationDTOList;
    }
    
}

const notificationsStore = new NotificationsStore();

export default notificationsStore;