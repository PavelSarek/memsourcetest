package name.psarek.memsourcetest

import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import groovy.transform.InheritConstructors
import groovy.util.logging.Log4j
import org.codehaus.groovy.grails.web.json.JSONObject
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

import java.text.SimpleDateFormat

/**
 * Obtrains and caches the memsource's API user token.
 */
@Log4j
class LoginService {

    static loginPath = "/auth/login"

    @InheritConstructors
    class LoginException extends Exception {
    }

    def accountService
    def grailsApplication
    def messageSource

    private String lastToken
    private Account lastTokenAccount
    private long lastTokenExpires       // millis

    /**
     * Obtains the token
     * @param   retry     <code>true</code> forces retrieving a new token
     * @return  token
     * @throws  LoginException if HTTP call fails for any reason
     */
    String obtainLoginToken(boolean retry) throws LoginException {
        final currentAccount = accountService.getCurrentAccount()

        if (!retry && cachedTokenUsable(currentAccount)) {
            return lastToken
        }

        RestResponse response = postLoginApi(currentAccount)

        if (response.getStatusCode() != HttpStatus.OK) {
            throwLoginException(response)
        } else {
            saveLastToken(response, currentAccount)
            return lastToken
        }
    }

    private boolean cachedTokenUsable(Account currentAccount) {
        return lastToken && notExpired(lastTokenExpires) && currentAccount.valueEquals(lastTokenAccount)
    }

    private boolean notExpired(long lastTokenExpires) {
        return System.currentTimeMillis() < lastTokenExpires
    }

    private RestResponse postLoginApi(currentAccount) {
        def response = new RestBuilder().post(grailsApplication.config.memsource.api.base + loginPath) {
            accept(JSONObject, "application/json")
            contentType("application/x-www-form-urlencoded")
            body(accountToForm(currentAccount))
        }
        response
    }

    private MultiValueMap<String, String> accountToForm(Account account) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>()
        form.add("userName", account.getUserName())
        form.add("password", account.getPassword())
        return form
    }

    private void saveLastToken(RestResponse response, Account currentAccount) {
        lastToken = response.json.token
        lastTokenAccount = currentAccount

        // "expires":"2017-02-13T16:44:23+0000"
        try {
            lastTokenExpires = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(response.json.expires).time
        } catch (Exception ignored) {
            log.error  ignored
            lastTokenExpires = 0
        }
    }

    private void throwLoginException(RestResponse response) {
        def message = messageSource.getMessage(
                "project.login.failed",
                [response.getStatusCode(), response.getText()] as Object[],
                LocaleContextHolder.getLocale()
        )
        log.error message
        throw new LoginException(message)
    }

}
