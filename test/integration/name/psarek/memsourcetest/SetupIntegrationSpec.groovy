package name.psarek.memsourcetest

import grails.test.mixin.integration.Integration
import grails.test.spock.IntegrationSpec
import grails.transaction.Rollback

@Rollback
class SetupIntegrationSpec extends IntegrationSpec {

    def accountService

    void "test update changes the account"() {
        given: "the controller"
        def setupController = new SetupController()

        and: "input date"
        setupController.params.userName = "NewUsername"
        setupController.params.password = "secret"

        when:
        setupController.update();

        then:
        setupController.response.redirectedUrl == "/test/edit"
        def current = accountService.getCurrentAccount()
        current.userName == "NewUsername"
        current.password == "secret"
    }

}
