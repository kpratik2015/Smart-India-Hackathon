# Smart-India-Hackathon

[Prototype demo on YouTube](https://youtu.be/0PDnGr--PF4)

## Mobile App Concept:

	- Login for authorized employees only
	- SQLite tab for overlooking Voltage values updated in realtime with server along with device selection
	- Latest Voltage tab for only viewing the latest voltage readings in case of quick response
	- Maps tab to locate the theft location via the coordinates submitted through Notification or through GSM Module mounted on device
	- Notification tab to display recent notifications in case missed or accidental clear
	
## Hackathon (Raw) Implementation:

The login and notification are based on firebase services. Firebase storage was to be used for real time updations in caught csv file containing voltage values. However, locally stored csv file was parsed due to limitation of time. Google API is used for maps and longitudes and latitudes are read from notification.
