import React from "react";
// @ts-ignore
import corvusPay from "../../../resources/images/logo-kartice/CorvusPAY.png";
// @ts-ignore
import visaCard from "../../../resources/images/logo-kartice/visa_fc.png";
// @ts-ignore
import visaObrocna from "../../../resources/images/logo-kartice/VISA 2019 obrocna otplata tag generiƒki.png";
// @ts-ignore
import mastercard from "../../../resources/images/logo-kartice/mc_acc_opt_70_1x.png";
// @ts-ignore
import maestro from "../../../resources/images/logo-kartice/ms_acc_opt_70_1x.png";
// @ts-ignore
import diners from "../../../resources/images/logo-kartice/DCI-Logo-horz.jpg";
// @ts-ignore
import discover from "../../../resources/images/logo-kartice/DISCOVER.jpg";
// @ts-ignore
import visa_sp from "../../../resources/images/logo-sigurnosni-programi/verified_by_visa.jpg";
// @ts-ignore
import mastercard_sp from "../../../resources/images/logo-sigurnosni-programi/mastercard_securecode.gif";
// @ts-ignore
import diners_sp from "../../../resources/images/logo-sigurnosni-programi/logo_sigurna kupnja_2.1.png";

export default class CorvusAndPaymentLogos extends React.Component<{}, {}> {
    render() {
        return (
            <div>
                <a href="https://www.corvuspay.com/" target="_blank">
                    <img className="corvus-payment-logo-size" src={corvusPay} alt="Shows"/>
                </a>
                &nbsp;&nbsp;
                <a href="https://www.visa.com.hr" target="_blank">
                    <img className="payment-logo-size" src={visaCard} alt="Shows"/>
                </a>
                &nbsp;&nbsp;
                <a href="https://www.pbzcard-premium.hr/hr/pogodnosti/obrocna-otplata/" target="_blank">
                    <img className="payment-logo-size" src={visaObrocna} alt="Shows"/>
                </a>
                &nbsp;&nbsp;
                <a href="https://www.zaba.hr/download/ecommerce/verified_by_visa.htm" target="_blank">
                    <img className="payment-logo-size" src={visa_sp} alt="Shows"/>
                </a>
                &nbsp;&nbsp;
                <a href="https://www.mastercard.com" target="_blank">
                    <img src={mastercard} alt="Shows"/>
                </a>
                &nbsp;&nbsp;
                <a href="https://www.mastercard.hr/hr-hr/consumers/find-card-products/debit-cards/maestro-debit.html" target="_blank">
                    <img src={maestro} alt="Shows"/>
                </a>
                &nbsp;&nbsp;
                <a href="https://www.zaba.hr/download/ecommerce/master_securecode.htm" target="_blank">
                    <img className="payment-logo-size" src={mastercard_sp} alt="Shows"/>
                </a>
                &nbsp;&nbsp;
                <a href="https://www.diners.com.hr/Pogodnosti-i-usluge/MasterCard-SecureCode.html?Ym5cMzQsY2FyZFR5cGVcMSxwXDc3" target="_blank">
                    <img className="discover-sp-payment-logo-size" src={diners} alt="Shows"/>
                </a>
                &nbsp;&nbsp;
                <a href="https://www.discover.com" target="_blank">
                    <img className="payment-logo-size" src={discover} alt="Shows"/>
                </a>
                &nbsp;&nbsp;
                <a href="https://www.erstecardclub.hr/hr/pogodnosti/visa-mastercard-pogodnosti/3d-usluge" target="_blank">
                    <img className="discover-sp-payment-logo-size" src={diners_sp} alt="Shows"/>
                </a>
            </div>
        );
    }
}