package services;

import dao.impl.IVideoDao;
import dao.impl.VideoDao;
import entity.Video;

import java.util.List;

public class VideoService implements IVideoService {
	private IVideoDao videoDao;

	public VideoService() {
		videoDao = new VideoDao();
	}

	@Override
	public void insert(Video video) {
		videoDao.insert(video);
	}

	@Override
	public void update(Video video) {
		videoDao.update(video);
	}

	@Override
	public void delete(String videoId) throws Exception {
		videoDao.delete(videoId);
	}

	@Override
	public Video findById(String videoId) {
		return videoDao.findById(videoId);
	}

	@Override
	public List<Video> findAll() {
		return videoDao.findAll();
	}

	@Override
	public int count() {
		return videoDao.count();
	}
}
