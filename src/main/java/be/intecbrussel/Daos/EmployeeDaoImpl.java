package be.intecbrussel.Daos;

import be.intecbrussel.Entities.Employee;
import be.intecbrussel.Utils.EntityManagerFactoryProvider;

import javax.persistence.*;

public class EmployeeDaoImpl implements EmployeeDao {

    private EntityManagerFactory emf = EntityManagerFactoryProvider.getInstance().getEmf();
    private EntityManager em = null;

    @Override
    public void createEmployee(Employee employee) {

        try {
            em = emf.createEntityManager();
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.persist(employee);
            tx.commit();
            System.out.println("Employee saved");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (em != null){
                em.close();
            }
        }
    }

    @Override
    public Employee readEmployee(String employeeLastName) {
        Employee employee = null;
        try{
            em = emf.createEntityManager();
            TypedQuery<Employee> query = em.createQuery("SELECT b from Employee b where b.lastName =?1", Employee.class);
            query.setParameter(1, employeeLastName);
            employee = query.getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (em != null){
                em.close();
            }
        }
        return employee;
    }

    @Override
    public void updateEmployee(Employee employee) {
        try{
            em = emf.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            em.merge(employee);
            transaction.commit();
            System.out.println("employee updated");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (em != null){
                em.close();
            }
        }
    }

    @Override
    public void deleteEmployee(Employee employee) {
        try{
            em = emf.createEntityManager();
            EntityTransaction transaction = em.getTransaction();
            Employee empl = em.find(Employee.class, employee.getEmployeeNumber());
            transaction.begin();
            em.remove(empl);
            transaction.commit();
            System.out.println("employee removed");
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
