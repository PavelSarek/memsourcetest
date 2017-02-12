package name.psarek.memsourcetest

class ProjectsController {

    static defaultAction = "list"

    def loginService;

    def list() {
        def loginToken = loginService.obtainLoginToken(false)

        render loginToken
    }

    def loginException(final LoginService.LoginException exception) {
        flash.error = "Login credentials are incorrect: " + exception.getMessage()
        redirect(controller: "setup", action: "edit")
    }

}
