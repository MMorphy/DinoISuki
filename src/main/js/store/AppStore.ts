import {action, observable} from "mobx";

class AppStore {
    @observable isSidebarVisible: boolean = false;
    @observable isLocationDropdownVisible: boolean = false;
    @observable isEditModalVisible: boolean = false;
    @observable isSecurityOfOnlinePaymentsModalVisible: boolean = false;
    @observable isChangePasswordModalVisible: boolean = false;
    @observable isLoginPasswordVisible: boolean = false;

    @observable showUpdateErrorMessage: boolean = false;
    @observable showSuccessfulUpdateMessage: boolean = false;

    @observable showSuccessfulPasswordUpdateMessage: boolean = false;
    @observable showUnsuccessfulPasswordUpdateMessage: boolean = false;
    @observable showWrongOldPasswordErrorMessage: boolean = false;
    @observable showDifferentConfirmedPasswordErrorMessage: boolean = false;
    @observable showDifferentPassAtRegistrationErrorMessage: boolean = false;

    @observable unsuccessfulRegistration: boolean = false;
    @observable successfulRegistration: boolean = false;
    @observable unsuccessfulLogin: boolean = false;

    @action
    changeSidebarVisibility() {
        this.isSidebarVisible = !this.isSidebarVisible;
    }

    @action
    changeLocationDropdownVisibility() {
        this.isLocationDropdownVisible = !this.isLocationDropdownVisible;
    }

    @action
    changeEditModalVisibility() {
        this.showUpdateErrorMessage = false;
        this.isEditModalVisible = !this.isEditModalVisible;
    }

    @action
    changeSecurityOfOnlinePaymentsModalVisibility() {
        this.showWrongOldPasswordErrorMessage = false;
        this.showDifferentConfirmedPasswordErrorMessage = false;
        this.isSecurityOfOnlinePaymentsModalVisible = !this.isSecurityOfOnlinePaymentsModalVisible;
    }

    @action
    changeChangePasswordModalVisibility() {
        this.isChangePasswordModalVisible = !this.isChangePasswordModalVisible;
    }

    @action
    showLoginPassword() {
        this.isLoginPasswordVisible = !this.isLoginPasswordVisible;
    }
}

const appStore = new AppStore();
export default appStore;