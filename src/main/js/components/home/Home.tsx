import * as React from "react";
import SlideShow from "./SlideShow";
import AboutDescription from "./AboutDescription";

export default class Home extends React.Component<{}, {}> {
    render() {
        document.title = "The Ball";

        return (
            <div>
                <SlideShow/>
                <br/>
                <AboutDescription/>
            </div>
        );
    }
}