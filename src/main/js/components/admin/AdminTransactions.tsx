import React from "react";
import {observer} from "mobx-react";
import { MDBDataTable, MDBBtn } from 'mdbreact';
import adminStore from "../../store/AdminStore";
import {Card} from "react-bootstrap";
import ErrorMessage from "../utils/ErrorMessage";

@observer
export default class AdminTransactions extends React.Component<{}, {}> {
	delete = async () => {
		if(event !== undefined && event.srcElement !== null) {
			let transactionId: number = adminStore.transactionDetailsDTO[this.getId(event)].id;
			let deleted: boolean = await adminStore.deleteTransactionDetails(transactionId);
			if(deleted) {
				await adminStore.getTransactionDetails('', '');	// does not return a Promise (void)
				this.forceUpdate();
			}
		}
  	};

	getId = (event: any) => {
		let _id: any = event.srcElement.id;
		if(_id === undefined || _id === null || _id == "") {
			_id = event.srcElement.parentNode.id;
		}
		return _id;
	}

    render() {
	
		const columns = [
		{
			label: '#',
	        field: 'id',
	        sort: 'asc',
	        width: 10
      	},
      	{
	        label: 'Korisničko ime',
	        field: 'username',
	        sort: 'asc',
	        width: 70
      	},
		{
	        label: 'ID transakcije',
	        field: 'transactionId',
	        sort: 'asc',
	        width: 70
      	},
		{
	        label: 'Vrijeme',
	        field: 'timestamp',
	        sort: 'asc',
	        width: 70
      	},
		{
	        label: 'Status',
	        field: 'transactionStatus',
	        sort: 'asc',
	        width: 20
      	},
		{
	        label: 'Brisanje',
	        field: 'delete',
	        width: 20
      	}
		];
		const rows = [];
		for (var i = 0; i < adminStore.transactionDetailsDTO.length; i++){
			let timestampStr: string = adminStore.transactionDetailsDTO[i].timestamp.toString();
			let timestamp: Date = new Date(timestampStr.substring(0, timestampStr.indexOf('.')) + 'Z');
			rows.push(
				{
					id: adminStore.transactionDetailsDTO[i].id,
					username: adminStore.transactionDetailsDTO[i].username,
					transactionId: adminStore.transactionDetailsDTO[i].transactionId,
					timestamp: timestamp.toLocaleString(),
					transactionStatus: adminStore.transactionDetailsDTO[i].transactionStatus,
					delete: <div className="admin-table-button-div">
								<MDBBtn bs-style="primary" size="sm" className="admin-table-button" id={i} color="red" onClick={() => this.delete()}>
									<i id={i.toString()} className="fas fa-trash"></i>
	                            </MDBBtn>
							</div>
				}
			)
		}
	
		const data = {
		    columns,
		    rows
		};
	
        return (
            <div>
				<Card className="my-profile-card card-style-center">
	                <Card.Header>
	                    <div className="row">
							<h5 className="h5-my-profile-card-title">Korisničke transakcije</h5>
	                    </div>
	                </Card.Header>
	                <Card.Body>
						<div>
						{
		                    !adminStore.transactionDeleteSuccessfull
		                        ? <ErrorMessage errorMessage="Pogreška kod brisanja transakcije" loginButton={false}/>
		                        : <div/>
		                }
						</div>
	                    <div className="row">
							<MDBDataTable className="admin-transactions-MDBDataTable"
								autoWidth
								reponsive={"true"}
								striped
								bordered
								small
								hover
								sortable
								paginationLabel={["Prethodna", "Slijedeća"]}
								infoLabel={["Prikazano", "do", "od", "transakcija"]}
								entriesLabel="Prikaži transakcija"
								searchLabel="Pronađi..."
								noRecordsFoundLabel="Nema podataka"
								tbodyTextWhite
								data={data}
						    />
	                    </div>
	                </Card.Body>
	            </Card>
            </div>
        );
    }

	componentDidMount(): void {
		adminStore.getTransactionDetails("", "");
    }

}

