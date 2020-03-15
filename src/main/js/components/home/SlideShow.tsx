import * as React from "react";
import { Slide } from 'react-slideshow-image';
import SlideShowImage from "./SlideShowImage";
// @ts-ignore
import image1 from "../../../resources/images/slide-show/01.jpg";
// @ts-ignore
import image2 from "../../../resources/images/slide-show/02.jpg";
// @ts-ignore
import image3 from "../../../resources/images/slide-show/03.jpg";
// @ts-ignore
import image4 from "../../../resources/images/slide-show/04.jpg";
// @ts-ignore
import image5 from "../../../resources/images/slide-show/05.jpg";
// @ts-ignore
import image6 from "../../../resources/images/slide-show/06.jpg";
// @ts-ignore
import image7 from "../../../resources/images/slide-show/07.jpg";
// @ts-ignore
import image8 from "../../../resources/images/slide-show/08.jpg";
// @ts-ignore
import image9 from "../../../resources/images/slide-show/09.jpg";
// @ts-ignore
import image10 from "../../../resources/images/slide-show/10.jpg";
// @ts-ignore
import image11 from "../../../resources/images/slide-show/11.jpg";
// @ts-ignore
import image12 from "../../../resources/images/slide-show/12.jpg";
// @ts-ignore
import image13 from "../../../resources/images/slide-show/13.jpg";
// @ts-ignore
import image14 from "../../../resources/images/slide-show/14.jpg";
// @ts-ignore
import image15 from "../../../resources/images/slide-show/15.jpg";
// @ts-ignore
import image16 from "../../../resources/images/slide-show/16.jpg";
// @ts-ignore
import image17 from "../../../resources/images/slide-show/17.jpg";

const slideImages = [image1, image2, image3, image4, image5, image6, image7, image8, image9,
    image10, image11, image12, image13, image14, image15, image16, image17];

const properties = {
    duration: 5000,
    transitionDuration: 500,
    infinite: true,
    indicators: true,
    arrows: true,
};

export default class SlideShow extends React.Component<{}, {}> {
    render() {
        let slideImageHtmls : Array<any> = [];

        slideImages.forEach((image: any, index: number) => {
            slideImageHtmls.push(
                <SlideShowImage image={image} key={index}/>
            )
        });

        return (
            <div className="slide-container">
                <Slide {...properties}>
                    {slideImageHtmls}
                </Slide>
            </div>
        );
    }
}