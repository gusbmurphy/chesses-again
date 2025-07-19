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

}
