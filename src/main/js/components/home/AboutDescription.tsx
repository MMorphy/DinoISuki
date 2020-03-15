import * as React from "react";

export default class AboutDescription extends React.Component<{}, {}> {
    render() {
        return (
            <div>
                <h5 className="about-letter-color">About <b>Go2Play</b></h5>
                <div className="about-text-align">
                    <p>
                        "I scored an incredibly beautiful goal yesterday!" - this is the sentence which
                        at the same time describes who we are, how we started and what are the benefits
                        you have from us. Can you guess?
                        <br/><br/>
                        About us? The Ball is website where you can download records that contain all your
                        goals, dribbles, defences, moves or funny situations you want to be
                        seen, but no one have ever seen them (as always, they are difficult to demonstrate...).
                        We set up cameras around the soccer fields in Zagreb and we are recording every game,
                        every one of your goals, every dribble, volley and defense, your every fall over the
                        ball and every miss, so you can show them whoever you want - your friends, wife,
                        girlfriend, mom, dad - whoever you want. All you need to do is register and/or download
                        our mobile application and video or clip of the match awaits for you.
                        You couldn't come to the appointment? You are injured? Or away on the trip? You have
                        obligations? We have solution for that, too - join to live broadcast of your appointment
                        and support yout team!
                        <br/><br/>
                        How we started? Exactly like this - no one had ever seen all those beautiful and funny
                        moves, goals and misses and you couldn't describe or demonstrate them ever again.
                        <br/><br/>
                        What are the benefits you have from us? With live broadcast of the appointments, video
                        appointments, we also prepared rich weekly and monthly prizes, interesting quizzes and
                        much more...
                        <br/><br/>
                        If you want to know more about us or have additional questions, please just send email:
                        the_ball_team@email.com.
                        <br/><br/><br/>
                        The Ball team
                    </p>
                </div>
            </div>
        );
    }
}