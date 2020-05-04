package com.customer.hibernate.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.customer.hibernate.beans.Department;
import com.customer.hibernate.beans.Employee;

/**
 * HQLTest
 *
 * @author Zichao Zhang
 * @date 2020/5/2
 */
public class HQLTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init() {
        Configuration configure = new Configuration().configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry =
                new ServiceRegistryBuilder().applySettings(configure.getProperties()).buildServiceRegistry();
        sessionFactory = configure.buildSessionFactory(serviceRegistry);
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void destroy() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    @Test
    public void testSave() {
        Employee emp1 = new Employee("Bob", 1234.5f, "bob@customer.com");
        Employee emp2 = new Employee("Rose", 2345.5f, "rose@customer.com");
        Employee emp3 = new Employee("Jack", 3456.5f, "jack@customer.com");
        Employee emp4 = new Employee("Tom", 4567.5f, "Tom@customer.com");
        Employee emp5 = new Employee("Mary", 5678.5f, "mary@customer.com");
        Employee emp6 = new Employee("Slina", 6789.5f, "slina@customer.com");
        Employee emp7 = new Employee("Eve", 7890.5f, "eve@customer.com");

        Department dept1 = new Department("dev");
        Department dept2 = new Department("test");
        Department dept3 = new Department("prod");

        dept1.getEmployees().add(emp1);
        dept1.getEmployees().add(emp2);
        dept1.getEmployees().add(emp3);

        dept2.getEmployees().add(emp4);
        dept2.getEmployees().add(emp5);

        dept3.getEmployees().add(emp6);
        dept3.getEmployees().add(emp7);

        emp1.setDepartment(dept1);
        emp2.setDepartment(dept1);
        emp3.setDepartment(dept1);
        emp4.setDepartment(dept2);
        emp5.setDepartment(dept2);
        emp6.setDepartment(dept3);
        emp7.setDepartment(dept3);

        session.save(dept1);
        session.save(dept2);
        session.save(dept3);

        session.save(emp1);
        session.save(emp2);
        session.save(emp3);
        session.save(emp4);
        session.save(emp5);
        session.save(emp6);
        session.save(emp7);
    }

    @Test
    public void testHQL() {
        String hql = "from Employee e where e.salary > ? and e.email like ? and e.department = ? order by e.salary";
        Query query = session.createQuery(hql);
        Department dept = new Department();
        dept.setId(2);
        query.setFloat(0, 3000)
                .setString(1, "%a%")
                .setEntity(2, dept);
        List<Employee> list = query.list();
        System.out.println("list = " + list);
    }

    @Test
    public void testHQLNamed() {
        String hql = "from Employee e where e.salary > :sal and e.email like :email";
        Query query = session.createQuery(hql);
        query.setFloat("sal", 3000)
                .setString("email", "%a%");
        List<Employee> list = query.list();
        System.out.println("list = " + list);
    }

