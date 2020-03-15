import {Accordion, Card} from "react-bootstrap";
import React from "react";

export default class TermsOfSale extends React.Component<{}, {}> {
    render() {
        return (
            <Card border="danger">
                <Accordion.Toggle as={Card.Header} eventKey="2">
                    <h5>Terms of Sale</h5>
                </Accordion.Toggle>
                <Accordion.Collapse eventKey="2">
                    <Card.Body>
                        <Card.Text>
                            *tekst*
                        </Card.Text>
                    </Card.Body>
                </Accordion.Collapse>
            </Card>
        );
    }
}