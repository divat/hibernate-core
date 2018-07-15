
package de.laliluna.relation.recursive;


import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;

import de.laliluna.hibernate.InitSessionFactory;

/**
 * @author hennebrueder
 *
 */
public class TestRecursive  {
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TestRecursive.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TreeItem subTree = create();
		deleteSubTree(subTree);

		//cleanup();
	}

	private static void cleanup() {
		log.debug("cleanup");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		session.createQuery("delete from TreeItem").executeUpdate();
		session.getTransaction().commit();
	}

	private static TreeItem create() {
		log.debug("create");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		TreeItem main = new TreeItem(null, "main");
		TreeItem sub1 = new TreeItem(null, "go swimming");
		sub1.setParent(main);
		main.getChildren().add(sub1);
		
		TreeItem sub2 =new TreeItem(null, "eat hot dog");
		sub2.setParent(main);
		main.getChildren().add(sub2);
		TreeItem sub11 = new TreeItem(null, "lake");
		sub11.setParent(sub1);
		sub1.getChildren().add(sub11);
		TreeItem sub12 = new TreeItem(null, "swimming pool");
		sub12.setParent(sub1);
		sub1.getChildren().add(sub12);
		TreeItem sub21 = new TreeItem(null, "with chips");
		sub21.setParent(sub2);
		sub2.getChildren().add(sub21);
		TreeItem sub22 = new TreeItem(null, "without chips");
		sub22.setParent(sub2);
		sub2.getChildren().add(sub22);
		
		session.save(main);
		// cascade will save all the children
		
		
		session.getTransaction().commit();
		return sub1;
	}

	private static void deleteSubTree(TreeItem subTree) {
		log.debug("delete subTree");
		Session session = InitSessionFactory.getInstance().getCurrentSession();
		session.beginTransaction();
		// reattach subTree as session could be closed.
		session.buildLockRequest(LockOptions.NONE).lock(subTree);
		log.debug("we will delete: "+subTree);
		/*
		 * we must remove the children from the parent or it will be resaved. when the parent is saved.
		 */
		subTree.getParent().getChildren().remove(subTree);
		session.delete(subTree);
		session.getTransaction().commit();
	}

}
