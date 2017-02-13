package name.psarek.memsourcetest

import grails.converters.JSON

class ProjectsController {

    static defaultAction = "list"

    def projectsService
    def loginService

    def load() {
        try {
            def list = projectsService.loadProjects(parsePageValue())
            render (list as JSON)
        } catch (ProjectsService.ProjectLoadingException ple) {
            render status: 500, text: ple.getMessage()
        }
    }

    private int parsePageValue() {
        try {
            return Integer.valueOf(params.page)
        } catch (Exception ignored) {
            return 0
        }
    }

    def list() {
        loginService.obtainLoginToken(false)        // verify we can log in
        []
    }

    def loginException(final LoginService.LoginException exception) {
        flash.error = "Login credentials are incorrect: " + exception.getMessage()
        redirect(controller: "setup", action: "edit")
    }

}
