package controller;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.ModelException;
import model.Post;
import model.dao.UserDAO;
import model.User;
import model.dao.DAOFactory;
import model.dao.PostDAO;

@WebServlet(urlPatterns = {"/posts", "/post/form", "/post/save", "/post/update", "/post/delete"})
public class PostController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		String action = req.getRequestURI();

		System.out.println(action);

		switch (action) {
		case "/facebook/posts": {
			
			// Carregar a lista de usuários do banco
			loadPosts(req);

			RequestDispatcher rd = req.getRequestDispatcher("posts.jsp");
			rd.forward(req, resp);
			break;
		}
		
		case "/facebook/post/save": {

			String postId = req.getParameter("post_id");
			if (postId != null && !postId.equals(""))
				updatePost(req);
			else insertPost(req);

			resp.sendRedirect("/facebook/posts");			
			break;
		}
		case "/facebook/post/update": {

			loadPost(req);

			RequestDispatcher rd = req.getRequestDispatcher("/form_post.jsp");
			rd.forward(req, resp);
			break;
		} 
		case "/facebook/post/form": {

			loadUser(req);

			RequestDispatcher rd = req.getRequestDispatcher("/form_post.jsp");
			rd.forward(req, resp);
			break;
		}
		case "/facebook/post/delete": {
			
			deletePost(req);
			
			resp.sendRedirect("/facebook/posts");
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}

	private void deletePost(HttpServletRequest req) {
	    String postIdString = req.getParameter("postId");
	    
	    if (postIdString == null || postIdString.isEmpty()) {
	        req.getSession().setAttribute("error", "ID do post não fornecido.");
	        return;
	    }

	    try {
	        int postId = Integer.parseInt(postIdString);
	        Post post = new Post(postId);
	        PostDAO dao = DAOFactory.createDAO(PostDAO.class);
	        
	        if (dao.delete(post)) {
	            req.getSession().setAttribute("msg", "Post excluído com sucesso!");
	        } else {
	            req.getSession().setAttribute("error", "Falha ao excluir o post.");
	        }
	    } catch (NumberFormatException e) {
	        req.getSession().setAttribute("error", "ID inválido.");
	    } catch (ModelException e) {
	        req.getSession().setAttribute("error", "Erro ao excluir post: " + e.getMessage());
	    }
	}
	private void updatePost(HttpServletRequest req) {
		Post post = createPost(req);

		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		try {
			dao.update(post);
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
		}
	}

	private Post createPost(HttpServletRequest req) {
		String postId = req.getParameter("post_id");
		String postContent = req.getParameter("post_content");
		
		String dateStr = req.getParameter("post_date");
		Date postDate = null;
		try {
		    postDate = (Date) new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
		} catch (Exception e) {
		    throw new RuntimeException("Data inválida", e);
		}

		int postUserId = Integer.parseInt(req.getParameter("postuser_id"));
		User user = new User(postUserId); // construtor que recebe só o id
		

		Post post;
		if (postId.equals(""))
			post = new Post();
		else post = new Post(Integer.parseInt(postId));
		
		post.setContent(postContent);
		post.setPostDate(postDate);
		post.setUser(user);
		
		return post;
	}

	private void loadPost(HttpServletRequest req) {
	    String postIdParam = req.getParameter("postId");
	    if (postIdParam == null) return; 

	    int postId = Integer.parseInt(postIdParam);
	    PostDAO postDao = DAOFactory.createDAO(PostDAO.class);
	    UserDAO userDao = DAOFactory.createDAO(UserDAO.class);

	    try {
	        Post post = postDao.findById(postId);
	        if (post == null) throw new ModelException("Post não encontrado");
	        
	        List<User> users = userDao.listAll();

	        req.setAttribute("post", post);
	        req.setAttribute("users", users);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private void loadUser(HttpServletRequest req) {
	    
	    UserDAO userDao = DAOFactory.createDAO(UserDAO.class);

	    try {
	        
	        List<User> users = userDao.listAll();

	        req.setAttribute("users", users);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	private void insertPost(HttpServletRequest req) {
		Post post = createPost(req);

		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		try {
			dao.save(post);
		} catch (ModelException e) {
			// log no servidor
			e.printStackTrace();
		}
	}

	private void loadPosts(HttpServletRequest req) {
		PostDAO dao = DAOFactory.createDAO(PostDAO.class);

		List<Post> posts = null;
		try {
			posts = dao.listAll();
		} catch (ModelException e) {
			// Log no servidor
			e.printStackTrace();
		}

		if (posts != null)
			req.setAttribute("posts", posts);
	}
	

}
