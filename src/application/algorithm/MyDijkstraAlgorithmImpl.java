package application.algorithm;

import java.util.Set;

import application.dao.daoimpl.PipeDaoImpl;
import application.dao.interfaces.PipeDao;
import application.utilclasses.Point;

public class MyDijkstraAlgorithmImpl {
	private Set<Point> points;
	private int endPoint;
	private PipeDao pipeDao;
	private Point currentPoint;
	private Point neighborOfCurrentPoint;

	public MyDijkstraAlgorithmImpl() {
		this.setPipeDao(new PipeDaoImpl());
		this.initializePoints();
	}

	public String getResultForPoints(int startPoint, int endPoint) {
		validatePoints(startPoint, endPoint);
		this.endPoint = endPoint;

		currentPoint = getPointFromListByName(startPoint);
		currentPoint.setDistanceFromSource(0);

		for (int i = 0; i < points.size(); i++) {
			getDistanceToTheNeighbors();
			currentPoint.setVisited(true);
			currentPoint = getNotVisitedNeighbor();

		}

		String result = getResultLine();
		return result;
	}

	private void getDistanceToTheNeighbors() {

		inputDataNullChecker(currentPoint);

		for (int i = 0; i < currentPoint.getPipes().size(); i++) {

			int neighborOfCurrentPointName = currentPoint.getPipes().get(i).getEndPoint();
			neighborOfCurrentPoint = getPointFromListByName(neighborOfCurrentPointName);

			int currentDistanceToTheNeighbor = currentPoint.getPipes().get(i).getLength();
			relaxDistanceIfNeeded(currentDistanceToTheNeighbor);

		}
	}

	private Point getNotVisitedNeighbor() {
		Point point = null;
		int min = Integer.MAX_VALUE;
		for (Point p : points) {
			if (!p.isVisited() && p.getDistanceFromSource() <= min) {
				min = p.getDistanceFromSource();
				point = p;
			}
		}
		return point;
	}

	private Point getPointFromListByName(int pointName) {
		inputDataNullChecker(pointName);
		Point point = null;
		for (Point p : points) {
			if (p.getPointName() == pointName) {
				point = p;
			}
		}
		return point;
	}

	private void relaxDistanceIfNeeded(int length) {
		inputDataNullChecker(length);

		int calculatedDistance = currentPoint.getDistanceFromSource() + length;

		if (calculatedDistance < neighborOfCurrentPoint.getDistanceFromSource()) {
			neighborOfCurrentPoint.setDistanceFromSource(calculatedDistance);
		}
	}

	private String getResultLine() {
		String result = null;

		Point point = getPointFromListByName(endPoint);

		result = checkRouteTo(point);

		return result;
	}

	private String checkRouteTo(Point point) {
		if (point.getDistanceFromSource() == Integer.MAX_VALUE / 2) {
			return "FALSE;";
		} else {
			return "TRUE;" + point.getDistanceFromSource();
		}

	}

	private void inputDataNullChecker(Object o) {
		if (o == null) {
			Exception e = new NullPointerException();
			e.printStackTrace();
			throw new NullPointerException("input data cannot be null");
		}
	}

	private void initializePoints() {
		this.points = pipeDao.getAllPointsAsSet();

		for (Point point : points) {
			point.initializeConnectedPipes();
		}

	}

	private void validatePoints(int startPoint, int endPoint) {
		if (startPoint == endPoint) {
			throw new IllegalArgumentException("Cannot calculate route for the same point");
		}
		if (startPoint < 0 || endPoint < 0) {
			throw new IllegalArgumentException("one or more points are less than 0");
		}
	}

	public void setPipeDao(PipeDao pipeDao) {
		this.pipeDao = pipeDao;
	}
}
