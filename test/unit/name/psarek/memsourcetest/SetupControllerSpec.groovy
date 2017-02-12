package name.psarek.memsourcetest

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(SetupController)
class SetupControllerSpec extends Specification {

    void "test edit returns the current account"() {
        given: "a mock account service"
        def mockAccountService = Mock(AccountService)
        1 * mockAccountService.getCurrentAccount() >> new Account(userName: "U", password: "P")
        controller.accountService = mockAccountService

        when: "controller is invoked"
        def model = controller.edit()

        then:
        model.account.userName == "U"
        model.account.password == "P"
    }

    void "test update changes the current account"() {
        given: "a mock account service"
        def mockAccountService = Mock(AccountService)
        controller.accountService = mockAccountService
        controller.params.userName = "NU"
        controller.params.password = "NP"

        when: "controller receives the account"
        controller.update()

        then:
        flash.message
        response.redirectedUrl == "/setup/edit"
        1 * mockAccountService.updateCurrentAccount({
            it.userName == "NU" && it.password == "NP"
        })
    }

}
