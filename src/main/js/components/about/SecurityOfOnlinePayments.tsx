import {Accordion, Card} from "react-bootstrap";
import React from "react";
import SecurityOfOnlinePaymentsText from "../utils/SecurityOfOnlinePaymentsText";

export default class SecurityOfOnlinePayments extends React.Component<{}, {}> {
    render() {
        return (
            <Card border="danger">
                <Accordion.Toggle as={Card.Header} eventKey="3">
                    <h5>Security of Online Payments</h5>
                </Accordion.Toggle>
                <Accordion.Collapse eventKey="3">
                    <Card.Body>
                        <SecurityOfOnlinePaymentsText/>
                    </Card.Body>
                </Accordion.Collapse>
            </Card>
        );
    }
}