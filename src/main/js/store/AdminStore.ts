import {action, observable} from "mobx";
import adminRepository from "../repository/AdminRepository";
import {AxiosResponse} from "axios";
import _ from 'lodash';
import AdminStatisticsDTO from "../model/AdminStatisticsDTO";

class AdminStore {
    @observable adminStatisticsDTO: AdminStatisticsDTO = new AdminStatisticsDTO();

    getAdminStatistics() {
        adminRepository.getAdminStatistics(sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
                this.adminStatisticsDTO = response.data;
            }));
    }

    
}

const adminStore = new AdminStore();

export default adminStore;