package name.psarek.memsourcetest

import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import groovy.transform.InheritConstructors
import org.codehaus.groovy.grails.web.json.JSONArray
import org.springframework.http.HttpStatus
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

import javax.xml.ws.http.HTTPException

/**
 * Loads project from Memsource API
 */
class ProjectsService {

    @InheritConstructors
    class ProjectLoadingException extends Exception {
    }

    static projectListPath = "/project/list"

    def loginService
    def grailsApplication

    /**
     * Loads the page of project
     * @param   page  pages starting with 0
     * @return  project as map/list or map[status, text in case of error]
     */
    def loadProjects(int page) throws ProjectLoadingException, LoginService.LoginException {
        RestResponse response = postProjectList(loginService.obtainLoginToken(false), page)

        if (response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            response = postProjectList(loginService.obtainLoginToken(true), page)
        }

        if (response.getStatusCode() == HttpStatus.OK) {
            List projects = processResponse(response)
            return projects
        } else {
            throw new ProjectLoadingException(response.getStatus() + ": " + response.getText())
        }
    }

    private RestResponse postProjectList(String token, int page) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>()
        form.add("token", token)
        form.add("page", page.toString())

        def response = new RestBuilder().post(grailsApplication.config.memsource.api.base + projectListPath) {
            accept(JSONArray, "application/json")
            contentType("application/x-www-form-urlencoded")
            body(form)
        }

        return response
    }

    private List processResponse(RestResponse response) {
        // go over all object and keep only necessary properties to reduce transport size
        def projects = []
        def jsonArray = response.body as JSONArray
        for (int index = 0; index < jsonArray.size(); index++) {
            Map eachObject = jsonArray.get(index);
            projects << [name       : eachObject.get("name"),
                         status     : eachObject.get("status"),
                         sourceLang : eachObject.get("sourceLang"),
                         targetLangs: eachObject.get("targetLangs")
            ]
        }
        return projects
    }

}
