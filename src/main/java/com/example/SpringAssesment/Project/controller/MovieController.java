package com.example.SpringAssesment.Project.controller;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.SpringAssesment.Project.model.Movie;
import com.example.SpringAssesment.Project.repository.MovieRepository;
import com.example.SpringAssesment.Project.service.MovieService;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

@RestController
public class MovieController {

	@Autowired
	private MovieService movieService;

	@RequestMapping(value = "/AllMovies", method = RequestMethod.GET)
	public List<Movie> getAllMovies() {
		return movieService.getAllMovies();
	}

	@GetMapping("/tvshows")
	public List<Movie> getTvShows(@RequestParam(required=false) Integer count, @RequestParam(required=false) String country, @RequestParam(required=false) String movieType, @RequestParam(required=false) String startDate, @RequestParam(required=false) String endDate) {
		if(count!= null) {
			return movieService.getTvShows(count);
		}
		else if(country!=null) {
			return movieService.getTvShowsByCountry(country);
		}
		else if(movieType!=null){
			return movieService.getTvShowsByMovieType(movieType);
		}
		else if(startDate!=null && endDate!=null){
			return movieService.getTvShowsByDate(startDate,endDate);
		}
		else {
			return movieService.getAllMovies();
		}
	}
	
	@Autowired
	MovieRepository service;
	
	@PostMapping("/upload")
	public String uploadedData(@RequestParam("file") MultipartFile file) throws Exception {
		List<Movie> movies = new ArrayList<>();
		InputStream inputStreams = file.getInputStream();
		CsvParserSettings setting = new CsvParserSettings();
		setting.setHeaderExtractionEnabled(true);
		CsvParser parser=new CsvParser(setting);
		List<Record> parseAllRecords=parser.parseAllRecords(inputStreams);
		parseAllRecords.forEach(record -> {
			Movie row=new Movie();
			row.setShowId(record.getString("Show_id"));
			row.setType(record.getString("type"));
			row.setTitle(record.getString("title"));
			row.setDirector(record.getString("director"));
			row.setCast(record.getString("cast"));
			row.setCountry(record.getString("country"));
			row.setDateAdded(record.getString ("date_added"));
			row.setReleaseYear(Integer.parseInt(record.getString("release_year")));
			row.setRating(record.getString("rating"));
			row.setDuration(record.getString("duration"));
			row.setListedIn(record.getString("listed_in"));
			row.setDescription(record.getString("description"));
			movies.add(row);
		});
		service.saveAll(movies);
		return "Uploaded Sucessfully";
	}
}
