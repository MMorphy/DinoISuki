import React from "react";
import {Accordion, Card} from "react-bootstrap";

export default class SalesPointDetails extends React.Component<{}, {}> {
    render() {
        document.title = "O aplikaciji";

        return (
            <Card border="danger">
                <Accordion.Toggle as={Card.Header} eventKey="0">
                    <h5>Podaci o prodajnom mjestu</h5>
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
                            Porezni broj: *9999*
                            <br/>
                            Broj telefona: *9999999999*
                            <br/>
                            Email adresa: *bla@email.hr*
                        </Card.Text>
                    </Card.Body>
                </Accordion.Collapse>
            </Card>
        );
    }
}