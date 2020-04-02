import React from "react";
import {observer} from "mobx-react";


@observer
export default class UserNotifications extends React.Component<{}, {}> {
    render() {
		document.title = "Korisničke poruke";
        return (
            <div className="about-go2play-margin">
				<p>Korisničke poruke</p>
				<br/>
            </div>
        );
    }
	
}