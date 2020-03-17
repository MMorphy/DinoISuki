import React from "react";
import {Col, Form, FormControl, FormGroup, FormLabel} from "react-bootstrap";
import userStore from "../../store/UserStore";
import DatePicker from "react-datepicker";
import {observer} from "mobx-react";

@observer
export default class EditModalFields extends React.Component<{}, {}> {
    render() {
        return (
            <Form className="edit-modal-center">
                <FormGroup controlId="email" className="edit-modal-input-form-size">
                    <Col>
                        <FormLabel><h5 className="font-color font-size">Email adresa</h5></FormLabel>
                    </Col>
                    <Col>
                        <FormControl type="email" onChange={(e: any) => userStore.updateContactInformationUpdateDto(e.target.value, 'email')}/>
                    </Col>
                </FormGroup>

                <FormGroup controlId="telephoneNumber" className="edit-modal-input-form-size">
                    <Col>
                        <FormLabel><h5 className="font-color font-size">Telefonski broj</h5></FormLabel>
                    </Col>
                    <Col>
                        <FormControl type="phone" onChange={(e: any) => userStore.updateContactInformationUpdateDto(e.target.value, 'telephoneNumber')}/>
                    </Col>
                </FormGroup>

                <FormGroup controlId="dateOfBirth" className="edit-modal-input-form-size">
                    <Col>
                        <FormLabel><h5 className="font-color font-size">Datum rođenja</h5></FormLabel>
                    </Col>
                    <Col className="date-picker-input-wrapper">
                        <DatePicker
                            selected={userStore.date}
                            onChange={(date: Date) => userStore.reformatDate(date, 'update')}
                            maxDate={new Date()}
                            peekNextMonth
                            showMonthDropdown
                            showYearDropdown
                            dropdownMode="select"
                            className="date-picker-input"
                        />
                    </Col>
                </FormGroup>
            </Form>
        )
    }
}