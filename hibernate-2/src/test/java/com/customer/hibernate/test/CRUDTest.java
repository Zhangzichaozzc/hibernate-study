package com.customer.hibernate.test;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.customer.hibernate.beans.News;
import com.customer.hibernate.beans.Pay;
import com.customer.hibernate.beans.Worker;

/**
 * CRUDTest
 *
 * @author Zichao Zhang
 * @date 2020/4/30
 */
public class CRUDTest {
private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init() {
        Configuration configure = new Configuration().configure("hibernate.cfg.xml");
        sessionFactory =
                configure.buildSessionFactory(new ServiceRegistryBuilder().applySettings(configure.getProperties()).buildServiceRegistry());
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void clearUp() {
        transaction.commit();
        session.close();
        sessionFactory.close();
    }

    /**
     * Session 对象中维护类查询出数据的一级缓存，如果Session 的生命周期没有结束且数据库中的数据页没有变化，则Session 中的二级缓存中的数据的生命周期不会结束
     */
    @Test
    public void testSessionCache() {
        News news = (News) session.get(News.class, 1);
        System.out.println("news = " + news);
        News news2 = (News) session.get(News.class, 1);
        System.out.println("news2 = " + news2);
    }

    /**
     * flush: 该方法可以使数据表中的数据与 Session 缓存中的数据保持一致，为了保持一致，则可能会向数据库发送数据更新的SQL
     * 1. 在 Transaction.commit 中，在提交事务之前会先执行 flush 操作，使数据表中的数据与 Session 缓存中的数据保持一致
     * 2. 调用 flush 方发时可能会生成 数据表更新的 SQL 语句， 生成的 SQL 语句暂时不会执行， 只有到 事务提交的时候才会真正的执行
     * 3. 除了显示的调用 flush 或者 调用 commit 方法会外，还有以下几种方式会执行flush  操作
     * 1) 执行 HQL 或者 QBC 查询的时候, 因为只有进行 flush 操作，才能保证在 HQL 和 QBC 查询的数据时最新的
     * 2) 若记录的 ID 是由数据库底层生成的，则在调用 save 方法后，就会立即发送 Insert 语句，因为 save 方法后必须保证 ID 是存在的
     */
    @Test
    public void testSessionCache2() {
        News news = (News) session.get(News.class, 1);
        news.setAuthor("Sun");

        News news1 = (News) session.createCriteria(News.class).uniqueResult();
        System.out.println("news1 = " + news1);
//        session.flush();
//        System.out.println("flush");
    }

    @Test
    public void testSessionFlush() {
        News news = new News("Java", "Oracle", new Date());
        session.save(news);
    }

    /**
     * session.refresh 方法会强制 向数据库发送 select语句，不管缓存中的数据有没有变化，将数据库中的记录信息与 Session 缓存中的信息保持同步
     * Mysql 默认的隔离级别为 Repeatable Read， 所以 refresh 的 Select 语句不会生效，可以在 主配置文件中将 Hibernate 的隔离级别改为 2，即读已提交
     */
    @Test
    public void testSessionRefresh() {
        News news = (News) session.get(News.class, 1);
        System.out.println("news = " + news);
        session.refresh(news);
        News news2 = (News) session.get(News.class, 1);
        System.out.println("news = " + news2);
    }

    /**
     * session.clear 方法会强制的清理缓存，所以在 session.clear 后会再次向 数据库发送 Select 语句进行查询
     */
    @Test
    public void testSessionClear() {
        News news = (News) session.get(News.class, 1);
        System.out.println("news = " + news);
        session.clear();
        News news2 = (News) session.get(News.class, 1);
        System.out.println("news = " + news2);
    }

    /**
     * Session.save()
     * 1. 会使一个临时状态的对象转换成一个持久化状态的对象
     * 2. save 操作，对象会默认生成一个 ID
     * 3. 根据 ID 的生成策略不同，会在不同的时机执行 flush 操作， 发送 Insert 语句，
     * 1). 比如，如果生成策略是 native,则在 save方法调用的时候发送
     * 2). 如果生成策略是 hilo, 则会在 Session.commit 调用的时候 发送 Insert 语句
     * 4. save 调用之前， 临时对象设置的 ID 不会生效
     * 5. save 后, 持久化的对象的 ID 不能修改，否则会抛出 异常
     */
    @Test
    public void testSave() {
        News news = new News("Java", "Oracle", new Date());
        news.setId(100);
        session.save(news);
//        news.setId(200);
    }

    /**
     * Session.persist() 方法和 save() 方法的区别:
     * persist() 方法在调用之前， 如果临时对象设置了 ID , 则会抛出异常
     */
    @Test
    public void testPersist() {
        News news = new News("AA", "aa", new Date());
//        news.setId(100);
        session.persist(news);
        System.out.println("news = " + news);
        news.setId(200);
    }

    /**
     * Session.get 和 Session.load 方法的区别
     * 1. 加载
     * get 是立即加载，会在该方法调用的时候即 发送 Select 语句
     * load 是延迟加载，只有当使用 返回的 持久化对象的时候才会 发送 Select 语句
     * 2. 返回的对象
     * get 返回的是 实体的对象
     * load 返回的是 实体的代理对象
     * 3. LazyInitializationException 异常
     * get 调用后，关闭 session, 然后再查询 返回的实例对象不会抛出该异常
     * load 调用后，关闭 session, 然后再查询 返回的实例对象 就会抛出该异常
     * 4. 查询的数据在数据表中不存在
     * get 会返回 null
     * load 会抛出 ObjectNotFoundException
     */
    @Test
    public void testGet() {
        News news = (News) session.get(News.class, 1);
//        session.close();
        System.out.println("news = " + news);
        System.out.println("news.getDate().getClass() = " + news.getDate().getClass());
//        System.out.println(news.getClass().getName());
    }

    @Test
    public void testLoad() {
        News news = (News) session.load(News.class, 10);
//        session.close();
        System.out.println("news = " + news);
//        System.out.println(news.getClass().getName());
    }

    /**
     * update() 方法注意事项:
     * 1.如果要对一个持久化对象更新的话，不需要显示的调用 update() 方法，会在 Transaction.commit 方法中调用 flush 方法，将持久化对象的状态更新的数据表中
     * 2. 如果要更新一个游离状态的 对象的属性， 则需要显示的调用 update 方法，将游离状态的对象的状态更新到数据表中，update 方法的一个副作用就是将一个游离状态的对象变为一个持久化状态的对象
     * 注意:
     * 一个游离状态的对象，无论对象的属性有没有改变都会 发送 Update 语句,将 映射文件的 class节点的 select-before-update 属性改为 true 则会在每次更新之前都会出发 Select
     * 语句，不会进行没有必要的 Update 操作，【但一般不会设置，除非在跟触发器协同工作的时候需要设置】
     * 3. 如果一个游离对象在数据表中没有对应的记录， 执行 update 方法会报异常
     * 4. 当 update() 方法关联一个游离对象时，如果在 Session 的缓存中已经存在了一个相同 IOD 的持久化对象，会抛出异常，因为在 Session 缓存中不能同时有两个OID相同的对象
     */
    @Test
    public void testUpdate() {
        News news = (News) session.get(News.class, 1);
        System.out.println("news = " + news);
        transaction.commit();
        session.close();
//        news.setId(9);
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        News news1 = (News) session.get(News.class, 1);
        news.setAuthor("Sun");
        session.update(news);
    }

    /**
     * saveOrUpdate:
     * 既可以执行 save 操作又可以执行 update 操作，如果对象的状态是游离状态，则执行 update(), 如果对象的状态是 临时状态，则执行 save() 方法
     * 判断一个对象是游离状态还是临时状态的标准:
     * IOD==null 则为临时状态
     * IOD!=null 则为游离状态
     */
    @Test
    public void testSaveOrUpdate() {
        News news = new News("FFF", "fff", new Date());
        news.setId(2);
        session.saveOrUpdate(news);
    }

    /**
     * delete:
     * 1. 既可以删除游离状态的对象在数据表中对应的记录，又可以删除持久化状态在数据表中对应的记录
     * 2. 如果一个游离状态的对象在数据表中没有对应的记录，执行 delete 操作会抛出异常
     * 3. delete 方法调用并不会立即执行删除操作，也就是不是立即发送 Delete 语句，在执行 session.commit 时才会发送 Delete 语句，所以在执行完 delete操作后再查询对象还会有 IOD
     * 属性的值，如果想要调用 delete 方法后 OID 的值消失，则需要在全局配置文件中设置 use_identifier_rollback=true
     */
    @Test
    public void testDelete() {
//        News news = new News();
//        news.setId(100);
        News news = (News) session.get(News.class, 5);
        session.delete(news);
        System.out.println("news = " + news);
    }

    /**
     * Session.evict() 方法，会将指定的对象从 Session 的缓存中删除
     */
    @Test
    public void testEvict() {
        News news = (News) session.get(News.class, 6);
        News news2 = (News) session.get(News.class, 7);

        news.setAuthor("Oracle");
        news2.setAuthor("Oracle");

        session.evict(news);
    }

    @Test
    public void testWork() {
        session.doWork(connection -> System.out.println("connection = " + connection));
    }

    @Test
    public void testDynamicInsert() {
        News news = (News) session.get(News.class, 6);
        news.setAuthor("Sun");
    }

    @Test
    public void testFormula(){
        News news = (News) session.get(News.class, 6);
        System.out.println("news = " + news);
    }

    @Test
    public void testComponent(){
        Pay pay = new Pay(20000, 280000, 10);
        Worker worker = new Worker("Java", pay);
        session.save(worker);
    }
}
