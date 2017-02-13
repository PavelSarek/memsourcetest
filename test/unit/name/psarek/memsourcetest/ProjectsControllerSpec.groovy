package name.psarek.memsourcetest

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ProjectsController)
class ProjectsControllerSpec extends Specification {

    void "test load passes correct page value"() {
        given: "a mock project service"
        def mockProjectsService = Mock(ProjectsService)
        1 * mockProjectsService.loadProjects(42) >> [[name: "Project A"]]
        controller.projectsService = mockProjectsService

        and:
        controller.params.page="42"


        when: "controller is invoked"
        controller.load()

        then:
        response.json[0].name == "Project A"
    }

}
