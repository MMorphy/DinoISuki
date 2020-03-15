import * as React from "react";

interface LocationInfoProps {
    locationName: string;
    locationDesc: string;
}

//Panel removed, replaced with Card components
export default class LocationInfo extends React.Component<LocationInfoProps, {}> {
    render() {
        return (
            <div></div>
            /*<Panel.Body>
                <h4>{this.props.locationName}</h4>
                <p>
                    {this.props.locationDesc}
                </p>
                <Button bs-style="primary" onClick={ () => {} }>
                    Book
                </Button>
            </Panel.Body>*/
        );
    }
}