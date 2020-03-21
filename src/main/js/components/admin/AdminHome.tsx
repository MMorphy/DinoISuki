import React from "react";
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import 'react-tabs/style/react-tabs.css';
import AdminStatistics from "./AdminStatistics";
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
						<Tab disabled>Pretplate</Tab>
						<Tab disabled>Transakcije</Tab>
				    </TabList>
				
				    <TabPanel>
						<AdminStatistics/>
				    </TabPanel>
				    <TabPanel>
						<h2>Admin pretplate</h2>
				    </TabPanel>
					<TabPanel>
						<h2>Admin transakcije</h2>
				    </TabPanel>
				</Tabs>
				<br/>
            </div>
        );
    }

	componentDidMount(): void {
        adminStore.getAdminStatistics();
    }
}