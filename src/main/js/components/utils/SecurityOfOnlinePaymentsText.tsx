import React from "react";

export default class SecurityOfOnlinePaymentsText extends React.Component<{}, {}> {
    render() {
        return (
            <p className="security-of-online-payments-align">
                    Pri plaćanju na našoj web trgovini koristite CorvusPay – napredni sustav za siguran prihvat platnih kartica
                    putem interneta.
                <br/>
                <br/>
                    CorvusPay osigurava potpunu tajnost Vaših kartičnih podataka već od trenutka kada ih upišete u CorvusPay
                    platni formular. Platni podaci prosljeđuju se šifrirano od Vašeg web preglednika do banke koja je izdala Vašu
                    karticu. Naša trgovina nikada ne dolazi u kontakt s cjelovitim podacima o Vašoj platnoj kartici. Također, podaci
                    su nedostupni čak i djelatnicima CorvusPay sustava. Izolirana jezgra samostalno prenosi i upravlja osjetljivim
                    podacima, čuvajući ih pri tom potpuno sigurnima.
                <br/>
                <br/>
                    Formular za upis platnih podataka osiguran je SSL transportnom šifrom najveće pouzdanosti. Svi skladišteni
                    podaci dodatno su zaštićeni šifriranjem, korištenjem kriptografskog uređaja certificiranog prema FIPS
                    140-2 Level 3 standardu. CorvusPay zadovoljava sve zahtjeve vezane uz sigurnost on-line plaćanja propisane
                    od strane vodećih kartičnih brandova, odnosno posluje sukladno normi – PCI DSS Level 1 – najviši sigurnosni
                    standard industrije platnih kartica. Pri plaćanju karticama uvrštenim u 3-D Secure program Vaša banka uz
                    valjanost same kartice dodatno potvrđuje i Vaš identitet pomoću tokena ili lozinke.
                <br/>
                <br/>
                    Corvus Info sve prikupljene informacije smatra bankovnom tajnom i tretira ih u skladu s tim. Informacije se
                    koriste isključivo u svrhe za koje su namijenjene. Vaši osjetljivi podaci u potpunosti su sigurni, a njihova
                    privatnost zajamčena je najmodernijim zaštitnim mehanizmima. Prikupljaju se samo podaci nužni za
                    obavljanje posla sukladno propisanim zahtjevnim procedurama za on-line plaćanje.
                    Sigurnosne kontrole i operativne procedure primijenjene na našu infrastrukturu osiguravaju trenutnu
                    pouzdanost CorvusPay sustava. Uz to održavanjem stroge kontrole pristupa, redovitim praćenjem sigurnosti
                    i dubinskim provjerama za sprječavanje ranjivosti mreže te planskim provođenjem odredbi o informacijskoj
                    sigurnosti trajno održavaju i unaprjeđuju stupanj sigurnosti sustava zaštitom Vaših kartičnih podataka.
                <br/>
                <br/>
                    Hvala što koristite CorvusPay!
            </p>
        );
    }
}