import React from "react";
import {Accordion, Card} from "react-bootstrap";

export default class SalesPointDetails extends React.Component<{}, {}> {
    render() {
        return (
            <Card border="danger">
                <Accordion.Toggle as={Card.Header} eventKey="0">
                    <h5>Sales Point Details</h5>
                </Accordion.Toggle>
                <Accordion.Collapse eventKey="0">
                    <Card.Body>
                        <Card.Text>
                            *Ime tvrtke/obrta*
                            <br/>
                            *Adresa*
                            <br/>
                            OIB: *99999999999999999*
                            <br/>
                            Tax number: *9999*
                            <br/>
                            Phone Number: *9999999999*
                            <br/>
                            E-mail: *bla@email.hr*
                        </Card.Text>
                    </Card.Body>
                </Accordion.Collapse>
            </Card>
        );
    }
}