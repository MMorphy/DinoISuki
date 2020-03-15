import 'bootstrap/dist/css/bootstrap.min.css';
import "./index.css";

import ReactDOM from "react-dom";
import React from "react";
import App from "./components/App";
import {configure} from "mobx";
import '@fortawesome/fontawesome-free/css/all.min.css';

configure({ enforceActions: "observed" });

ReactDOM.render(
    <App/>,
    document.getElementById("app")
);
