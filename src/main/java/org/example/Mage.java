package org.example;
import lombok.*;
import javax.persistence.*;

@NamedQueries({
        @NamedQuery(name = "Mage.showAll",
                query = "SELECT m FROM Mage m"),
})
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name="MAGE")
@Entity
public class Mage {
    @Id
    @Getter
    private String name;
    @Getter
    @Setter
    private int level;
    @ManyToOne
    @Getter
    @Setter
    private Tower tower;

    public Mage(String name, int level, Tower tower){
        this.name = name;
        this.level = level;
        this.tower = tower;
    }
    public Mage(String name, int level){
        this.name = name;
        this.level = level;
        this.tower = null;
    }

    @Override
    public String toString()
    {
        return name + " - " + level +" lvl - " + ((tower == null) ? "NULL" : tower.getName()) + " tower";
    }
}
