import * as React from "react";
import SlideShow from "./SlideShow";
import AboutDescription from "./AboutDescription";

export default class Home extends React.Component<{}, {}> {
    render() {
        document.title = "Go2Play";

        return (
            <div>
                <SlideShow/>
                <br/>
                <AboutDescription/>
            </div>
        );
    }
}