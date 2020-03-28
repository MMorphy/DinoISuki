import React from "react";
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import 'react-tabs/style/react-tabs.css';
import AdminStatistics from "./AdminStatistics";
import AdminTransactions from "./AdminTransactions";
import AdminSubscriptions from "./AdminSubscriptions";
import AdminNotifications from "./AdminNotifications";
import adminStore from "../../store/AdminStore";
import {observer} from "mobx-react";

@observer
export default class AdminHome extends React.Component<{}, {}> {
    render() {
        return (
            <div className="about-go2play-margin">
				<Tabs className="reactTabs">
					<TabList style={{paddingTop: '20px'}}>
						<Tab>Statistike</Tab>
						<Tab>Pretplate</Tab>
						<Tab>Transakcije</Tab>
						<Tab>Poruke</Tab>
				    </TabList>
				
				    <TabPanel>
						<AdminStatistics/>
				    </TabPanel>
				    <TabPanel>
						<AdminSubscriptions/>
				    </TabPanel>
					<TabPanel>
						<AdminTransactions/>
				    </TabPanel>
					<TabPanel>
						<AdminNotifications/>
				    </TabPanel>
				</Tabs>
				<br/>
            </div>
        );
    }

	componentDidMount(): void {
        adminStore.getAdminStatistics();
		adminStore.getTransactionDetails("", "");
		// adminStore.getAllSubscriptions(false);
    }
}