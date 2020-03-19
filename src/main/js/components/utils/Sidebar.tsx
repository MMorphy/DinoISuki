import appStore from "../../store/AppStore";
import {Modal} from "react-bootstrap";
import * as React from "react";
import {observer} from "mobx-react";
import {Link} from "react-router-dom";
import userStore from "../../store/UserStore";

@observer
export default class Sidebar extends React.Component<{}, {}> {
    render() {
        return (
            <Modal dialogClassName="sidebar" show={appStore.isSidebarVisible} onHide={() => appStore.changeSidebarVisibility()} autoFocus={true}>
                <Modal.Header className="sidebar" closeButton={true}/>
                <Modal.Body className="sidebar">
                    <Link className="font-color" to="/" onClick={() => appStore.changeSidebarVisibility()}><h5 id="home">Početna</h5></Link>
                    {/*<Link className="font-color" to="/sports" onClick={() => appStore.changeSidebarVisibility()}><h5 id="sports">Sports</h5></Link>
                    <Link className="font-color" to="" onClick={() => appStore.changeLocationDropdownVisibility()}><h5 id="location">Location</h5></Link>*/}
                    {/*{
                        (appStore.isLocationDropdownVisible)
                            ? <ul>
                                <li>
                                    <Link className="font-color" to={`/locations/${1}`} onClick={() => appStore.changeSidebarVisibility()}>Location1</Link>
                                </li>
                                <li>
                                    <Link className="font-color" to={`/locations/${2}`} onClick={() => appStore.changeSidebarVisibility()}>Location2</Link>
                                </li>
                            </ul>
                            : <div></div>
                    }*/}
                    <Link className="font-color" to="/videos" onClick={() => appStore.changeSidebarVisibility()}><h5 id="videos">Video</h5></Link>
                    <br/>
                    {
                        (sessionStorage.getItem('token'))
                            ? <Link className="font-color" to="/myprofile" onClick={() => appStore.changeSidebarVisibility()}><h5 id="myprofile">Moj profil</h5></Link>
                            : <div/>
                    }
                    {
                        (!sessionStorage.getItem('token'))
                            ? <Link className="font-color" to="/login" onClick={() => appStore.changeSidebarVisibility()}><h5 id="login">Prijava</h5></Link>
                            : <div/>
                    }
                    {
                        (!sessionStorage.getItem('token'))
                            ? <Link className="font-color" to="/register" onClick={() => appStore.changeSidebarVisibility()}><h5 id="register">Registracija</h5></Link>
                            : <div/>
                    }
					<br/>
					{
                        (sessionStorage.getItem('token') && sessionStorage.getItem('isAdmin'))
                            ? <Link className="font-color" to="/admin" onClick={() => appStore.changeSidebarVisibility()}><h5 id="admin">Postavke</h5></Link>
                            : <div/>
                    }
                    {
                        (sessionStorage.getItem('token'))
                            ? <Link className="font-color" to="/" onClick={() => this.logout()}><h5 id="logout">Odjava</h5></Link>
                            : <div/>
                    }
                    <br/>
                    <Link className="font-color" to="/about" onClick={() => appStore.changeSidebarVisibility()}><h5 id="about">O aplikaciji</h5></Link>
                </Modal.Body>
            </Modal>
        );
    }

    private logout() {
        userStore.clearSessionStorage();
        appStore.changeSidebarVisibility();
    }
}