DROP SEQUENCE IF EXISTS conference_seq;
CREATE SEQUENCE conference_seq START 5;

DROP SEQUENCE IF EXISTS rooms_seq;
CREATE SEQUENCE rooms_seq START 6;

DROP SEQUENCE IF EXISTS users_seq;
CREATE SEQUENCE users_seq START 6;

INSERT INTO users(user_id,dob, first_name, last_name, gender) VALUES
(1,'1990-02-01', 'Sank', 'Gaut', 'MALE'),
(2,'1991-08-02', 'Sal', 'Rup', 'FEMALE'),
(4,'1985-01-01', 'Jane', 'Mary', 'FEMALE'),
(5,'1986-01-01', 'Lionel', 'Messi', 'MALE'),
(3,'2004-01-01', 'Megan', 'Fox', 'FEMALE');

INSERT INTO rooms(room_id,name, location, max_capacity, user_id, status) VALUES
 (1,'Annapurna', 'Manang', 10, 5, 'AVAILABLE'),
 (2, 'Everest', 'Sagarmatha', 4, 4, 'AVAILABLE'),
 (3,'Kanguru', 'Manang', 40, 3, 'AVAILABLE'),
(4,'Kyanjin Ri', 'Rasuwa', 10, 2, 'AVAILABLE'),
(5,'Gosainkunda', 'Rasuwa', 17, 1, 'AVAILABLE');

INSERT INTO conference(conference_id, start_date_time, end_date_time, room_id, description, name) VALUES
(1, '2024-10-26T15:46:24+03:00', '2024-10-26T16:46:24+03:00',3, 'Pizza discussion', 'Pizza'),
(2, '2024-10-26T17:46:24+03:00', '2024-10-26T18:46:24+03:00',3, 'Story Sizing', 'Sprint Planning'),
(3,'2024-10-26T19:46:24+03:00', '2024-10-26T20:46:24+03:00', 5, 'Making alternative trek routes available for tourists', 'Alternate trekking routes'),
(4,'2024-10-26T21:46:24+03:00', '2024-10-26T22:46:24+03:00', 4, 'Yearly financial report discussion', 'Yearly financial report');