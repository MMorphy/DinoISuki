import * as React from "react";
import {Button, Col, Form, FormControl, FormGroup, FormLabel} from "react-bootstrap";
import {Link} from "react-router-dom";
import {observer} from "mobx-react";
import userStore from "../../store/UserStore";
import {History, LocationState} from "history";
import appStore from "../../store/AppStore";
import ErrorMessage from "../utils/ErrorMessage";
import {action} from "mobx";

interface LoginProps {
    history: History<LocationState>;
}

@observer
export default class Login extends React.Component<LoginProps, {}>  {
    render() {
        document.title = "Login";

        return (
            <div>
                <Form className="login-form-width">
                    <FormGroup controlId="formHorizontalEmail">
                        <Col>
                            <FormLabel><h5 className="font-color font-size">Username</h5></FormLabel>
                        </Col>
                        <Col>
                            <FormControl defaultValue={localStorage.getItem('username') ? localStorage.getItem('username') as string : ""}
                                         type="username"
                                         placeholder="Username"
                                         onChange={(e: any) => userStore.updateUserLoginDto(e.target.value, "username")}/>
                        </Col>
                    </FormGroup>

                    <FormGroup controlId="formHorizontalPassword">
                        <Col>
                            <FormLabel><h5 className="font-color font-size">Password</h5></FormLabel>
                        </Col>
                        <Col className="login-password-input-form">
                            <FormControl defaultValue={localStorage.getItem('password') ? localStorage.getItem('password') as string : ""}
                                         type={appStore.isLoginPasswordVisible ? "text" : "password"}
                                         placeholder="Password"
                                         onChange={(e: any) => userStore.updateUserLoginDto(e.target.value, "password")}
                                         className="login-password-input-form-border"/>
                            <Button id="pass-status" className="fa fa-eye show-password-button" aria-hidden="true" onClick={() => appStore.showLoginPassword()}/>
                        </Col>
                    </FormGroup>
                    <Col>
                    {
                        appStore.unsuccessfulLogin
                            ? <ErrorMessage errorMessage={"You haven't successfully logged in! Try again."} loginButton={false}/>
                            : <div/>
                    }
                    </Col>
                    <FormGroup>
                        <Col className="login-registration-button-center">
                            <Button type="submit" className="login-registration-button-color" onClick={(e: any) => this.login(e)}><b>Sign in</b></Button>
                        </Col>
                        <p className="register-text-padding font-color">Not registered yet? <Link to="/register"><b>Register</b></Link> now</p>
                    </FormGroup>
                </Form>
            </div>
        );
    }

    @action
    componentWillUnmount(): void {
        appStore.unsuccessfulLogin = false;
    }

    private login(e: any) {
        e.preventDefault();
        userStore.login()
            .then(() => {
                this.props.history.push("/");
            })
            .catch(action(() => {
                appStore.unsuccessfulLogin = true
                })
            );
    }
}