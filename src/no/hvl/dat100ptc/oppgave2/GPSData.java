package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;

	public GPSData(int n) {

		// TODO - START
		antall = 0;

		gpspoints = new GPSPoint[n];

		// TODO - SLUTT
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	protected boolean insertGPS(GPSPoint gpspoint) {

		boolean inserted = false;

		// TODO - START

		if (antall < gpspoints.length) {

			gpspoints[antall] = gpspoint;
			antall++;
			inserted = true;
		}
		if (antall > gpspoints.length) {
			inserted = false;
		}
		return inserted;
		// TODO - SLUTT
	}

	public boolean insert(String time, String latitude, String longitude, String elevation) {

		GPSPoint gpspoint;

		// TODO - START
		int tim = GPSDataConverter.toSeconds(time);
		double lat = Double.parseDouble(latitude);
		double lon = Double.parseDouble(longitude);
		double ele = Double.parseDouble(elevation);
		gpspoint = new GPSPoint(tim, lat, lon, ele); 
		if (antall < gpspoints.length) {
			gpspoint.toString();
		}

		return this.insertGPS(gpspoint);

		// TODO - SLUTT

	}

	public void print() {

		System.out.println("====== Konvertert GPS Data - START ======");

		// TODO - START

		while (antall < gpspoints.length) {
			System.out.println(gpspoints[antall]);
			antall++;
		}

		// TODO - SLUTT

		//System.out.println("====== Konvertert GPS Data - SLUTT ======");

	}
}
