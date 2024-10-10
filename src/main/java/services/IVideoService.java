package services;

import entity.Video;

import java.util.List;

public interface IVideoService {
	void insert(Video video);

	void update(Video video);

	void delete(String videoId) throws Exception;

	Video findById(String videoId);

	List<Video> findAll();

	int count();
}
