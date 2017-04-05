package sample;

/**
 * Created by Fikre on 07.03.2017.
 */
public class ConwaysRules implements Rules {

    @Override
    public int overlever(int lever, int antallNaboer) {
        // FØLGE REGLER OG SE OM DEN SKAL LEVE

        //HVIS DET ER NØYAKTIG 3 LEVENDE CELLER RUNDT EN DØD CELLE, BLIR DET TIL EN LEVENDE CELLE
        //OVERPRODUKSJON
        if (lever == 0 && antallNaboer == 3) {
            return 1;
        }

        // HVIS CELLER RUNDT ER UNDER 2 LEVENDE, SÅ DØR DEN AV UNDERPOPULASJON
        if (lever == 1 && antallNaboer < 2) {
            return 0;
        }

        //HVIS CELLER RUNDT ER 2 ELLER 3, SÅ LEVER DEN VIDERE
        if (lever == 1 && antallNaboer == 2 || antallNaboer == 3) {
            return 1;
        }

        //HVIS CELLER RUNDT ER FLER ENN 3, DØR DEN AV OVERPOPULASJON
        if (lever == 1 && antallNaboer > 3) {
            return 0;
        }

        return lever; // RETUNER 0 ELLER 1.     1 ER LEVENDE OG 0 ER DØD.
    }
}


