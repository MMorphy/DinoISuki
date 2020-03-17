import {observable} from "mobx";

export default class RoleDTO {

    @observable id : string = "";
    @observable name: string = "";
}