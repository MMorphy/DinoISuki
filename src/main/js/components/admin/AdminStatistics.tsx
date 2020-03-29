import React from "react";
import {Card} from "react-bootstrap";
import adminStore from "../../store/AdminStore";
import {observer} from "mobx-react";

@observer
export default class AdminStatistics extends React.Component<{}, {}> {
    render() {
		let subscriptionHeaderRows = [];
		let subscriptionHeaderCell = [];
		subscriptionHeaderCell.push(<td key="subscriptionHeaderCell1" className="admin-statistics-table-header-cell">Razdoblje</td>);
		subscriptionHeaderCell.push(<td key="subscriptionHeaderCell2" className="admin-statistics-table-header-cell">Tip pretplate</td>);
		subscriptionHeaderCell.push(<td key="subscriptionHeaderCell3" className="admin-statistics-table-header-cell">Sumarno naplaćeno (kn)</td>);
		subscriptionHeaderRows.push(<tr key='subscriptionHeaderRow' id="header_row">{subscriptionHeaderCell}</tr>);
		let subscriptionRows = [];
		for (var i = 0; i < adminStore.adminStatisticsDTO.subscriptionStatistics.length; i++){
			let rowID = `row${i}`;
			let subscriptionCell = [];
			subscriptionCell.push(<td key="subscriptionCell1${i}" id="cell1" className="admin-statistics-table-body-cell">{adminStore.adminStatisticsDTO.subscriptionStatistics[i].yyyymm}</td>);
			subscriptionCell.push(<td key="subscriptionCell2${i}" id="cell2" className="admin-statistics-table-body-cell">{adminStore.adminStatisticsDTO.subscriptionStatistics[i].subscriptionType}</td>);
			subscriptionCell.push(<td key="subscriptionCell3${i}" id="cell3" className="admin-statistics-table-body-cell">{adminStore.adminStatisticsDTO.subscriptionStatistics[i].sumPerType}</td>);
			subscriptionRows.push(<tr key={rowID} id={rowID} style={{borderBlockStyle: 'solid'}}>{subscriptionCell}</tr>);
	    }

		let diskSpaceHeaderRows = [];
		let diskSpaceHeaderCell = [];
		diskSpaceHeaderCell.push(<td key="diskSpaceHeaderCell1" id="header_cell1" className="admin-statistics-table-header-cell">Particija</td>);
		diskSpaceHeaderCell.push(<td key="diskSpaceHeaderCell2" id="header_cell2" className="admin-statistics-table-header-cell">Slobodnog prostora</td>);
		diskSpaceHeaderCell.push(<td key="diskSpaceHeaderCell3" id="header_cell3" className="admin-statistics-table-header-cell">Zauzeto</td>);
		diskSpaceHeaderCell.push(<td key="diskSpaceHeaderCell4" id="header_cell3" className="admin-statistics-table-header-cell">Ukupno</td>);
		diskSpaceHeaderRows.push(<tr key="diskSpaceHeaderRow" id="header_row">{diskSpaceHeaderCell}</tr>);
		let diskSpaceRows = [];
		for (var i = 0; i < adminStore.adminStatisticsDTO.diskSpaceInfo.length; i++){
			let rowID = `row${i}`;
			let diskSpaceCell = [];
			diskSpaceCell.push(<td key="diskSpaceCell1${i}" id="cell1" className="admin-statistics-table-body-cell">{adminStore.adminStatisticsDTO.diskSpaceInfo[i].partition}</td>);
			diskSpaceCell.push(<td key="diskSpaceCell2${i}" id="cell2" className="admin-statistics-table-body-cell">{adminStore.adminStatisticsDTO.diskSpaceInfo[i].available}</td>);
			diskSpaceCell.push(<td key="diskSpaceCell3${i}" id="cell3" className="admin-statistics-table-body-cell">{adminStore.adminStatisticsDTO.diskSpaceInfo[i].used}</td>);
			diskSpaceCell.push(<td key="diskSpaceCell4${i}" id="cell3" className="admin-statistics-table-body-cell">{adminStore.adminStatisticsDTO.diskSpaceInfo[i].total}</td>);
			diskSpaceRows.push(<tr key={rowID} id={rowID} style={{borderBlockStyle: 'solid'}}>{diskSpaceCell}</tr>);
	    }

        return (
            <div>
				<Card className="my-profile-card">
	                <Card.Header>
	                    <div className="row">
							<h5 className="h5-my-profile-card-title">Generalne statistike</h5>
	                    </div>
	                </Card.Header>
	                <Card.Body>
	                    <div className="row">
	                        <div className="column my-profile-card-first-column">
	                            <div className="font-margin">
	                                <b className="font-color font-size">Registrirani korisnici:</b>&nbsp;
	                                <b className="secondary-font-color font-size">{adminStore.adminStatisticsDTO.numberOfUsers}</b>
	                            </div>
								<div className="font-margin">
	                                <b className="font-color font-size">Broj lokacija:</b>&nbsp;
	                                <b className="secondary-font-color font-size">{adminStore.adminStatisticsDTO.numberOfLocations}</b>
	                            </div>
								<div className="font-margin">
	                                <b className="font-color font-size">Broj terena:</b>&nbsp;
	                                <b className="secondary-font-color font-size">{adminStore.adminStatisticsDTO.numberOfFields}</b>
	                            </div>
								<div className="font-margin">
	                                <b className="font-color font-size">Broj kamera:</b>&nbsp;
	                                <b className="secondary-font-color font-size">{adminStore.adminStatisticsDTO.numberOfCameras}</b>
	                            </div>
								<div className="font-margin">
	                                <b className="font-color font-size">Broj kamera:</b>&nbsp;
	                                <b className="secondary-font-color font-size">{adminStore.adminStatisticsDTO.numberOfCameras}</b>
	                            </div>
								<div className="font-margin">
	                                <b className="font-color font-size">Broj aktivnih snimki:</b>&nbsp;
	                                <b className="secondary-font-color font-size">{adminStore.adminStatisticsDTO.numberOfActiveVideos}</b>
	                            </div>
	                        </div>
	                    </div>
	                </Card.Body>
					<Card.Header>
	                    <div className="row">
							<h5 className="h5-my-profile-card-title">Statistike o pretplatama</h5>
	                    </div>
	                </Card.Header>
	                <Card.Body>
	                    <div className="row">
	                        <div className="column my-profile-card-first-column">
	                            <div className="font-margin">
	                                <table>
									<thead>
									{subscriptionHeaderRows}
									</thead>
									<tbody>
									{subscriptionRows}
									</tbody>
									</table>
	                            </div>
								
	                        </div>
	                    </div>
	                </Card.Body>
					<Card.Header>
	                    <div className="row">
							<h5 className="h5-my-profile-card-title">Stanje na serveru</h5>
	                    </div>
	                </Card.Header>
	                <Card.Body>
	                    <div className="row">
	                        <div className="column my-profile-card-first-column">
	                            <div className="font-margin">
	                                <table>
									<thead>
									{diskSpaceHeaderRows}
									</thead>
									<tbody>
									{diskSpaceRows}
									</tbody>
									</table>
	                            </div>
								
	                        </div>
	                    </div>
	                </Card.Body>
	            </Card>
				
            </div>
        );
    }

	componentDidMount(): void {
        adminStore.getAdminStatistics();
    }
}