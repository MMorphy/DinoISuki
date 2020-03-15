import * as React from "react";
import SlideShow from "../home/SlideShow";
import {observer} from "mobx-react";

//Panel removed, replaced with Card components
@observer
export default class Sports extends React.Component<{}, {}> {
    render() {
        document.title = "Sports";

        return (
            <div>
                <SlideShow/>
                <br/>
                <div>
                    {/*<Panel id="collapsible-panel-example-2" defaultExpanded={false}>
                        <Panel.Heading>
                            <Panel.Title toggle>
                                Socker
                            </Panel.Title>
                        </Panel.Heading>
                        <Panel.Collapse>
                            <LocationInfo locationName="location1" locationDesc="This is first location"/>
                            <LocationInfo locationName="location2" locationDesc="This is second location"/>
                        </Panel.Collapse>
                    </Panel>*/}
                </div>
            </div>
        );
    }
}