    @Test
    public void testHQLPaging() {
        String hql = "from Employee";
        Query query = session.createQuery(hql);

        int pageNo = 3;
        int pageSize = 2;
        List list = query.setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize).list();
        System.out.println("list = " + list);
    }

    @Test
    public void testHQLQueryName() {
        Query empBySalary = session.getNamedQuery("empBySalary");
        List list = empBySalary.setFloat("minSal", 2000).setFloat("maxSal", 7000).list();
        System.out.println("list = " + list);
    }

    @Test
    public void testFieldsQuery() {
        String hql = "select e.email, e.salary, e.department from Employee e where e.department = :dept";
        Department dept = new Department();
        dept.setId(1);
        List<Object[]> objs = session.createQuery(hql).setEntity("dept", dept).list();
        for (Object[] obj : objs) {
            System.out.println(Arrays.asList(obj));
        }
    }

    @Test
    public void testFieldsQuery2() {
        // 可以在 Employee 的构造器中提供 包含指定参数的构造器，可以使用构造器来包装投影映射的结果
        String hql = "select new Employee(e.salary, e.email, e.department) from Employee e where e.department = :dept";
        Department dept = new Department();
        dept.setId(1);
        List<Employee> objs = session.createQuery(hql).setEntity("dept", dept).list();
        for (Employee emp : objs) {
            System.out.println("salary: " + emp.getSalary() + ", email: " + emp.getEmail() + ", dept: " + emp.getDepartment());
        }
    }

    @Test
    public void testHQLFunction() {
        String hql = "select min(e.salary), max(e.salary) from Employee e group by e.department having min(e.salary) " +
                "> " +
                ":minSal";
        List<Object[]> list = session.createQuery(hql).setFloat("minSal", 1000).list();
        for (Object[] objects : list) {
            System.out.println(Arrays.asList(objects));
        }
    }

    @Test
    public void testLeftJoinFetch() {
        String hql = "select distinct d from Department d left join fetch d.employees";
        List<Department> list = session.createQuery(hql).list();
        list = new ArrayList<>(new LinkedHashSet<>(list));
        System.out.println("list.size() = " + list.size());
        for (Department department : list) {
            System.out.println("department.name=" + department.getName() + ",department.getEmployees() = " + department.getEmployees());
        }
    }

    @Test
    public void testLeftJoin() {
        String hql = "select distinct d from Department d left join d.employees";
//        List<Object[]> list = session.createQuery(hql).list();
//        list = new ArrayList<>(new LinkedHashSet<>(list));
//        System.out.println("list.size() = " + list.size());
        List<Department> list = session.createQuery(hql).list();
        for (Department department : list) {
            for (Employee employee : department.getEmployees()) {
                System.out.println(employee.getName());
            }
        }
    }

    @Test
    public void testLeftJoinFetch2() {
        String hql = "from Employee e left join fetch e.department";
        List<Employee> list = session.createQuery(hql).list();
        list = new ArrayList<>(new LinkedHashSet<>(list));
        System.out.println("list.size() = " + list.size());
        for (Employee employee : list) {
            System.out.println("employee.getDepartment().getName() = " + employee.getDepartment().getName());
        }
    }

    @Test
    public void testLeftJoin2() {
        String hql = "from Employee e left join e.department";
//        List<Object[]> list = session.createQuery(hql).list();
        List<Employee> list = session.createQuery(hql).list();
        System.out.println("list.size() = " + list.size());
        for (Employee employee : list) {
            System.out.println("employee.getDepartment().getName() = " + employee.getDepartment().getName());
        }
    }

    @Test
    public void testQBC() {
        Criteria criteria = session.createCriteria(Employee.class);
        Employee employee = (Employee) criteria.add(Restrictions.eq("email", "rose@customer.com"))
                .add(Restrictions.gt("salary", 2000f)).uniqueResult();
        System.out.println("employee = " + employee);
        System.out.println("employee.getDepartment().getId() = " + employee.getDepartment().getName());
    }

    @Test
    public void testQBC2() {
        Criteria criteria = session.createCriteria(Employee.class);
        Conjunction conjunction = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("name", "Rose"));
        conjunction.add(Restrictions.gt("salary", 2000f));
        Employee employee = (Employee) criteria.add(conjunction).uniqueResult();
        System.out.println("employee = " + employee);
    }

    @Test
    public void testQBC3() {
        Criteria criteria = session.createCriteria(Employee.class);
        // Disjunction 可以用来生成 Or 语句
        Disjunction disjunction = Restrictions.disjunction();
        // Conjunction 使用 And 来连接条件语句
        Conjunction conjunction = Restrictions.conjunction();
        Conjunction conjunction1 = Restrictions.conjunction();
        conjunction.add(Restrictions.eq("name", "Jack"))
                .add(Restrictions.ge("salary", 3000f));
        conjunction1.add(Restrictions.eq("name", "Slina"))
                .add(Restrictions.le("salary", 7000f));
        disjunction.add(conjunction).add(conjunction1);
        List<Employee> list = criteria.add(disjunction).list();
        for (Employee employee : list) {
            System.out.println("employee = " + employee);
        }
    }

    @Test
    public void testQBC4() {
        Criteria criteria = session.createCriteria(Employee.class);
        // setProjection 可以设置使用统计函数进行统计查询
        criteria.setProjection(Projections.max("salary"));
        System.out.println("criteria.uniqueResult() = " + criteria.uniqueResult());
    }

    @Test
    public void testQBC5() {
        Criteria criteria = session.createCriteria(Employee.class);
        // addOrder 可以设置排序
        criteria.addOrder(Order.asc("name")).addOrder(Order.desc("salary"));
        int pageNo = 3;
        int pageSize = 2;
        List<Employee> list = criteria.setFirstResult((pageNo - 1) * pageSize).setMaxResults(pageSize).list();
        for (Employee employee : list) {
            System.out.println("employee = " + employee);
        }
    }

    @Test
    public void testNativeSQL() {
        String sql = "insert into tbl_dept values (?, ?)";
        session.createSQLQuery(sql)
                .setInteger(0, 4)
                .setString(1, "Java")
                .executeUpdate();
    }

    @Test
    public void testHQLDelete() {
        String hql = "delete from Department d where d.id = :id";
        session.createQuery(hql)
                .setInteger("id", 4)
                .executeUpdate();
    }
}
