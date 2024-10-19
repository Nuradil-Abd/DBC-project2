package java15.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter

@AllArgsConstructor

public class Job {
    private Long id;
    private String position;//("Mentor","Management","Instructor") ушундай маанилер берилсин
    private String profession;//("Java","JavaScript")
    private String description;//("Backend developer","Fronted developer")
    private int experience;//(1,2,3........) опыт работы

    public Job(String position, String profession, String description, int experience) {
        this.position = position;
        this.profession = profession;
        this.description = description;
        this.experience = experience;
    }

    @Override
    public String toString() {
        return "\nJob(" +
               "id=" + id +
               ", position='" + position + '\'' +
               ", profession='" + profession + '\'' +
               ", description='" + description + '\'' +
               ", experience=" + experience +
               ')';
    }
}
