import {observable} from "mobx";

export default class UserDTO {

     @observable username: string = "";
     @observable password: string = "";
     @observable dateOfBirth: string = "";
     @observable createdAt: string = "";
     @observable enabled: boolean = true; //za sad
}