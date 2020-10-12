package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataConverter;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {

	private GPSPoint[] gpspoints;

	public GPSComputer(String filename) {

		GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
		gpspoints = gpsdata.getGPSPoints();

	}

	public GPSComputer(GPSPoint[] gpspoints) {
		this.gpspoints = gpspoints;
	}

	public GPSPoint[] getGPSPoints() {
		return this.gpspoints;
	}

	// beregn total distances (i meter)
	public double totalDistance() {

		double distance = 0;

		// TODO - START
		int i = 0;
		while (i < gpspoints.length - 1) {
			GPSPoint gpspoint1 = gpspoints[i];
			GPSPoint gpspoint2 = gpspoints[i + 1];
			distance = GPSUtils.distance(gpspoint1, gpspoint2) + distance;
			i++;
		}
		return distance;
		// TODO - SLUTT

	}

	// beregn totale høydemeter (i meter)
	public double totalElevation() {

		double elevation = 0;

		// TODO - START
		int i = 0;
		double dif = 0;
		while (i < gpspoints.length - 1) {
			GPSPoint gpspoint1 = gpspoints[i];
			GPSPoint gpspoint2 = gpspoints[i + 1];
			if (gpspoint1.getElevation() < gpspoint2.getElevation()) {
				dif = gpspoint1.getElevation() - gpspoint2.getElevation();
				elevation += dif;
			}
			i++;
		}
		return elevation * -1;

		// TODO - SLUTT

	}

	// beregn total tiden for hele turen (i sekunder)
	public int totalTime() {
		int tid = 0;
		int i = 0;
		int dif = 0;
		while (i < gpspoints.length - 1) {
			GPSPoint gpspoint1 = gpspoints[i];
			GPSPoint gpspoint2 = gpspoints[i + 1];
			dif = gpspoint1.getTime() - gpspoint2.getTime();
			i++;
			tid += dif;
		}
		return tid * -1;
	}

	// beregn gjennomsnitshastighets mellom hver av gps punktene

	public double[] speeds() {

		// TODO - START // OPPGAVE - START
		double[] speed = new double[getGPSPoints().length - 1];
		for (int i = 0; i < getGPSPoints().length - 1; i++) {
			speed[i] = GPSUtils.speed(gpspoints[i], gpspoints[i + 1]);
		}
		return speed;
		// TODO - SLUTT

	}

	public double maxSpeed() {

		double maxspeed = 0;

		// TODO - START
		maxspeed = GPSUtils.findMax(speeds());
		return maxspeed;
		// TODO - SLUTT

	}

	public double averageSpeed() {

		double average = 0;

		// TODO - START
		average = totalDistance()/totalTime();
		average = average *3600/1000;
		return average;
		// TODO - SLUTT

	}

	/*
	 * bicycling, <10 mph, leisure, to work or for pleasure 4.0 bicycling, general
	 * 8.0 bicycling, 10-11.9 mph, leisure, slow, light effort 6.0 bicycling,
	 * 12-13.9 mph, leisure, moderate effort 8.0 bicycling, 14-15.9 mph, racing or
	 * leisure, fast, vigorous effort 10.0 bicycling, 16-19 mph, racing/not drafting
	 * or >19 mph drafting, very fast, racing general 12.0 bicycling, >20 mph,
	 * racing, not drafting 16.0
	 */

	// conversion factor m/s to miles per hour
	public static double MS = 2.236936;

	// beregn kcal gitt weight og tid der kjøres med en gitt hastighet
	public double kcal(double weight, int secs, double speed) {

		double kcal;

		// MET: Metabolic equivalent of task angir (kcal x kg-1 x h-1)
		double met = 0;
		double speedmph = speed * MS;

		// TODO - START
		if (speedmph < 10) {
			met = 4;
		} else if (speedmph > 10 && speedmph < 12) {
			met = 6;
		} else if (speedmph > 12 && speedmph < 14) {
			met = 8;
		} else if (speedmph > 14 && speedmph < 16) {
			met = 10;
		} else if (speedmph > 16 && speedmph < 20) {
			met = 12;
		} else {
			met = 16;
		}
		kcal = met * weight * secs / 3600;

		return kcal;
		// TODO - SLUTT

	}

	public double totalKcal(double weight) {

		double totalkcal = 0;

		// TODO - START

		for (int i = 0; i < gpspoints.length - 1; i++) {
			totalkcal += kcal(WEIGHT, gpspoints[i + 1].getTime() - gpspoints[i].getTime(),
					GPSUtils.speed(gpspoints[i], gpspoints[i + 1]));
		}
		return totalkcal;
		// TODO - SLUTT

	}

	private static double WEIGHT = 80.0;

	public void displayStatistics() {

		System.out.println("==============================================");

		// TODO - START

		System.out.println("Total Time     : " + GPSUtils.formatTime(totalTime()));
		System.out.println("Total Distance :      " + Math.round(totalDistance()/10.0)/100.0 + " km");
		System.out.println("Total elevation:     " + Math.round(totalElevation()*100.00)/100.00 + "  m");
		System.out.println("Max Speed      :      " + Math.round(maxSpeed()*100.0)/100.0+" km/t");
		System.out.println("Average Speed  :      " + Math.round(averageSpeed()*100.0)/100.0+" km/t");
		System.out.println("Energy         :     " + Math.round(totalKcal(WEIGHT))*1000/1000.0+"  kcal");

		// TODO - SLUTT

	}

}
