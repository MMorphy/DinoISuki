import * as React from "react";
import {HashRouter, Route, Switch} from "react-router-dom";
import Home from "./home/Home";
import Sports from "./sport/Sports";
import Locations from "./location/Locations";
import MyProfile from "./userprofile/MyProfile";
import VideoGallery from "./video/VideoGallery";
import Login from "./login&register/Login";
import Registration from "./login&register/Registration";
import CustomNavbar from "./utils/CustomNavbar";
import Sidebar from "./utils/Sidebar";
import AboutGo2Play from "./about/AboutGo2Play";

export default class App extends React.Component<{}, {}> {
    render() {
        return (
            <HashRouter>
                <div>
                    <CustomNavbar/>
                    <Sidebar/>
                    <Switch>
                        <Route exact path="/" component={Home}/>
                        <Route path="/sports" component={Sports}/>
                        <Route path="/locations/:locationId" component={Locations}/>
                        <Route path="/videos" component={VideoGallery}/>
                        <Route path="/myprofile" component={MyProfile}/>
                        <Route path="/login" component={Login}/>
                        <Route path="/register" component={Registration}/>
                        <Route path="/about" component={AboutGo2Play}/>
                    </Switch>
                </div>
            </HashRouter>
        )
    }
}