import * as React from "react";

export default class AboutDescription extends React.Component<{}, {}> {
    render() {
        return (
            <div>
                <h5 className="about-letter-color"><b>The Ball</b></h5>
                <div className="about-text-align">
                    <p>
                        "Jučer sam zabio nevjerojatno prekrasan gol." - rečenica je koja ujedno opisuje tko smo,
                        kako smo nastali te kakve koristi ti imaš od nas. Pogađaš?
                        <br/><br/>
                        Tko smo: The Ball je aplikacija na kojoj možeš skinuti snimku svih svojih golova, driblinga,
                        obrana, poteza, smiješnih situacija koje nitko nije vidio (klasika, kako to nekome objasniti?).
                        Postavili smo kamere po nogometnim terenima u Zagrebu, snimamo svaku tekmu, svaki tvoj gol,
                        svaki dribling, svaki volej, svaku obranu, svaki tvoj pad preko lopte, svaki tvoj promašaj,
                        tako da ga možeš pokazati kome god želiš - frendovima, ženi, curi, mami, tati - kome god hoćeš.
                        Trebaš se samo registrirati ili skinuti našu aplikaciju i čeka te video ili isječak dijela tekme
                        koji želiš. Nisi mogao na termin? Ozlijeđen si? Na putu si? Imaš obaveza? Imamo rješenje i za
                        to, uključi se u live prijenos svog termina i prati svoju ekipu!
                        <br/><br/>
                        Kako smo nastali: upravo tako - nitko nije vidio sve te lijepe i smiješne poteze, golove,
                        promašaje koje kasnije nikome ne bi mogao opisati i dočarati.
                        <br/><br/>
                        Kakve koristi imaš od nas? Uz live prijenos termina, video termina, pripremili smo i bogate
                        tjedne i mjesečne nagrade, zanimljive kvizove i ostalo.
                        <br/><br/>
                        Ako te zanima nešto više o nama ili imaš dodatnih pitanja, javi se na mail: --------
                        <br/><br/><br/>
                        The Ball team
                    </p>
                </div>
            </div>
        );
    }
}