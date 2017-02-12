<%@ page import="name.psarek.memsourcetest.Account" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title><g:message code="setup.header" /></title>
	</head>

	<body>
		<div id="setup" class="content" role="main">
			<h1><g:message code="setup.header" /></h1>

			<g:if test="${flash.error}">
				<div class="errors" role="status">${flash.error}</div>
			</g:if>
			<g:if test="${flash.message}">
			    <div class="message" role="status">${flash.message}</div>
			</g:if>

			<g:hasErrors bean="${account}">
			<ul class="errors" role="alert">
				<g:eachError bean="${account}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>

			<g:form url="[resource:setup, action:'update']" method="POST" >

				<fieldset class="form">
					<div class="fieldcontain ${hasErrors(bean: account, field: 'userName', 'error')} required">
						<label for="userName">
							<g:message code="account.userName.label" default="Username" />
							<span class="required-indicator">*</span>
						</label>
						<g:textField name="userName" required="" value="${account?.userName}"/>
					</div>

					<div class="fieldcontain ${hasErrors(bean: account, field: 'password', 'error')} required">
						<label for="password">
							<g:message code="account.password.label" default="Password" />
							<span class="required-indicator">*</span>
						</label>
						<g:passwordField name="password" required="" value="${account?.password}"/>

					</div>
				</fieldset>

				<fieldset class="buttons">
					<g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" />
				</fieldset>
			</g:form>

		</div>
	</body>
</html>
