package org.example;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class App 
{
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab4pu");

    public static void main( String[] args )
    {
        /*
        addTower(new Tower("Wieza jaskolki", 100));
        addTower(new Tower("Zirael", 70));
        addTower(new Tower("Samotna Wieza", 25));
        addTower(new Tower("Tor Gvalch'ca", 43));
        addMage(new Mage("Vilgefortz", 99), "Wieza jaskolki");
        addMage(new Mage("Triss", 43), "Zirael");
        addMage(new Mage("Merlin", 42), "Tor Gvalch'ca");
        addMage(new Mage("Assire", 87), "Tor Gvalch'ca");
        addMage(new Mage("Tissaia", 23), "Samotna Wieza");
        addMage(new Mage("Dorregaray", 12), "Tor Gvalch'ca");
        addMage(new Mage("Ortolan", 54), "Zirael");
        addMage(new Mage("Francesca", 9), "Samotna Wieza");
        addMage(new Mage("Milegarda", 41), "Zirael");


        deleteMage("Vilgefortz");
        deleteMage("Merlin");
        deleteMage("Triss");

        deleteTower("Zirael");
        */

        //showAll();

        queries();

        emf.close();
    }

    private static void showAll() // Ex. 4
    {
        EntityManager em = emf.createEntityManager();
        try{
            Query query = em.createNamedQuery("Tower.showAll");
            List<Object> towers = query.getResultList();
            query = em.createNamedQuery("Mage.showAll");
            List<Object> mages = query.getResultList();
            printListObjects(towers);
            printListObjects(mages);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            em.close();
        }
    }
    private static void addMage(Mage mage, String towerName) // Ex. 2
    {
        EntityManager em = emf.createEntityManager();
        try{
            Tower tower = em.find(Tower.class, towerName);
            if(tower == null)
            {
                System.out.println("Tower" + towerName + " not exists");
                em.getTransaction().commit();
                em.close();
                return;
            }
            mage.setTower(tower);
            em.getTransaction().begin();

            em.persist(mage);
            tower.getMages().add(mage);
            em.getTransaction().commit();
            System.out.println("Mage " + mage.toString() + " successfully added !");
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            em.close();
        }
    }

    private static void addTower(Tower tower)
    {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(tower);
            em.getTransaction().commit();
            System.out.println("Tower " + tower.toString() + " successfully added !");
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            em.close();
        }

    }

    // Delete tower and set tower_name in mages to null
    private static void deleteTower(String name) // Ex. 3
    {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();

            Tower tower = em.find(Tower.class, name);
            if(tower == null) {
                System.out.println("Tower" + name + " not exists");
                em.getTransaction().commit();
                em.close();
                return;
            }
            List<Mage> mages = tower.getMages();
            for(Mage mage : mages) {
                mage.setTower(null);
            }
            em.remove(tower);

            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            em.close();
        }
    }

    private static void deleteMage(String name)
    {
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            Mage mage = em.find(Mage.class, name);
            if(mage == null)
            {
                System.out.println("Mage " + name + " not exists");
                em.getTransaction().commit();
                em.close();
                return;
            }
            Tower tower = mage.getTower();
            tower.getMages().remove(mage);
            em.remove(mage);

            em.getTransaction().commit();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            em.close();
        }
    }

    private static void printListObjects(List<Object> objects){
        for(Object object : objects){
            System.out.println(object.toString());
        }
    }
    private static void queries() // Ex. 5
    {
        /**String queryString = "SELECT t FROM Tower t WHERE t.name LIKE :name";
         Query query = em.createQuery(queryString, Tower.class);
         query.setParameter("name", towerName);
         Tower tower = (Tower)query.getSingleResult();
         */
        EntityManager em = emf.createEntityManager();
        try{
            String queryString1 = "SELECT m FROM Mage m WHERE m.level > :level";
            Query query1 = em.createQuery(queryString1, Mage.class);
            query1.setParameter("level", 50); // Mage level
            List<Object> mages = query1.getResultList();
            System.out.println(queryString1);
            printListObjects(mages);

            String queryString2 = "SELECT t FROM Tower t WHERE t.height > :height";
            Query query2 = em.createQuery(queryString2, Tower.class);
            query2.setParameter("height", 40); // height
            List<Object> towers = query2.getResultList();
            System.out.println(queryString2);
            printListObjects(towers);

            String queryString3 = "SELECT m FROM Mage m " +
                    "INNER JOIN Tower t ON t.name = m.tower " +
                    "WHERE m.level > :level AND t.name = :tower";
            Query query3 = em.createQuery(queryString3, Mage.class);
            query3.setParameter("level", 10); // Mage level
            query3.setParameter("tower", "Samotna wieza");
            List<Object> mages2 = query3.getResultList();
            System.out.println(queryString3);
            printListObjects(mages2);

        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            em.close();
        }

    }
}
