import * as React from "react";
import {Button, Col, Form, FormControl, FormGroup, FormLabel} from "react-bootstrap";
import {observer} from "mobx-react";
import userStore from "../../store/UserStore";
import {History, LocationState} from "history";
import appStore from "../../store/AppStore";
import ErrorMessage from "../utils/ErrorMessage";
import {action} from "mobx";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

interface RegistrationProps {
    history: History<LocationState>;
}

@observer
export default class Registration extends React.Component<RegistrationProps, {}> {
    render() {
        document.title = "Registracija";

        return (
            <div>
                <Form className="register-form-width">
                    <FormGroup controlId="username">
                        <Col>
                            <FormLabel><h5 className="font-color font-size">Korisničko ime</h5></FormLabel>
                        </Col>
                        <Col>
                            <FormControl type="username" onChange={(e: any) => userStore.updateUserRegistrationDto(e.target.value, "username")}/>
                        </Col>
                    </FormGroup>

                    <FormGroup controlId="password">
                        <Col>
                            <FormLabel><h5 className="font-color font-size">Lozinka</h5></FormLabel>
                        </Col>
                        <Col>
                            <FormControl type="password" onChange={(e: any) => userStore.changePasswordForRegistration(e.target.value)}/>
                        </Col>
                    </FormGroup>

                    <FormGroup controlId="confirmedPassword">
                        <Col>
                            <FormLabel><h5 className="font-color font-size">Potvrdi lozinku</h5></FormLabel>
                        </Col>
                        <Col>
                            <FormControl type="password" onChange={(e: any) => userStore.changeConfirmedPasswordForRegistration(e.target.value)}/>
                        </Col>
                    </FormGroup>

                    <FormGroup controlId="email">
                        <Col>
                            <FormLabel><h5 className="font-color font-size">Email adresa</h5></FormLabel>
                        </Col>
                        <Col>
                            <FormControl type="email" onChange={(e: any) => userStore.updateContactInformationRegistrationDto(e.target.value, 'email')}/>
                        </Col>
                    </FormGroup>

                    <FormGroup controlId="dateOfBirth">
                        <Col>
                            <FormLabel><h5 className="font-color font-size">Datum rođenja</h5></FormLabel>
                        </Col>
                        <Col className="date-picker-input-wrapper">
                            <DatePicker
                                selected={userStore.date}
                                onChange={(date: Date) => userStore.reformatDate(date)}
                                maxDate={new Date()}
                                peekNextMonth
                                showMonthDropdown
                                showYearDropdown
                                dropdownMode="select"
                                className="date-picker-input"
                            />
                        </Col>
                    </FormGroup>
                    <Col>
                    {
                        appStore.showDifferentPassAtRegistrationErrorMessage
                            ? <ErrorMessage errorMessage="Lozinke ne odgovaraju!" loginButton={false}/>
                            : <div/>
                    }
                    {
                        appStore.successfulRegistration
                            ? <ErrorMessage errorMessage={"Uspješna registracija! "} loginButton={true}/>
                            : <div/>
                    }
                    {
                        appStore.unsuccessfulRegistration
                            ? <ErrorMessage errorMessage="Neuspješna registracija! Pokušaj ponovo." loginButton={false}/>
                            : <div/>
                    }
                    </Col>
                    <FormGroup>
                        <Col className="login-registration-button-center">
                            <Button type="submit" className="login-registration-button-color" onClick={(e: any) => this.submitRegistration(e)}><b>Registriraj se</b></Button>
                        </Col>
                    </FormGroup>
                </Form>
            </div>
        );
    }

    @action
    componentWillUnmount(): void {
        appStore.unsuccessfulRegistration = false;
        appStore.successfulRegistration = false;
        appStore.showDifferentPassAtRegistrationErrorMessage = false;
        userStore.resetDate();
    }

    private submitRegistration(e: any) {
        e.preventDefault();
        userStore.submitRegistration()
            .catch(action(() => {
                appStore.unsuccessfulRegistration = true;
                appStore.successfulRegistration = false;
            }));
    }
}