import React from "react";
import SalesPointDetails from "./SalesPointDetails";
import {Accordion} from "react-bootstrap";
import ProductServices from "./ProductServices";
import TermsOfSale from "./TermsOfSale";
import SecurityOfOnlinePayments from "./SecurityOfOnlinePayments";
import CorvusAndPaymentLogos from "../utils/CorvusAndPaymentLogos";

export default class AboutGo2Play extends React.Component<{}, {}> {
    render() {
        return (
            <div className="about-go2play-margin">
                <Accordion defaultActiveKey="0" bsPrefix="accordion accordion-size">
                    <SalesPointDetails/>
                    <ProductServices/>
                    <TermsOfSale/>
                    <SecurityOfOnlinePayments/>
                </Accordion>
                <br/>
                <CorvusAndPaymentLogos/>
            </div>
        );
    }
}