import * as React from "react";
import {observer} from "mobx-react";
import {Button, Navbar} from "react-bootstrap";
import appStore from "../../store/AppStore";
// @ts-ignore
import image from "../../../resources/images/logo.png";
import userStore from "../../store/UserStore";

@observer
export default class CustomNavbar extends React.Component<{}, {}> {
    render() {
        return (
            <Navbar className="navbar-background-color" fixed={"top"}>
                <Navbar.Collapse>
                    <div className="row navbar-height">
                        <div className="column sidebar-left">
                            <div className="button-padding">
                                <Button bs-style="primary" size="lg" className="button-color" onClick={() => appStore.changeSidebarVisibility()}>
									<i className="fas fa-bars"></i>
									{
					                    userStore.hasUnreadMessages
					                        ? <i className="fas fa-circle sidebar-notification-dot"></i>
											: <div/>
					                }
                                </Button>
                            </div>
                        </div>
                        <div className="column right">
                            <img className="logo-margin" src={image} alt="Shows"/>
                        </div>
                    </div>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}