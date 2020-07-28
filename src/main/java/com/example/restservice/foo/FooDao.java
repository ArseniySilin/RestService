package com.example.restservice.foo;

import java.util.List;
import com.example.restservice.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class FooDao {
  public void saveStudent(Foo foo) {
    Transaction transaction = null;
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      // start a transaction
      transaction = session.beginTransaction();
      // save the student object
      session.save(foo);
      // commit transaction
      transaction.commit();
    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }
  public List<Foo> getFoos() {
    try (Session session = HibernateUtil.getSessionFactory().openSession()) {
      return session.createQuery("from Foo", Foo.class).list();
    }
  }
}
