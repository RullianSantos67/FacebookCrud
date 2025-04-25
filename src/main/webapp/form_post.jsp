<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
  <title>Cadastro de Post</title>
</head>
<body>
  <div class="container">
    <div class="row justify-content-center">
      <form action=/facebook/post/save" method="post" class="col-8">
        <h1 class="mb-4 text-center">
          <c:choose>
            <c:when test="${ not empty post.id }">Editar Post</c:when>
            <c:otherwise>Novo Post</c:otherwise>
          </c:choose>
        </h1>

        <c:if test="${ not empty post.id }">
          <input type="hidden" name="post_id" value="${post.id}" />
        </c:if>

        <div class="mb-3">
          <label for="post_content" class="form-label">Conteúdo</label>
          <textarea id="post_content"
                    name="post_content"
                    class="form-control"
                    rows="4"
                    required><c:out value="${post.content}" /></textarea>
        </div>

        <div class="mb-3">
          <label for="post_date" class="form-label">Data do Post</label>
          <c:choose>
            <c:when test="${ not empty post.postDate }">
              <fmt:formatDate value="${post.postDate}" pattern="yyyy-MM-dd" var="dt" />
              <input type="date"
                     id="post_date"
                     name="post_date"
                     class="form-control"
                     value="${dt}"
                     required />
            </c:when>
            <c:otherwise>
              <input type="date"
                     id="post_date"
                     name="post_date"
                     class="form-control"
                     required />
            </c:otherwise>
          </c:choose>
        </div>

        <div class="mb-3">
          <label for="postuser_id" class="form-label">Usuário</label>
          <select id="postuser_id"
                  name="postuser_id"
                  class="form-select"
                  required>
            <option value="">-- selecione --</option>
            <c:forEach var="u" items="${users}">
              <option value="${u.id}"
                      <c:if test="${ post.user != null and u.id == post.user.id }">selected</c:if>>
                <c:out value="${u.name}" />
              </option>
            </c:forEach>
          </select>
        </div>

        <div class="d-flex justify-content-between">
          <a href="${pageContext.request.contextPath}/posts" class="btn btn-secondary">Cancelar</a>
          <button type="submit" class="btn btn-primary">Salvar</button>
        </div>

      </form>
    </div>
  </div>
  <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
