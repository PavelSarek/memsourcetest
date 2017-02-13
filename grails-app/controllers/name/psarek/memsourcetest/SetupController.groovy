package name.psarek.memsourcetest

/**
 * Controls the setup page - setup of user name and password.
 */
class SetupController {

    static defaultAction = "edit"

    def accountService;

    def edit() {
        [ account: accountService.getCurrentAccount() ]
    }

    def update() {
        try {
            def updatedAccount = new Account(params)
            accountService.updateCurrentAccount(updatedAccount);
            flash.message = message(code: "setup.updated.flash")
        } catch (Exception e) {
            flash.error = "Update failed: " + e.getMessage()
        }
        redirect(action: "edit")
    }

}
