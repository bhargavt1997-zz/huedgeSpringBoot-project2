package com.example.SpringAssesment.Project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.SpringAssesment.Project.model.Movie;

@Repository
public interface MovieRepository extends CrudRepository<Movie, String>{
	
	@Query("select m from Movie m where m.type=:type")
	List<Movie> findByType(@Param("type") String type);

	List<Movie> findByTypeAndCountry(String type, String country);

	@Query("select m from Movie m where m.listedIn like %:movieType% and m.type=:type")
	List<Movie> findByTypeAndMovieType(@Param("type") String type, @Param("movieType") String movieType);
	
	@Query("select m from Movie m where m.type=:type and m.dateAdded<=:endDate and m.dateAdded>=:startDate")
	List<Movie> findUsingDate(@Param("type") String type, @Param("startDate") String startDate, @Param("endDate") String endDate);

}
