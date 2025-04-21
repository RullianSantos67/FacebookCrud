<jsp:directive.page contentType="text/html; charset=UTF-8" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="pt-BR">

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-icons.css">
    <title>Facebook - Posts</title>
</head>

<body>
    <div class="container">
        <div class="row pt-5">
            <div class="col-md-1"></div>
            <div class="col-md-10">
                <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-secondary">
                    <i class="bi bi-house"></i>
                </a>

                <div class="d-flex justify-content-between align-items-center mb-3">
                    <h1 class="m-0">Posts</h1>

                  
                    </a> 
   <a href="${pageContext.request.contextPath}/post/form" class="btn btn-primary">Novo Post</a>
                    
                </div>

                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th scope="col">Id</th>
                            <th scope="col">Conteúdo</th>
                            <th scope="col">Data</th>
                            <th scope="col">User ID</th>
                            <th scope="col">Editar</th>
                            <th scope="col">Remover</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${posts}">
                            <tr>
                                <th scope="row">${p.id}</th>
                                <td>${p.content}</td>
                                <td>${p.postDate}</td>
                                <td>${p.user.id}</td>
                                <td>
                                    <a class="bi bi-pencil-square" href="${pageContext.request.contextPath}/post/update?postId=${p.id}"></a>
                                </td>
                                <td>
                                    <a class="bi bi-trash" href="${pageContext.request.contextPath}/post/delete?postId=${p.id}" onclick="return confirm('Excluir este post?');"></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="col-md-1"></div>
        </div>
    </div>
    <script src="${pageContext.request.contextPath}/js/bootstrap.bundle.min.js"></script>
</body>
</html>
