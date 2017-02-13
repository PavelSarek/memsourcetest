package name.psarek.memsourcetest

import grails.transaction.Transactional

/**
 * Store the user account.
 * This class takes care the is only one account (in normal course of operation).
 */
@Transactional
class AccountService {

    Account getCurrentAccount() {
        return getSingletonAccount()
    }

    def updateCurrentAccount(Account updated) {
        def account = getSingletonAccount()
        account.userName = updated.userName
        account.password = updated.password
        account.save()
    }

    private Account getSingletonAccount() {
        def account = Account.first()
        if (account == null) {
            account = new Account()
        }
        return account
    }

}
