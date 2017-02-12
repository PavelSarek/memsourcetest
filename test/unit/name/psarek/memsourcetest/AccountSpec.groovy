
package name.psarek.memsourcetest

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Account)
class AccountSpec extends Specification {

    void "Test userName is not empty"() {
        when: 'the userName is empty'
        def account = new Account(password: "p")

        then: 'validation should fail'
        !account.validate()
    }

    void "Test password is not empty"() {
        when: 'the password is empty'
        def account = new Account(userName: "u")

        then: 'validation should fail'
        !account.validate()
    }

}
