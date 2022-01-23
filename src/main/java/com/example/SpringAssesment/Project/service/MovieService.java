package com.example.SpringAssesment.Project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.SpringAssesment.Project.model.Movie;
import com.example.SpringAssesment.Project.repository.MovieRepository;

@Service
@Transactional
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;

	public List<Movie> getAllMovies() {
		List<Movie> allMovies= (List<Movie>) movieRepository.findAll();
		return allMovies;
	}

	
	public List<Movie> getTvShows(Integer count) {
		List<Movie> tvShows= (List<Movie>) movieRepository.findByType("TV Show");
		List<Movie> countTvShows=new ArrayList();
		for(int i=0;i<count;i++) {
			countTvShows.add(tvShows.get(i));
		}
		return countTvShows;
	}
	
	public List<Movie> getTvShowsByCountry(String country) {
		List<Movie> tvShowsByCountry= (List<Movie>) movieRepository.findByTypeAndCountry("TV Show",country);
		return tvShowsByCountry;
	}
	
	
	public List<Movie> getTvShowsByMovieType(String movieType) {
		List<Movie> tvShowsByMovieType= (List<Movie>) movieRepository.findByTypeAndMovieType("TV Show",movieType);
		return tvShowsByMovieType;
	}


	public List<Movie> getTvShowsByDate(String startDate, String endDate) {
		List<Movie> tvShowsByDate= (List<Movie>) movieRepository.findUsingDate("TV Show",startDate,endDate);
		return tvShowsByDate;
	}
}
