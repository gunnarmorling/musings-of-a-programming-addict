package de.gmorling.moapa.videorental.domain;

import java.util.Date;

public class Movie {

	private final long id;
	
	private final String title;
	
	private int runTime;
	
	private final String director;
	
	private final Date releaseDate;

	public Movie(long id, String title, int runTime, String director,
			Date releaseDate) {

		this.id = id;
		this.title = title;
		this.runTime = runTime;
		this.director = director;
		this.releaseDate = releaseDate;
	}

	public Movie(long id, String title, int runTime) {

		this.id = id;
		this.title = title;
		this.runTime = runTime;
		this.director = null;
		this.releaseDate = null;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public int getRunTime() {
		return runTime;
	}

	public String getDirector() {
		return director;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}
	
}
