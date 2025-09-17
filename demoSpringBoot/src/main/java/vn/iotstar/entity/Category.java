

package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Category")
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId")
    private Long categoryId;

    @Column(name = "CategoryCode")
    private String categorycode;
    @Column(name = "CategoryName", columnDefinition = "nvarchar(255)")
    private String categoryname;

    @Column(name = "Images")
    private String images;

    @Column(name = "Status")
    private boolean status;

    // Quan hệ 1 Category - N Video
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private Set<Video> videos;
}
