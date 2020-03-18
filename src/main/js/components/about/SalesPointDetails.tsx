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
                            Go2Play j.d.o.o.
                            <br/>
                            <br/>
                            Adresa: Hrgovici 35, Kneza Ljudevita Posavskog 36b, 10 000 Zagreb
                            <br/>
                            <br/>
                            OIB: 14509637799
                            <br/>
                            <br/>
                            Porezni broj: *9999*
                            <br/>
                            <br/>
                            Tehnički kontakt:
                            Dino *prezime* - +385 91 619 9912, Ivan Šušnja - +385 91 160 0010
                            <br/>
                            <br/>
                            Računovodstveni kontakt: Snježana Frulja +385 98 817 471
                            <br/>
                            <br/>
                            Email adresa: dinoh8@outlook.com, info@ibas-idea.hr
                            {/*Maticni br posl.sub.- 5201411*/}
                        </Card.Text>
                    </Card.Body>
                </Accordion.Collapse>
            </Card>
        );
    }
}