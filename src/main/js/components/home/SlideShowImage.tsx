import React from "react";

interface SlideShowImageProps {
    image: string;
}

export default class SlideShowImage extends React.Component<SlideShowImageProps, {}> {
    render() {
        return (
            <div className="each-slide">
                <div style={{'backgroundImage': `url(${this.props.image})`}}/>
            </div>
        );
    }
}