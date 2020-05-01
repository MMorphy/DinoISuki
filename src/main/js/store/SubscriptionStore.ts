import {action, observable} from "mobx";
import SubscriptionDTO from "../model/SubscriptionDTO";
import subscriptionRepository from "../repository/SubscriptionRepository";
import {AxiosResponse} from "axios";

class SubscriptionStore {

    @observable activeSubscriptions: Array<SubscriptionDTO> = [];
    @observable inactiveSubscriptions: Array<SubscriptionDTO> = [];

    getActiveSubscriptionsByUser() {
        subscriptionRepository.getActiveSubscriptionsByUser(sessionStorage.getItem('username')!)
            .then(action((response: AxiosResponse) => {
                this.activeSubscriptions = response.data;
            }))
    }

    getInactiveSubscriptionsByUser() {
        subscriptionRepository.getInactiveSubscriptionsByUser(sessionStorage.getItem('username')!)
            .then(action((response: AxiosResponse) => {
                this.inactiveSubscriptions = response.data;
            }))
    }
}

const subscriptionStore = new SubscriptionStore();

export default subscriptionStore;