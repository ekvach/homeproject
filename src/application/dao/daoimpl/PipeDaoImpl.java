package application.dao.daoimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.dao.interfaces.PipeDao;
import application.entities.Pipe;
import application.hibernateconfig.HibernateConnectionConfig;
import application.utilclasses.Point;

public class PipeDaoImpl implements PipeDao {

	private static final Logger logger = LoggerFactory.getLogger(PipeDaoImpl.class);

	public PipeDaoImpl() {
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public List<Pipe> getAllPipesAsList() {
		List<Pipe> pipeImplList = null;

		try (Session session = HibernateConnectionConfig.getSessionFactory().getCurrentSession()) {
			Transaction transaction = session.beginTransaction();
			pipeImplList = (List<Pipe>) session.createQuery("FROM PIPE").list();
		} catch (Exception e) {
			logger.error("something went wrong upon global search", e);
			throw new IllegalArgumentException("something went wrong upon global search", e);
		}
		return pipeImplList;
	}

	@Override
	public void create(Pipe pipe) {

		assignIdIfNull(pipe);
		
		Transaction transaction = null;

		try (Session session = HibernateConnectionConfig.getSessionFactory().getCurrentSession()) {

			transaction = session.beginTransaction();
			session.save(pipe);
			transaction.commit();

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error("something went wrong with creating", e);
			throw new IllegalArgumentException("something went wrong upon creating", e);
		}
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public Integer[] getArrayOfUniqueStartPoints() {

		try (Session session = HibernateConnectionConfig.getSessionFactory().getCurrentSession()) {
			Transaction transaction = session.beginTransaction();

			List<Integer> uniqueStartPoints = (List<Integer>) session
					.createQuery("select distinct startPoint FROM PIPE").list();

			Object[] tempArray = uniqueStartPoints.toArray();
			Integer[] uniqueStartPointsArray = createIntArrayFrom(tempArray);

			return uniqueStartPointsArray;

		} catch (Exception e) {
			logger.error("something went wrong upon getArrayOfUniqueStartPoints method calling", e);
			throw new IllegalArgumentException("something went wrong upon getArrayOfUniqueStartPoints method calling",
					e);
		}
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public Integer[] getArrayOfUniqueEndPoints() {

		try (Session session = HibernateConnectionConfig.getSessionFactory().getCurrentSession()) {
			Transaction transaction = session.beginTransaction();

			List<Integer> uniqueEndPointList = (List<Integer>) session.createQuery("select distinct endPoint FROM PIPE")
					.list();

			Object[] tempArray = uniqueEndPointList.toArray();
			Integer[] uniqueEndPointArray = createIntArrayFrom(tempArray);

			return uniqueEndPointArray;

		} catch (Exception e) {
			logger.error("something went wrong upon getArrayOfUniqueEndPoints method calling", e);
			throw new IllegalArgumentException("something went wrong upon getArrayOfUniqueEndPoints method calling", e);
		}
	}

	@Override
	public Set<Point> getAllPointsAsSet() {

		Set<Point> pointSet = new HashSet<>();
		Set<Integer> tempSet = new HashSet<>();

		Integer[] startPoints = getArrayOfUniqueStartPoints();
		Integer[] endPoints = getArrayOfUniqueEndPoints();

		tempSet.addAll(Arrays.asList(startPoints.clone()));
		tempSet.addAll(Arrays.asList(endPoints.clone()));

		for (int pointName : tempSet) {

			Point point = new Point();
			point.setPointName(pointName);

			pointSet.add(point);
		}

		return pointSet;
	}

	@Override
	public List<Point> getAllUniquePointsAsList() {

		return new ArrayList<>(getAllPointsAsSet());
	}

	@Override
	public List<Pipe> getAllConnectedPipesTo(int point) {
		List<Pipe> pipes = getAllPipesAsList();
		List<Pipe> connectedPipesToPoint = new ArrayList<>();
		for (Pipe pipe : pipes) {
			if (pipe.getStartPoint() == point) {
				connectedPipesToPoint.add(pipe);
			}
			//
			if (pipe.getEndPoint() == point) {
				connectedPipesToPoint.add(pipe);
			}
			//
		}
		return connectedPipesToPoint;
	}

	private void assignIdIfNull(Pipe pipe) {

		if (pipe.getId() == null) {

			try (Session session = HibernateConnectionConfig.getSessionFactory().getCurrentSession()) {
				@SuppressWarnings("unused")
				Transaction transaction = session.beginTransaction();

				Long maxId = (Long) session.createQuery("select max(id) from PIPE").uniqueResult();

				if (maxId == null) {
					maxId = 0L;
				}

				pipe.setId(maxId + 1L);

			} catch (Exception e) {
				logger.error("something went wrong upon Id assigning", e);
				throw new IllegalArgumentException("something went wrong upon Id assigning", e);
			}
		}
	}

	private Integer[] createIntArrayFrom(Object[] o) {

		Integer[] result = new Integer[o.length];

		for (int j = 0; j < o.length; j++) {
			result[j] = (int) o[j];
		}

		return result;
	}
}
