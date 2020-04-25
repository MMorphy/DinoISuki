import React from "react";
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import 'react-tabs/style/react-tabs.css';
import AdminStatistics from "./AdminStatistics";
import AdminTransactions from "./AdminTransactions";
import AdminSubscriptions from "./AdminSubscriptions";
import AdminNotifications from "./AdminNotifications";
import AdminQuiz from "./AdminQuiz";
import {observer} from "mobx-react";
import {History, LocationState} from "history";

interface AdminHomeProps {
    history: History<LocationState>;
}

@observer
export default class AdminHome extends React.Component<AdminHomeProps, {}> {
    render() {
		document.title = "Postavke";
        return (
            <div className="about-go2play-margin">
				<Tabs className="reactTabs">
					<TabList style={{paddingTop: '20px'}}>
						<Tab>Statistike</Tab>
						<Tab>Pretplate</Tab>
						<Tab>Transakcije</Tab>
						<Tab>Poruke</Tab>
						<Tab>Kvizovi</Tab>
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
					<TabPanel>
						<AdminQuiz/>
				    </TabPanel>
				</Tabs>
				<br/>
            </div>
        );
    }
	
	componentDidMount(): void {
        if (!sessionStorage.getItem('username')) {
            this.props.history.push("/login");
        }
    }
}