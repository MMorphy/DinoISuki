import * as React from "react";
import {Button, Col, Form, FormControl, FormGroup, FormLabel} from "react-bootstrap";
import {observer} from "mobx-react";
import userStore from "../../store/UserStore";
import {History, LocationState} from "history";
import appStore from "../../store/AppStore";
import ErrorMessage from "../utils/ErrorMessage";
import {action} from "mobx";

interface RegistrationProps {
    history: History<LocationState>;
}

@observer
export default class Registration extends React.Component<RegistrationProps, {}> {
    render() {
        document.title = "Registration";

        return (
            <div>
                <Form className="register-form-width">
                    <FormGroup controlId="username">
                        <Col sm={{span: 11}}>
                            <FormLabel><h5 className="font-color font-size">Username</h5></FormLabel>
                        </Col>
                        <Col sm={{span: 11}}>
                            <FormControl type="username" placeholder="Username" onChange={(e: any) => userStore.updateUserRegistrationDto(e.target.value, "username")}/>
                        </Col>
                    </FormGroup>

                    <FormGroup controlId="password">
                        <Col sm={{span: 11}}>
                            <FormLabel><h5 className="font-color font-size">Password</h5></FormLabel>
                        </Col>
                        <Col sm={{span: 11}}>
                            <FormControl type="password" placeholder="Password" onChange={(e: any) => userStore.changePasswordForRegistration(e.target.value)}/>
                        </Col>
                    </FormGroup>

                    <FormGroup controlId="confirmedPassword">
                        <Col sm={{span: 11}}>
                            <FormLabel><h5 className="font-color font-size">Confirm Password</h5></FormLabel>
                        </Col>
                        <Col sm={{span: 11}}>
                            <FormControl type="password" placeholder="Password" onChange={(e: any) => userStore.changeConfirmedPasswordForRegistration(e.target.value)}/>
                        </Col>
                    </FormGroup>

                    <FormGroup controlId="email">
                        <Col sm={{span: 11}}>
                            <FormLabel><h5 className="font-color font-size">Email</h5></FormLabel>
                        </Col>
                        <Col sm={{span: 11}}>
                            <FormControl type="email" placeholder="Email" onChange={(e: any) => userStore.updateContactInformationRegistrationDto(e.target.value, 'email')}/>
                        </Col>
                    </FormGroup>

                    <FormGroup controlId="dateOfBirth">
                        <Col sm={{span: 11}}>
                            <FormLabel><h5 className="font-color font-size">Date of Birth</h5></FormLabel>
                        </Col>
                        <Col sm={{span: 11}}>
                            <FormControl type="date" placeholder="Date of Birth" max="2020-12-31" onChange={(e: any) => userStore.updateUserRegistrationDto(e.target.value, "dateOfBirth")}/>
                        </Col>
                    </FormGroup>
                    <Col sm={{span: 11}}>
                    {
                        appStore.showDifferentPassAtRegistrationErrorMessage
                            ? <ErrorMessage errorMessage="Passwords don't match!" loginButton={false}/>
                            : <div/>
                    }
                    {
                        appStore.successfulRegistration
                            ? <ErrorMessage errorMessage={"You have successfully registered! "} loginButton={true}/>
                            : <div/>
                    }
                    {
                        appStore.unsuccessfulRegistration
                            ? <ErrorMessage errorMessage="You haven't successfully registered! Try again." loginButton={false}/>
                            : <div/>
                    }
                    </Col>
                    <FormGroup>
                        <Col sm={{span: 11}} className="login-registration-button-center">
                            <Button type="submit" className="login-registration-button-color" onClick={(e: any) => this.submitRegistration(e)}><b>Register</b></Button>
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