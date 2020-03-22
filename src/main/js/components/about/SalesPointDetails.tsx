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
                            <div className="row">
                                <div className="column sales-point-details-column-width">
                                    Ime tvrtke:
                                    <br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;Go2Play j.d.o.o.
                                    <br/>
                                    <br/>
                                    OIB:
                                    <br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;14509637799
                                    <br/>
                                    <br/>
                                    Matični broj poslovnog subjekta:
                                    <br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;5201411
                                </div>
                                <div className="column sales-point-details-column-width">
                                    Tehnički kontakt:
                                    <br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;Dino Hadžić - +385 91 619 9912
                                    <br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;Ivan Šušnja - +385 91 160 0010
                                    <br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;Hrgovići 35, 10 000 Zagreb
                                    <br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;dinoh8@outlook.com
                                    <br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;info@ibas-idea.hr
                                    <br/>
                                    <br/>
                                    Računovodstveni kontakt:
                                    <br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;Snježana Frulja - +385 98 817 471
                                    <br/>
                                    &nbsp;&nbsp;&nbsp;&nbsp;Kneza Ljudevita Posavskog 36b, 10 000 Zagreb
                                </div>
                            </div>
                    </Card.Body>
                </Accordion.Collapse>
            </Card>
        );
    }
}