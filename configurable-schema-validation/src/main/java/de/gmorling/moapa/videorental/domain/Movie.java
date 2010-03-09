/**
 *  Copyright 2010 Gunnar Morling (http://www.gunnarmorling.de/)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
