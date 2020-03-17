import React from "react";
import {Accordion} from "react-bootstrap";

export default class AdminHome extends React.Component<{}, {}> {
    render() {
        return (
            <div className="about-go2play-margin">
				<h2>Hello Admin world!</h2>
                <Accordion defaultActiveKey="0" bsPrefix="accordion accordion-size">
                </Accordion>
                <br/>
            </div>
        );
    }
}