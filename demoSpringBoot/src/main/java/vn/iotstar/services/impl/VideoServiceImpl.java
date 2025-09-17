package vn.iotstar.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import vn.iotstar.entity.Video;
import vn.iotstar.repository.VideoRepository;
import vn.iotstar.services.VideoService;
@Service
public class VideoServiceImpl implements VideoService {
@Autowired
private VideoRepository videoRepository;

public VideoServiceImpl(VideoRepository videoRepository) {
	this.videoRepository = videoRepository;
}

@Override
public Page<Video> findByTitleContainingIgnoreCase(String title, Pageable pageable) {
	return videoRepository.findByTitleContainingIgnoreCase(title, pageable);
}

@Override
public List<Video> findAll(Sort sort) {
	return videoRepository.findAll(sort);
}

@Override
public <S extends Video> S save(S entity) {
	return videoRepository.save(entity);
}

@Override
public Page<Video> findAll(Pageable pageable) {
	return videoRepository.findAll(pageable);
}

@Override
public List<Video> findAll() {
	return videoRepository.findAll();
}

@Override
public List<Video> findAllById(Iterable<String> ids) {
	return videoRepository.findAllById(ids);
}

@Override
public Optional<Video> findById(String id) {
	return videoRepository.findById(id);
}

@Override
public long count() {
	return videoRepository.count();
}

@Override
public void deleteById(String id) {
	videoRepository.deleteById(id);
}
@Override
public Page<Video> search(String title, Pageable pageable) {
    if (title == null || title.isBlank()) {
        return videoRepository.findAll(pageable);
    }
    return videoRepository.findByTitleContainingIgnoreCase(title, pageable);
}

@Override
public void delete(Video entity) {
	videoRepository.delete(entity);
}

@Override
public void deleteAll() {
	videoRepository.deleteAll();
}

}
