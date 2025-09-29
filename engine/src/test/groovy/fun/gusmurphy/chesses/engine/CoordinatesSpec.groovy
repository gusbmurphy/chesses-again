package fun.gusmurphy.chesses.engine

import spock.lang.Specification

import static fun.gusmurphy.chesses.engine.Coordinates.*

class CoordinatesSpec extends Specification {

    def "we can check if two coordinates are diagonal to each other"() {
        expect:
        c1.isDiagonalFrom(c2) == expected

        where:
        c1 | c2 || expected
        A1 | A2 || false
        A1 | B2 || true
        A1 | C2 || false
    }

    def "we can get all coordinates between two positions that are vertical from each other"() {
        given:
        LineOfCoordinates line = a.lineTo(b)

        expect:
        line.inOrder() == expected

        where:
        a  | b  || expected
        C2 | C7 || [C3, C4, C5, C6]
        D6 | D3 || [D5, D4]
    }

    def "we can get all coordinates between two positions that are horizontal from each other"() {
        given:
        LineOfCoordinates line = a.lineTo(b)

        expect:
        line.inOrder() == expected

        where:
        a  | b  || expected
        C2 | G2 || [D2, E2, F2]
        H6 | D6 || [G6, F6, E6]
    }

}
