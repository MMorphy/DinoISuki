import * as React from "react";
import {observer} from "mobx-react";

@observer
export default class Locations extends React.Component<{}, {}> {
    render() {
        document.title = "Locations";

        return (
            <div>
                location page
            </div>
        );
    }
}