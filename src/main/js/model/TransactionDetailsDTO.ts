import {observable} from "mobx";

const DaysEnum = Object.freeze({"SUCCESSFUL":1, "FAILED":2});

export default class TransactionDetailsDTO {
	
	@observable id: number = 0;
	@observable username: string = "";
	@observable transactionId: string = "";
	@observable timestamp: Date = new Date();
	@observable transactionStatus = DaysEnum.SUCCESSFUL;
	
}

