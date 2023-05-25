package org.example;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@NamedQueries({
        @NamedQuery(name = "Tower.showAll",
                query = "SELECT t FROM Tower t"),
})
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Table(name="TOWER")
@Entity
public class Tower {
    @Id
    @Getter
    @Column(unique = true)
    private String name;
    @Getter
    @Setter
    private int height;
    @OneToMany(mappedBy = "tower")
    @Getter
    @Setter
    private List<Mage> mages;

    public Tower(String name, int height){
        this.name = name;
        this.height = height;
        this.mages = new ArrayList<>();
    }

    public void addMage(Mage mage){
        mages.add(mage);
    }

    @Override
    public String toString() {
        String result = name + " - " + height + " m \n";
        if(mages != null || !mages.isEmpty()){
            result += "Mages: \n";
            for(Mage mage : mages){
                result += "\t - " + mage.getName() + "\n";
            }
        }
        return result;
    }

}
