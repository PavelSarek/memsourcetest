package name.psarek.memsourcetest

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(AccountService)
@Mock(Account)
class AccountServiceSpec extends Specification {

    void "test account is correctly updated"() {
        given:
            def accountA = new Account(userName: 'aaa', password: 'AAA')
            def accountB = new Account(userName: 'bbb', password: 'BBB')

        when:
            service.currentAccount
            service.updateCurrentAccount(accountA)
        then:
            compareAccount(service.currentAccount, accountA)

        when:
            service.updateCurrentAccount(accountB)
        then:
            compareAccount(service.currentAccount, accountB)
    }

    boolean compareAccount(Account lhs, Account rhs) {
        return lhs.userName == rhs.userName && lhs.password == rhs.password
    }

}
