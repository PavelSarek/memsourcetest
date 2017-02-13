<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title><g:message code="projects.header" /></title>
    <g:javascript library="jquery" />
    <g:javascript>

        function addNewProject(project) {
            console.log("invoke with " + project);
            var projectList = $('#projectList');
            projectList.append($('<dl>').text(project.name));
            var innerList = $('<ul>');
            innerList.append($('<li>').text("State: " + project.status));
            innerList.append($('<li>').text("Source language: " + project.sourceLang));
            innerList.append($('<li>').text("Target languages: " + project.targetLangs.join(", ")));
            projectList.append($('<dt>').append(innerList));
        }

        function incrementPage() {
            var pageField = $("#page");
            var newValue = parseInt(pageField.val()) + 1;
            pageField.val(newValue);
        }

        function addNewProjects(projects) {
            $("#flashError").hide();
            if (!projects || projects.length == 0) {
                $("#loadMoreButton").hide();
                $("#allLoaded").show();
            } else {
                projects.forEach(function (eachProject) {
                    addNewProject(eachProject);
                });

                incrementPage();
            }
        }

        function reportFailure(data) {
            $("#flashError").show();
            $("#flashError").text(data);
        }

        function showSpinner(visible) {
            var loadMoreSpinner = $('#loadMoreSpinner');
            if (visible) {
                loadMoreSpinner.show();
            } else {
                loadMoreSpinner.hide();
            }
        }

        jQuery(document).ready(function(){
            $("#loadMoreButton").click();
        });
    </g:javascript>
</head>

<body>
    <div id="projects" class="content" role="main">
        <h1><g:message code="projects.header" /></h1>

        <div id="flashError" class="errors" role="status" style="display: none"><!-- AJAX target --></div>

        <div>
            <dl id="projectList">
                <!-- AJAX append target -->
            </dl>
        </div>

        <g:form>
            <g:hiddenField id="page" name="page" value="0" />
            <fieldset class="buttons">
                <g:submitToRemote
                        id="loadMoreButton"
                        class="load"
                        value="${message(code: 'projects.loadMore')}"
                        url="[action: 'load']"
                        onSuccess="addNewProjects(data)"
                        onFailure="reportFailure(XMLHttpRequest.responseText)"
                        onLoading="showSpinner(true)"
                        onComplete="showSpinner(false)"
                />
                <label id="allLoaded" style="display: none"><g:message code="projects.allLoaded" /></label>
                <div id="loadMoreSpinner" class="spinner" style="display: none"></div>
            </fieldset>

        </g:form>

    </div>
</body>
</html>
