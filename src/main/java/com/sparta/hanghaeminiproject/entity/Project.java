package com.sparta.hanghaeminiproject.entity;

import com.sparta.hanghaeminiproject.dto.ProjectRequestDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "project")
public class Project extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = true)
    private String imageUrl;

//    @Column(nullable = false)
//    List<String> stacks = new ArrayList<>();

    @Column(nullable = true)
    private int backEndMember;

    @Column(nullable = true)
    private int frontEndMember;

    @ElementCollection
    @CollectionTable(name = "project_stacks",
        joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "stack_name")
    List<String> stacks = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    //    @JoinTable(
//            name = "comments",
//            joinColumns = @JoinColumn(name = "project_id"),
//            inverseJoinColumns = @JoinColumn(name = "")
//    )
    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("createdAt desc")
    List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
    List<ProjectLike> likes = new ArrayList<>();

    @Builder
    public Project(ProjectRequestDto requestDto, User user, String imageUrl) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContent();
        this.imageUrl = imageUrl;
        this.stacks = requestDto.getStacks();
        this.frontEndMember = requestDto.getFrontEndMember();
        this.backEndMember = requestDto.getBackEndMember();
        this.user = user;
    }

    public void update(ProjectRequestDto requestDto, String imageUrl) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContent();
        this.imageUrl = imageUrl;
        this.stacks = requestDto.getStacks();
        this.frontEndMember = requestDto.getFrontEndMember();
        this.backEndMember = requestDto.getBackEndMember();
    }

}