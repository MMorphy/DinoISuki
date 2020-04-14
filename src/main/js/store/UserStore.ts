import {action, observable} from "mobx";
import UserDTO from "../model/UserDTO";
import ContactInformationDTO from "../model/ContactInformationDTO";
import RoleDTO from "../model/RoleDTO";
import userRepository from "../repository/UserRepository";
import {AxiosResponse} from "axios";
import appStore from "./AppStore";
import _ from 'lodash';

class UserStore {
    @observable userProfileDto: UserDTO = new UserDTO();
    @observable contactInformationProfileDto: ContactInformationDTO = new ContactInformationDTO();

    @observable userRegistrationDto: UserDTO = new UserDTO();
    @observable contactInformationRegistrationDto: ContactInformationDTO = new ContactInformationDTO();

    @observable userLoginDto: UserDTO = new UserDTO();

    @observable userUpdateDto: UserDTO = new UserDTO();
    @observable contactInformationUpdateDto: ContactInformationDTO = new ContactInformationDTO();

    @observable userProfilePhoto: string = "";

    @observable oldPasswordForUpdate: string = "";
    @observable newPasswordForUpdate: string = "";
    @observable confirmedPasswordForUpdate: string = "";

    @observable passwordForRegistration: string = "";
    @observable confirmedPasswordForRegistration: string = "";

    @observable date: Date | undefined = undefined;

    //update nakon unosa
    @action
    updateUserRegistrationDto(value: string, key: string) {
        // @ts-ignore
        this.userRegistrationDto[key] = value;
    }

    @action
    updateContactInformationRegistrationDto(value: string, key: string) {
        // @ts-ignore
        this.contactInformationRegistrationDto[key] = value;
    }

    @action
    updateUserLoginDto(value: string, key: string) {
        // @ts-ignore
        this.userLoginDto[key] = value;
    }

    @action
    updateUserUpdateDto(value: string, key: string) {
        // @ts-ignore
        this.userUpdateDto[key] = value;
    }

    @action
    updateContactInformationUpdateDto(value: string, key: string) {
        // @ts-ignore
        this.contactInformationUpdateDto[key] = value;
    }

    // repository
    @action
    submitRegistration(): Promise<any> {
        if (this.passwordForRegistration !== this.confirmedPasswordForRegistration) {
            appStore.showDifferentPassAtRegistrationErrorMessage = true;

            return new Promise((resolve, reject) => {
                reject();
            });
        }
        this.updateUserRegistrationDto(this.passwordForRegistration, "password");
        this.updateUserRegistrationDto(this.getCurrentDateTime(), "createdAt");
        this.updateContactInformationRegistrationDto(this.userRegistrationDto.username, "username");

        return userRepository.submitRegistration(this.userRegistrationDto)
            .then(() => {
                userRepository.saveContactInfo(this.contactInformationRegistrationDto)
                    .then(action(() => {
                        appStore.successfulRegistration = true;
                        appStore.unsuccessfulRegistration = false;
                        appStore.showDifferentPassAtRegistrationErrorMessage =  false;
                    }))
                    .catch(action(() => {
                        userRepository.deleteUser(this.userRegistrationDto); //if whole registration is not successful, delete userDto
                        appStore.unsuccessfulRegistration = true;
                        appStore.successfulRegistration = false;
                    }))
            });
    }

    login() {
         return userRepository.login(this.userLoginDto)
            .then(action((response: AxiosResponse) => {
                this.addUserToSessionStorage(response.data[1].token, response.data[0].username);
				this.getUserRoles();
            }))
    }

