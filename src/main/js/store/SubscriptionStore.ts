import {action, observable} from "mobx";
import SubscriptionDTO from "../model/SubscriptionDTO";
import subscriptionRepository from "../repository/SubscriptionRepository";
import {AxiosResponse} from "axios";

class SubscriptionStore {

    @observable allSubscriptions: Array<SubscriptionDTO> = [];
    @observable activeSubscriptions: Array<SubscriptionDTO> = [];
    @observable inactiveSubscriptions: Array<SubscriptionDTO> = [];

    getAllSubscriptions(active: boolean) {
        subscriptionRepository.getAllSubscriptions(active)
            .then(action((response: AxiosResponse) => {
                this.allSubscriptions = response.data;
            }))
    }

    getActiveSubscriptionsByUser(username: string) {
        subscriptionRepository.getActiveSubscriptionsByUser(username)
            .then(action((response: AxiosResponse) => {
                this.activeSubscriptions = response.data;
            }))
    }

    getInactiveSubscriptionsByUser(username: string) {
        subscriptionRepository.getInactiveSubscriptionsByUser(username)
            .then(action((response: AxiosResponse) => {
                this.inactiveSubscriptions = response.data;
            }))
    }
}

const subscriptionStore = new SubscriptionStore();

export default subscriptionStore;