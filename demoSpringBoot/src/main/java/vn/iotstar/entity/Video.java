package vn.iotstar.entity;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Videos")
public class Video implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "VideoId")
    private String videoId;

    @Column(name = "Active")
    private boolean active;

    @Column(name = "Description", columnDefinition = "nvarchar(MAX) not null")
    private String description;

    @Column(name = "Poster")
    private String poster;

    @Column(name = "Title", columnDefinition = "nvarchar(255) not null")
    private String title;

    @Column(name = "Views")
    private int views;

    // Quan hệ N Video - 1 Category
    @ManyToOne
    @JoinColumn(name = "CategoryId")
    private Category category;
}
