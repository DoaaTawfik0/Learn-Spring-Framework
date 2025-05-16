<%@ include file = "common/header.jspf" %>
<%@ include file = "common/navigation.jspf" %>

<div class = "container">

	<h4>Login
		<h4>

			<pre>${errorMessage}</pre>
			<form method = "post">

				Name: <input type = "text"
				             name = "name">
				Password: <input type = "password"
				                 name = "password">

				Submit<input type = "submit">


			</form>
</div>
</body>

<%@ include file = "common/footer.jspf" %>

