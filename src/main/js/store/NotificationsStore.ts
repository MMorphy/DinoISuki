import {action, observable} from "mobx";
import adminRepository from "../repository/AdminRepository";
import {AxiosResponse, AxiosError} from "axios";
import AdminNotificationDTO from "../model/AdminNotificationDTO";

class NotificationsStore {
	@observable adminNotificationDTOList: AdminNotificationDTO[] = [];
	@observable successfulNotificationsDelete: boolean = true;
	@observable responseErrorMessage: string = '';
	@observable successfulNotificationSave: boolean = true;
	@observable newNotification: AdminNotificationDTO = new AdminNotificationDTO();
	@observable editNotification: AdminNotificationDTO = new AdminNotificationDTO();

	getNotifications(srcUser: string, destUser: string) {
        adminRepository.getNotifications(srcUser, destUser, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
            	this.adminNotificationDTOList = response.data;
            }))
			.catch(action((error: AxiosError) => {
                this.adminNotificationDTOList = [];
				if(error.response) {
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

const notificationsStore = new NotificationsStore();

export default notificationsStore;