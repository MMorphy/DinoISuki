import React from "react";

export default class SecurityOfOnlinePaymentsText extends React.Component<{}, {}> {
    render() {
        return (
            <p className="security-of-online-payments-align">
                While conducting payments on our web shop you are using CorvusPay – an advanced system for secure
                acceptance of credit cards on the Internet.
                <br/>
                <br/>
                CorvusPay ensures complete privacy of your credit card data from the moment you type them into the
                CorvusPay payment form. Data required for billing is forwarded encrypted from your web browser to the
                bank that issued your payment card. Our store never comes into contact with your sensitive payment card
                data. Similarly, CorvusPay operators cannot access your complete cardholder data. An isolated system core
                independently transmits and manages sensitive data while at the same time keeping it completely safe.
                <br/>
                <br/>
                The form for entering payment data is secured by an SSL transmission cipher of the greatest reliability. All
                stored data is additionally protected by hi-grade encryption, using hardware devices certified by FIPS 140 2
                Level 3 standard. CorvusPay fulfills all of the requirements for safe online payment prescribed by the
                leading credit card brands, operating in compliance to the PCI DSS Level 1 standard - the highest security
                standard of the payment card industry. Payments made by cards enroled with the 3-D Secure program are
                further authenticated by the issuing bank, confirming your identity through the use of a token or a
                password.
                <br/>
                <br/>
                All information collected by Corvus Info is considered a banking secret and treated accordingly. The
                information is used exclusively for the purposes for which they were intended. Your sensitive data is fully
                secure and it’s privacy is guaranteed by the state of the art safeguard mechanisms. We collect only the data
                necessary for performing the work in accordance with the demanding prescribed procedures for online
                payment. Security controls and operating procedures applied within the CorvusPay infrastructure not only ensure
                current reliability of CorvusPay but permanently maintain and enhance the security levels of protecting
                your credit card information by maintaining strict access controls, regular security and in-depth system
                checks for preventing network vulnerabilities.
                <br/>
                <br/>
                Thank you for using CorvusPay!
            </p>
        );
    }
}