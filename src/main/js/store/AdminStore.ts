import {action, observable} from "mobx";
import adminRepository from "../repository/AdminRepository";
import {AxiosResponse} from "axios";
import _ from 'lodash';
import AdminStatisticsDTO from "../model/AdminStatisticsDTO";
import TransactionDetailsDTO from "../model/TransactionDetailsDTO";

class AdminStore {
    @observable adminStatisticsDTO: AdminStatisticsDTO = new AdminStatisticsDTO();
	@observable transactionDetailsDTO: TransactionDetailsDTO[] = [];
	@observable transactionDeleteSuccessfull: boolean = true;
	

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

    
}

const adminStore = new AdminStore();

export default adminStore;