import React from "react";
import {Card, Accordion} from "react-bootstrap";

export default class ProductServices extends React.Component<{}, {}> {
    render() {
        return (
            <Card border="danger">
                   <Accordion.Toggle as={Card.Header} eventKey="1">
                       <h5>O proizvodima i/ili uslugama</h5>
                   </Accordion.Toggle>
                   <Accordion.Collapse eventKey="1">
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