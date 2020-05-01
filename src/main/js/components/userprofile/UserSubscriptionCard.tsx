import React from "react";
import {Card, Tab, Tabs} from "react-bootstrap";
import subscriptionStore from "../../store/SubscriptionStore";
import {observer} from "mobx-react";

@observer
export default class UserSubscriptionCard extends React.Component<{}, {}> {
    render() {
        return (
            <Card className="my-profile-card">
                <Card.Header>
                    <h5 className="h5-my-profile-card-title">Moje pretplate</h5>
                </Card.Header>
                <Card.Body>
                    <Tabs defaultActiveKey={subscriptionStore.activeSubscriptions === [] ? "history-subs" : "active-subs"} id="uncontrolled-tab-example" className="subscription-tabs">
                        {
                            subscriptionStore.activeSubscriptions === []
                            ? <div/>
                            : this.getActiveSubscriptionsTab()
                        }

                        <Tab eventKey="history-subs" title="Povijest pretplati">

                        </Tab>
                    </Tabs>
                   {/* <div className="row">
                        <div className="column my-profile-card-first-column">
                            lista subskripcija ili neke tak
                        </div>
                        <div className="column my-profile-card-second-column">
                            <PayPalButton
                                amount="0.01"
                                shippingPreference="NO_SHIPPING" // default is "GET_FROM_FILE"
                                onSuccess={(details: any, data: any) => {
                                    alert("Transaction completed by " + details.payer.name.given_name);

                                    // OPTIONAL: Call your server to save the transaction
                                    return fetch("/paypal-transaction-complete", {
                                        method: "post",
                                        body: JSON.stringify({
                                            orderID: data.orderID
                                        })
                                    });
                                }}
                            />
                        </div>
                    </div>*/}
                </Card.Body>
            </Card>
        );
    }

    private getActiveSubscriptionsTab() {
        return <Tab eventKey="active-subs" title="Aktivne pretplate">

        </Tab>;
    }
}