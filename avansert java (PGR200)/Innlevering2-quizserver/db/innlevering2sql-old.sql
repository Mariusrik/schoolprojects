PRAGMA foreign_keys = ON;
  
  --  === TABLES  === 
  
   -- Relation tables.
  DROP TABLE IF EXISTS band_member;
  DROP TABLE IF EXISTS band_album;
  
  DROP TABLE IF EXISTS album_track;
  DROP TABLE IF EXISTS album;
  DROP TABLE IF EXISTS band;
  DROP TABLE IF EXISTS artist;
  DROP TABLE IF EXISTS band_role;
  
  CREATE TABLE artist (
  	id INTEGER PRIMARY KEY,
  	stage_name TEXT,
  	first_name TEXT,
  	last_name TEXT
  );

 
  CREATE TABLE band (
  	id INTEGER PRIMARY KEY,
  	name TEXT NOT NULL
  );
  
  
  CREATE TABLE album (
  	id INTEGER PRIMARY KEY,
  	name TEXT NOT NULL,
  	released DATETIME NOT NULL
  );
  
  -- Describes the roles a member of a band can have.
  CREATE TABLE band_role (
  	id INTEGER PRIMARY KEY,
  	name TEXT NOT NULL UNIQUE
  );
  
  
  
  CREATE TABLE album_track (
  	id INTEGER PRIMARY KEY,
  	album_id INTEGER NOT NULL,
  	track_number INTEGER NOT NULL,
  	name TEXT NOT NULL,
  	FOREIGN KEY(album_id) REFERENCES album(id),
  	UNIQUE(album_id, track_number)
  );
  

  CREATE TABLE band_member (
 	artist_id INTEGER NOT NULL,
 	band_id INTEGER NOT NULL,
 	band_role_id INTEGER NOT NULL,
 	FOREIGN KEY(artist_id) REFERENCES artist(id),
 	FOREIGN KEY(band_id) REFERENCES band(id),
 	FOREIGN KEY(band_role_id) REFERENCES band_role(id),
 	PRIMARY KEY(artist_id, band_id, band_role_id)
 );
 
 -- Many-to-many table between bands and albums.
 CREATE TABLE band_album (
 	album_id INTEGER NOT NULL,
 	band_id INTEGER NOT NULL,
 	FOREIGN KEY(album_id) REFERENCES album(id),
 	FOREIGN KEY(band_id) REFERENCES band(id),
 	PRIMARY KEY(album_id, band_id)
 );
 
 --  === VIEWS === 
 
 DROP VIEW IF EXISTS album_release_year;
 DROP VIEW IF EXISTS band_is_solo;
 DROP VIEW IF EXISTS band_num_members;
 DROP VIEW IF EXISTS band_members_unique;
 DROP VIEW IF EXISTS album_band_view;
 
 
 -- This is an ease of use view, giving a deduplicated view of the band members
 -- in a band.
 CREATE VIEW band_members_unique (
 	band_id,
 	artist_id,
 	num_roles
 )
 AS
 	SELECT 
 		band_id, 
 		artist_id, 
 		count(*) AS num_roles
 	FROM
 		band_member
 	GROUP BY
 		band_id, artist_id;
 
 -- Ease of use view, showing number of members in a band.
 CREATE VIEW band_num_members (
 	band_id,
 	num_members
 )
 AS
 	SELECT
 		band_id,
 		count(*) AS num_members
 	FROM
 		band_members_unique
 	GROUP BY
 		band_id;
 
 -- Ease of use view, showing if a band is really a one man artist.
 CREATE VIEW band_is_solo (
 	band_id,
	solo
 )
 AS
 	SELECT
 		band_id,
 		num_members = 1 AS solo
 	FROM
 		band_num_members;
 
 -- Ease of use view, showing info about the release of an album.
 CREATE VIEW album_release_year (
 	album_id,
 	year
 )
 AS
 	SELECT
 		id as album_id,
 		strftime('%Y', released) AS year
 	FROM
 		album;
		
		
CREATE VIEW album_band_view 
AS 
	SELECT
		a.id as a_id,
		a.name AS a_name,
		b.id as b_id,
		b.name AS b_name
	FROM
		album a
		INNER JOIN band_album ba ON (a.id =  ba.album_id)
INNER JOIN band b ON (ba.band_id = b.id);



		
INSERT INTO band (id, name) VALUES (1, 'Metallica');

INSERT INTO artist (id, stage_name, first_name, last_name) VALUES (1, 'James Hetfield', 'James', 'Hetfield'), (2, 'Lars Ullrich', 'Lars', 'Ullrich'), (3, 'Kirk Hammett', 'Kirk', 'Hammett'), (4, 'Robert Trujillo', 'Robert', 'Truilljo'), (5, 'Cliff Burton', 'Cliff', 'Burton'), (6, 'Dave Mustain', 'Dave', 'Mustain');

INSERT INTO band_role (id, name) VALUES (1, 'Gitar'), (2, 'Trommer'), (3, 'Vokalist'), (4, 'Piano'), (5, 'Bass');

INSERT INTO album (id, name, released) VALUES (1, 'Kill em all', strftime('%J', '1983-07-25'));

INSERT INTO band_album (album_id, band_id) VALUES (1, 1);

INSERT INTO band_member (artist_id, band_id, band_role_id) VALUES (1, 1, 3), ( 2, 1, 2), ( 3, 1, 1), ( 4, 1, 5), ( 5, 1, 5), (6,1 , 1);

INSERT INTO album_track (id, album_id, track_number, name) VALUES ( 1, 1, 1, 'Hit the lights'), ( 2, 1, 2, 'The Four Horsemen'), ( 3, 1, 3, 'Motorbreath'), ( 4, 1, 4, 'Jump in the Fire'), ( 5, 1, 5, 'Pulling Theeth'), ( 6, 1, 6, 'Whiplash'), ( 7, 1, 7, 'Phantom Lord'), ( 8, 1, 8, 'No Remorse');