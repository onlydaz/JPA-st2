package controllers.admin;

import entity.Category;
import entity.Video;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import services.CategoryService;
import services.ICategoryService;
import services.IVideoService;
import services.VideoService;
import utils.Constant;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(urlPatterns = { "/admin/videos", "/admin/video/edit", "/admin/video/update", "/admin/video/insert",
		"/admin/video/add", "/admin/video/delete" })
public class VideoController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private IVideoService videoService = new VideoService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	    req.setCharacterEncoding("UTF-8");
	    resp.setCharacterEncoding("UTF-8");
	    String url = req.getRequestURI();

	    if (url.endsWith("/videos") || url.endsWith("/add")) {
	        // Lấy danh sách video
	        List<Video> listVideos = videoService.findAll();
	        req.setAttribute("listVideos", listVideos);

	        // Lấy danh sách danh mục
	        ICategoryService categoryService = new CategoryService();
	        List<Category> listCategories = categoryService.findAll();
	        req.setAttribute("listCategories", listCategories);

	        req.getRequestDispatcher("/views/admin/video-add-list.jsp").forward(req, resp);
	    } else if (url.endsWith("/edit")) {
	        String videoId = req.getParameter("id");
	        Video video = videoService.findById(videoId);
	        req.setAttribute("video", video);

	        // Lấy danh sách danh mục
	        ICategoryService categoryService = new CategoryService();
	        List<Category> listCategories = categoryService.findAll();
	        req.setAttribute("listCategories", listCategories);

	        req.getRequestDispatcher("/views/admin/video-edit.jsp").forward(req, resp);
	    } else if (url.endsWith("/delete")) {
	        String videoId = req.getParameter("id");
	        try {
	            videoService.delete(videoId); // Xóa video
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        resp.sendRedirect(req.getContextPath() + "/admin/videos");
	    }
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String url = req.getRequestURI();

		if (url.endsWith("/update")) {
			updateVideo(req, resp);
		} else if (url.endsWith("/insert")) {
			insertVideo(req, resp);
		}
	}

	private void updateVideo(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String videoId = req.getParameter("videoId");
		String title = req.getParameter("title");
		int views = Integer.parseInt(req.getParameter("views"));
		String description = req.getParameter("description");
		int status = Integer.parseInt(req.getParameter("status"));
		String categoryId = req.getParameter("categoryId");
		
		Video video = new Video();
		video.setVideoId(videoId);
		video.setTitle(title);
		video.setViews(views);
		video.setDescription(description);
		video.setActive(status);
		// Set the category for the video
	    ICategoryService categoryService = new CategoryService();
	    Category category = categoryService.findById(Integer.parseInt(categoryId));
	    video.setCategory(category);
	    
		// Lưu hình cũ
		Video oldVideo = videoService.findById(videoId);
		String oldPoster = oldVideo.getPoster();
		String fileName = "";
		String uploadPath = Constant.UPLOAD_DIRECTORY;
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			Part part = req.getPart("poster");
			if (part.getSize() > 0) {
				String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
				String ext = filename.substring(filename.lastIndexOf("."));
				fileName = System.currentTimeMillis() + ext;
				part.write(uploadPath + "/" + fileName);
				video.setPoster(fileName);
			} else {
				video.setPoster(oldPoster);
			}
		} catch (Exception e) {
			e.printStackTrace();
			video.setPoster(oldPoster);
		}

		videoService.update(video);
		resp.sendRedirect(req.getContextPath() + "/admin/videos");
	}

	private void insertVideo(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		Video video = new Video();
		String videoId = req.getParameter("videoId");
		String title = req.getParameter("title");
		int views = Integer.parseInt(req.getParameter("views"));
		String description = req.getParameter("description");
		int status = Integer.parseInt(req.getParameter("status"));
		String categoryId = req.getParameter("categoryId");
		
		video.setVideoId(videoId);
		video.setTitle(title);
		video.setViews(views);
		video.setDescription(description);
		video.setActive(status);
		
	    ICategoryService categoryService = new CategoryService();
	    Category category = categoryService.findById(Integer.parseInt(categoryId));
	    video.setCategory(category);

		String fileName = "";
		String uploadPath = Constant.UPLOAD_DIRECTORY;
		File uploadDir = new File(uploadPath);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}

		try {
			Part part = req.getPart("poster");
			if (part.getSize() > 0) {
				String filename = Paths.get(part.getSubmittedFileName()).getFileName().toString();
				String ext = filename.substring(filename.lastIndexOf("."));
				fileName = System.currentTimeMillis() + ext;
				part.write(uploadPath + "/" + fileName);
				video.setPoster(fileName);
			} else {
				video.setPoster("default-poster.png");
			}
		} catch (Exception e) {
			e.printStackTrace();
			video.setPoster("default-poster.png");
		}

		videoService.insert(video);
		resp.sendRedirect(req.getContextPath() + "/admin/videos");
	}
}