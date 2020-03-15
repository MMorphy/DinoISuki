import React from "react";
import {Col, Form, FormControl, FormGroup, FormLabel} from "react-bootstrap";
import userStore from "../../store/UserStore";

export default class EditModalFields extends React.Component<{}, {}> {
    render() {
        return (
            <Form className="edit-modal-center">
                <FormGroup controlId="email">
                    <Col sm={{span: 12}}>
                        <FormLabel><h5 className="font-color font-size">Email</h5></FormLabel>
                    </Col>
                    <Col sm={{span: 12}}>
                        <FormControl type="email" placeholder="Email" onChange={(e: any) => userStore.updateContactInformationUpdateDto(e.target.value, 'email')}/>
                    </Col>
                </FormGroup>

                <FormGroup controlId="telephoneNumber">
                    <Col sm={{span: 12}}>
                        <FormLabel><h5 className="font-color font-size">Phone Number</h5></FormLabel>
                    </Col>
                    <Col sm={{span: 12}}>
                        <FormControl type="phone" placeholder="Phone Number" onChange={(e: any) => userStore.updateContactInformationUpdateDto(e.target.value, 'telephoneNumber')}/>
                    </Col>
                </FormGroup>

                <FormGroup controlId="dateOfBirth">
                    <Col sm={{span: 12}}>
                        <FormLabel><h5 className="font-color font-size">Date of Birth</h5></FormLabel>
                    </Col>
                    <Col sm={{span: 12}}>
                        <FormControl type="date" placeholder="Date of Birth" max="2020-12-31" onChange={(e: any) => userStore.updateUserUpdateDto(e.target.value, "dateOfBirth")}/>
                    </Col>
                </FormGroup>
            </Form>
        )
    }
}