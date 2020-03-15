import {observable} from "mobx";

export default class ContactInformationDTO {

    @observable username: string = "";
    @observable telephoneNumber: string = "";
    @observable email: string = "";
}