    fetchUserProfile() {
        userRepository.fetchUser(sessionStorage.getItem('username')!, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
                this.addFetchedUserToUserDto(response);
            }));

        userRepository.fetchUserContactInfo(sessionStorage.getItem('username')!, sessionStorage.getItem('token')!)
            .then((response: AxiosResponse) => {
                this.addFetchedUserContactInfoToContactInfoDto(response)
            });
    }

    fetchUserProfilePhoto() {
        userRepository.fetchUserProfilePhoto(sessionStorage.getItem('username')!, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
                this.userProfilePhoto = btoa(new Uint8Array(response.data).reduce(
                    (data, byte) => data + String.fromCharCode(byte),
                    '',
                ),);
        }));
    }

	getUserRoles() {
		userRepository.getUserRoles(sessionStorage.getItem('username')!, sessionStorage.getItem('token')!)
            .then(action((response: AxiosResponse) => {
				sessionStorage.removeItem('isAdmin');
                response.data.forEach((role: RoleDTO) => {
		            if(role.name !== undefined && role.name.includes('admin')) {
						sessionStorage.setItem('isAdmin', 'true');
						return;
					}
		        });
        }));
	}
	
    @action
    submitUserUpdate() {
        this.updateContactInformationUpdateDto(this.userUpdateDto.username, "username");

        userRepository.submitUserUpdate(this.userUpdateDto, sessionStorage.getItem('token')!)
            .then(() => {
                userRepository.saveContactInfo(this.contactInformationUpdateDto)
                    .then(action(() => {
                        this.addUserUpdateInformationToUserProfile();
                        appStore.showUpdateErrorMessage = false;
                        appStore.showSuccessfulUpdateMessage = true;
                    }))
                    .catch(action(() => {
                        appStore.showSuccessfulUpdateMessage = false;
                        appStore.showUpdateErrorMessage = true;
                    }))
            })
            .catch(action(() => {
                appStore.showSuccessfulUpdateMessage = false;
                appStore.showUpdateErrorMessage = true;
            }));
    }

    uploadProfilePhoto(imageFile: File) {
        userRepository.uploadProfilePhoto(imageFile, sessionStorage.getItem('username')!, sessionStorage.getItem('token')!)
            .then(() => {
                this.fetchUserProfilePhoto();
            });
    }

    @action
    updatePassword() {
        if (this.newPasswordForUpdate !== this.confirmedPasswordForUpdate) {
            appStore.showSuccessfulPasswordUpdateMessage = false;
            appStore.showUnsuccessfulPasswordUpdateMessage = false;
            appStore.showDifferentConfirmedPasswordErrorMessage = true;
            return;
        }

        const passwordDto = {
            username: sessionStorage.getItem('username')!,
            oldPassword: this.oldPasswordForUpdate,
            newPassword: this.newPasswordForUpdate
        };

        userRepository.updatePassword(passwordDto, sessionStorage.getItem('token')!)
            .then(action(() => {
                appStore.showUnsuccessfulPasswordUpdateMessage = false;
                appStore.showDifferentConfirmedPasswordErrorMessage = false;
                appStore.showSuccessfulPasswordUpdateMessage = true;
            }))
            .catch(action(() => {
                appStore.showSuccessfulPasswordUpdateMessage = false;
                appStore.showDifferentConfirmedPasswordErrorMessage = false;
                appStore.showUnsuccessfulPasswordUpdateMessage = true;
            }));
    }


    // util funkcije
    @action
    private addUserUpdateInformationToUserProfile() {
        _.assign(this.userProfileDto, this.userUpdateDto);
        _.assign(this.contactInformationProfileDto, this.contactInformationUpdateDto);
    }

    private getCurrentDateTime() {
        const today = new Date();
        const date = today.getFullYear() + '-' + (today.getMonth()+1) + '-' + today.getDate();
        const time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();

        return (date + 'T' + time + '.000Z');
    }

    private addUserToSessionStorage(token: string, username: string) {
        sessionStorage.setItem('token', token);
        sessionStorage.setItem('username', username);
    }

    clearSessionStorage() {
        sessionStorage.removeItem('token');
        sessionStorage.removeItem('username');
		location.href = '/#/login';
    }

    @action
    private addFetchedUserToUserDto(response: AxiosResponse) {
        _.assign(this.userProfileDto, _.pick(response.data, ["username", "dateOfBirth", "createdAt"]));
        _.assign(this.userUpdateDto, _.pick(response.data, ["username", "dateOfBirth", "createdAt"]));
    }

    @action
    private addFetchedUserContactInfoToContactInfoDto(response: AxiosResponse) {
        _.assign(this.contactInformationProfileDto, _.pick(response.data, ["username", "telephoneNumber", "email"]));
        _.assign(this.contactInformationUpdateDto, _.pick(response.data, ["username", "telephoneNumber", "email"]));
    }

    @action
    changeOldPasswordForUpdate(value: string) {
        this.oldPasswordForUpdate = value;
    }

    @action
    changeNewPasswordForUpdate(value: string) {
        this.newPasswordForUpdate = value;
    }

    @action
    changeConfirmedPasswordForUpdate(value: string) {
        this.confirmedPasswordForUpdate = value;
    }

    @action
    changePasswordForRegistration(value: string) {
        this.passwordForRegistration = value;
    }

    @action
    changeConfirmedPasswordForRegistration(value: string) {
        this.confirmedPasswordForRegistration = value;
    }

    @action
    reformatDate(date: Date, key: string = 'register') {
        this.date = date;
        (key === 'register')
            ? this.updateUserRegistrationDto(date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate(), 'dateOfBirth')
            : this.updateUserUpdateDto(date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate(), 'dateOfBirth')
    }

    @action
    resetDate() {
        this.date = undefined;
    }

}

const userStore = new UserStore();

export default userStore;