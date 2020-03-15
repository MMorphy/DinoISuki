import React from "react";
import {Link} from "react-router-dom";

interface ErrorMessageProps {
    errorMessage: string;
    loginButton: boolean;
}

export default class ErrorMessage extends React.Component<ErrorMessageProps, {}> {
    render() {
        return (
            <div className="updateErrorMessage">
                <b>{this.props.errorMessage}</b>
                {
                    this.props.loginButton
                        ? <Link to={"/login"}>Login here</Link>
                        : <div/>
                }
                <br/>
            </div>
        );
    }
}